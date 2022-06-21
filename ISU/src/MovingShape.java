import java.awt.*;
//this interface exists so collision methods in the main class only need to be called once
public interface MovingShape {
    public Rectangle getRectangle();
    public void collision(Rectangle hitbox, Rectangle wall);
}
