package cover.data;

import java.util.ArrayList;
import java.util.List;

//    Class that represents set of components
public class Set {
    private int Id;
    private List<Component> listOfComponents;
    private int howManyComponents;

    public int getId() {
        return Id;
    }

    public Set(int Id) {
        this.Id = Id;
        listOfComponents = new ArrayList<>(6);
        howManyComponents = 0;
    }

//    Adds given component to this set
    public void addComponent(Component toAdd) {
        listOfComponents.add(toAdd);
        howManyComponents++;
    }

//    Covers given collection with every component of this set
    public int coverComponents(int[] covered) {
        int coveredSum = 0;
        for (int j = 0; j < howManyComponents; j++) {
            coveredSum += listOfComponents.get(j).cover(covered);
        }
        return coveredSum;
    }

//    Uncovers given collection with every component of this set
    public int uncoverComponents(int[] covered) {
        int uncoveredSum = 0;
        for (int j = 0; j < howManyComponents; j++) {
            uncoveredSum += listOfComponents.get(j).uncover(covered);
        }
        return uncoveredSum;
    }
}