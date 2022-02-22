package cover.data;

//    Class of Components that represent simple integer
public class OneComponent extends Component{
    public OneComponent(int firstElement) {
        super(firstElement);
    }

    public int cover(int[] covered) {
        if (covered.length >= firstElement) {
            covered[firstElement - 1]++;
            if (covered[firstElement - 1] == 1)
                return 1;
        }
        return 0;
    }

    public int uncover(int[] covered) {
        if (covered.length >= firstElement) {
            covered[firstElement - 1]--;
            if (covered[firstElement - 1] == 0)
                return 1;
        }
        return 0;
    }
}
