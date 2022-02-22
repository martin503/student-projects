package cover.algorithms;

import java.util.Arrays;

//    Contains Ids of sets
public class SetsIds {
    protected Integer[] set;

    public SetsIds(Integer[] set) {
        this.set = set.clone();
    }

    public void setLength(int length) {
        Integer[] newSet = new Integer[length];
        System.arraycopy(set, 0, newSet, 0, length);
        set = newSet;
    }

    @Override
    public String toString() {
        return Arrays.toString(set);
    }
}
