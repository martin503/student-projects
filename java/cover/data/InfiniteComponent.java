package cover.data;

//    Class of Components that represent infinite arithmetic series
public class InfiniteComponent extends Component {
    private int difference;

    public InfiniteComponent(int firstElement, int difference) {
        super(firstElement);
        this.difference = difference;
    }

    public int cover(int[] covered) {
        int coveredSum = 0;
        for (int i = firstElement; i <= covered.length; i += difference) {
            covered[i - 1]++;
            if (covered[i - 1] == 1)
                coveredSum++;
        }
        return coveredSum;
    }

    public int uncover(int[] covered) {
        int uncoveredSum = 0;
        for (int i = firstElement; i <= covered.length; i += difference) {
            covered[i - 1]--;
            if (covered[i - 1] == 0)
                uncoveredSum++;
        }
        return uncoveredSum;
    }
}
