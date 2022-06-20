import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractLevel {
    public ArrayList<Rectangle> walls;
    public ArrayList<Tank> tanks;
    public ArrayList<Rectangle> getWalls() {
        return walls;
    }
    public ArrayList<Tank> getTanks() {
        return tanks;
    }
}
