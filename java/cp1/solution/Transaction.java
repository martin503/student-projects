package cp1.solution;

import cp1.base.Resource;
import cp1.base.ResourceId;
import cp1.base.ResourceOperation;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class Transaction {
    private final Long startTime;
    private final List<ResourceOperation> myOperationsHistory;
    private final List<ResourceId> correspondingResources;
    private final Set<ResourceId> uniqueResources;
    private boolean isAborted;

    public Transaction(Long startTime) {
        this.startTime = startTime;
        myOperationsHistory = new LinkedList<>();
        correspondingResources = new LinkedList<>();
        uniqueResources = new LinkedHashSet<>();
        isAborted = false;
    }

    public boolean isAborted() {
        return isAborted;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Set<ResourceId> getUniqueCorrespondingResources() {
        return uniqueResources;
    }

    public void addResource(ResourceId rid) {
        uniqueResources.add(rid);
    }

    public void addOperation(ResourceOperation operation, ResourceId resource) {
        myOperationsHistory.add(operation);
        correspondingResources.add(resource);
    }

    public void abort() {
        isAborted = true;
    }

    public void undoAll(ConcurrentMap<ResourceId, Resource> resourcesMap) {
        ListIterator<ResourceOperation> operations = myOperationsHistory.listIterator(myOperationsHistory.size());
        ListIterator<ResourceId> resources = correspondingResources.listIterator(correspondingResources.size());
        while (operations.hasPrevious())
            operations.previous().undo(resourcesMap.get(resources.previous()));
    }
}