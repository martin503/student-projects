package cp1.solution;

import cp1.base.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TM implements cp1.base.TransactionManager {
    private final LocalTimeProvider timeProvider;
    private final ConcurrentMap<ResourceId, Resource> resourceMap;
    private final ConcurrentMap<Long, Transaction> threadTransactionMap;
    private final ConcurrentMap<ResourceId, Long> affiliationResourceThreadMap;
    private final ConcurrentMap<Long, ResourceId> threadDependentOnResourceMap;
    private final ConcurrentMap<ResourceId, Integer> howManyWaitForResource;
    private final ConcurrentMap<ResourceId, ReentrantLock> waitingForResourceMap;
    private final ConcurrentMap<Long, Thread> idThreadMap;
    private final Lock mutex;
    private final Semaphore setAffiliation;

    public TM(Collection<Resource> resources, LocalTimeProvider timeProvider) {
        this.timeProvider = timeProvider;
        threadTransactionMap = new ConcurrentHashMap<>();
        affiliationResourceThreadMap = new ConcurrentHashMap<>();
        threadDependentOnResourceMap = new ConcurrentHashMap<>();
        howManyWaitForResource = new ConcurrentHashMap<>();
        resourceMap = new ConcurrentHashMap<>();
        waitingForResourceMap = new ConcurrentHashMap<>();
        idThreadMap = new ConcurrentHashMap<>();
        mutex = new ReentrantLock(true);
        setAffiliation = new Semaphore(0);
        for (Resource r : resources) {
            resourceMap.put(r.getId(), r);
            howManyWaitForResource.put(r.getId(), 0);
            waitingForResourceMap.put(r.getId(), new ReentrantLock(true));
        }
    }

    @Override
    public void startTransaction() throws AnotherTransactionActiveException {
        if (threadTransactionMap.containsKey(getCurrentThreadId()))
            throw new AnotherTransactionActiveException();
        threadTransactionMap.put(getCurrentThreadId(), new Transaction(timeProvider.getTime()));
        idThreadMap.put(getCurrentThreadId(), Thread.currentThread());
    }

    @Override
    public void operateOnResourceInCurrentTransaction(ResourceId rid, ResourceOperation operation)
            throws NoActiveTransactionException, UnknownResourceIdException, ActiveTransactionAborted,
            ResourceOperationException, InterruptedException {
        if (!isTransactionActive())
            throw new NoActiveTransactionException();
        else if (!resourceMap.containsKey(rid))
            throw new UnknownResourceIdException(rid);
        else if (isTransactionAborted())
            throw new ActiveTransactionAborted();

        if (isResourceNotHeldByMe(rid)) {
            mutex.lockInterruptibly();
            if (isResourceNotHeld(rid)) {
                try {
                    waitingForResourceMap.get(rid).lockInterruptibly();
                } catch (InterruptedException e) {
                    mutex.unlock();
                    throw new InterruptedException();
                }
                affiliationResourceThreadMap.put(rid, getCurrentThreadId());
                threadTransactionMap.get(getCurrentThreadId()).addResource(rid);
                mutex.unlock();
            } else {
                threadDependentOnResourceMap.put(getCurrentThreadId(), rid);
                howManyWaitForResource.put(rid, howManyWaitForResource.get(rid) + 1);
                abortIfNecessary(getCurrentThreadId());
                mutex.unlock();
                try {
                    waitingForResourceMap.get(rid).lockInterruptibly();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    howManyWaitForResource.put(threadDependentOnResourceMap.get(getCurrentThreadId()),
                            howManyWaitForResource.get(threadDependentOnResourceMap.get(getCurrentThreadId())) - 1);
                    threadDependentOnResourceMap.remove(getCurrentThreadId());
                    if (threadTransactionMap.get(getCurrentThreadId()).isAborted())
                        throw new ActiveTransactionAborted();
                    throw new InterruptedException();
                }
                howManyWaitForResource.put(rid, howManyWaitForResource.get(rid) - 1);
                threadDependentOnResourceMap.remove(getCurrentThreadId());
                affiliationResourceThreadMap.put(rid, getCurrentThreadId());
                threadTransactionMap.get(getCurrentThreadId()).addResource(rid);
                setAffiliation.release();
            }
        }

        operation.execute(resourceMap.get(rid));
        if (Thread.currentThread().isInterrupted()) {// Control if thread was interrupted during operation execution
            operation.undo(resourceMap.get(rid));
            throw new InterruptedException();
        }
        threadTransactionMap.get(getCurrentThreadId()).addOperation(operation, rid);
    }

    @Override
    public void commitCurrentTransaction() throws NoActiveTransactionException, ActiveTransactionAborted {
        if (!isTransactionActive())
            throw new NoActiveTransactionException();
        else if (isTransactionAborted())
            throw new ActiveTransactionAborted();

        clearAfterTransaction();
    }

    @Override
    public void rollbackCurrentTransaction() {
        if (threadTransactionMap.containsKey(getCurrentThreadId())) {
            threadTransactionMap.get(getCurrentThreadId()).undoAll(resourceMap);
            clearAfterTransaction();
        }
    }

    @Override
    public boolean isTransactionActive() {
        return threadTransactionMap.containsKey(getCurrentThreadId());
    }

    @Override
    public boolean isTransactionAborted() {
        return isTransactionActive() && threadTransactionMap.get(getCurrentThreadId()).isAborted();
    }

    private void abortIfNecessary(Long firstThreadId) {
        List<Long> inheritedDependentThreadIds = new LinkedList<>();
        Long last = firstThreadId;
        do {
            if (isWaitingForResource(last)) {
                inheritedDependentThreadIds.add(last);
                last = affiliationResourceThreadMap.get(threadDependentOnResourceMap.get(last));
            } else
                break;
        } while (!firstThreadId.equals(last));
        if (firstThreadId.equals(last)) {
            Long latest = firstThreadId;
            for (Long threadId : inheritedDependentThreadIds)
                latest = getLaterThreadId(threadId, latest);
            threadTransactionMap.get(latest).abort();
            idThreadMap.get(latest).interrupt();
        }
    }

    private void clearAfterTransaction() {
        mutex.lock();
        for (ResourceId rid : threadTransactionMap.get(getCurrentThreadId()).getUniqueCorrespondingResources()) {
            affiliationResourceThreadMap.remove(rid);
            if (howManyWaitForResource.get(rid) > 0) {
                waitingForResourceMap.get(rid).unlock();
                setAffiliation.acquireUninterruptibly(); // waiting for unlocked thread to take resources
            } else
                waitingForResourceMap.get(rid).unlock();
        }
        threadTransactionMap.remove(getCurrentThreadId());
        idThreadMap.remove(getCurrentThreadId());
        mutex.unlock();
    }

    private Long getCurrentThreadId() {
        return Thread.currentThread().getId();
    }

    private boolean isResourceNotHeld(ResourceId rid) {
        return !affiliationResourceThreadMap.containsKey(rid);
    }

    private boolean isResourceNotHeldByMe(ResourceId rid) {
        return !getCurrentThreadId().equals(affiliationResourceThreadMap.get(rid));
    }

    private boolean isWaitingForResource(Long toCheck) {
        return threadDependentOnResourceMap.containsKey(toCheck);
    }

    private Long getLaterThreadId(Long firstThreadId, Long secondThreadId) {
        Long firstsTime = threadTransactionMap.get(firstThreadId).getStartTime();
        Long secondsTime = threadTransactionMap.get(secondThreadId).getStartTime();
        if (firstsTime.equals(secondsTime))
            return Math.max(firstThreadId, secondThreadId);
        return firstsTime > secondsTime ? firstThreadId : secondThreadId;
    }
}