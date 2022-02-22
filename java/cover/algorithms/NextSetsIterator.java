package cover.algorithms;

//    Class that produces possible sets configurations
public class NextSetsIterator {
    final private int setCount;
    private Integer[] currentPos;
    private int posCount;

    public NextSetsIterator(int setCount, int posCount) {
        this.setCount = setCount;
        this.posCount = posCount;
        currentPos = new Integer[posCount];
        init();
    }

//    Sets how long configuration we look for
    public void setPosCount(int newPosCount) {
        Integer[] newPos = new Integer[newPosCount];
        System.arraycopy(currentPos, 0, newPos, 0, newPosCount);
        this.currentPos = newPos;
        this.posCount = newPosCount;
    }

    private void init() {
        for (int i = 0; i < posCount-1; i++)
            currentPos[i] = i;
        currentPos[posCount - 1] = posCount - 2;
    }

    private boolean findNext() {
        for (int pos = posCount - 1; pos >= 0; pos--) {
            int maxToPut = posCount - pos;
            if (currentPos[pos] < setCount - maxToPut ) {
                int start = currentPos[pos];
                for (int j = pos; j < posCount; j++) {
                    start++;
                    currentPos[j] = start;
                }
                return true;
            }
        }
        return false;
    }

//    Returns next set configuration
    public SetsIds next() {
        if (findNext())
            return new SetsIds(currentPos);
        else
            return null;
    }

}
