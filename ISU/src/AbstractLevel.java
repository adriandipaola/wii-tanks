/**
 * This made it much easier to set up all the levels; without this, we cannot have an array of levels
 * in the main class. We would have had to stuff all the code from each "level" class into one.
 */

import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractLevel {
    //stores spawn points & attributes of walls and tanks for each level
    public ArrayList<Rectangle> walls;
    public ArrayList<Tank> tanks;
    public ArrayList<Rectangle> getWalls() {
        return walls;
    }
    public ArrayList<Tank> getTanks() {
        return tanks;
    }
}
