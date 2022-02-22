package cover.data;

//    Class of objects that represents elements in set
public abstract class Component {
    protected int firstElement;

    public Component(int firstElement) {
        this.firstElement = firstElement;
    }

//    Covers with component given set represented by array
    public abstract int cover(int[] covered);

//    Uncovers with component given set represented by array
    public abstract int uncover(int[] covered);
}