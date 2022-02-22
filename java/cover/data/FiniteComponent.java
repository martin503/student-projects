package cover.data;

//    Class of Components that represent finite arithmetic series
public class FiniteComponent extends Component {
    private int difference;
    private int max;

    public FiniteComponent(int firstElement, int difference, int max) {
        super(firstElement);
        this.difference = difference;
        this.max = max;
    }

    public int cover(int[] covered) {
        int coveredSum = 0;
        for (int i = firstElement; i <= max  && i <= covered.length; i += difference) {
            covered[i - 1]++;
            if (covered[i - 1] == 1)
                coveredSum++;
        }
        return coveredSum;
    }

    public int uncover(int[] covered) {
        int uncoveredSum = 0;
        for (int i = firstElement; i <= max  && i <= covered.length; i += difference) {
            covered[i - 1]--;
            if (covered[i - 1] == 0)
                uncoveredSum++;
        }
        return uncoveredSum;
    }
}
