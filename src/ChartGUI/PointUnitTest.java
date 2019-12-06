package ChartGUI;

import org.junit.Test;

import static org.junit.Assert.*;

/*****************************************************************
 Class for unit testing the Point class.
 @author    Jordan Brown, Jacob Rodriguez, Cade Snuffer
 @version   1.0
 *****************************************************************/
public class PointUnitTest {

    @Test
    public void setX() {
        Point t = new Point();
        t.setX(1.55);
        assertEquals(1.55, t.x, 0.0001);
        Point t2 = new Point(21, 55.5);
        t.setX(100);
        assertEquals(100.0, t.x, 0.0001);
    }

    @Test
    public void setY() {
        Point t = new Point();
        t.setY(-1550.69);
        assertEquals(-1550.69, t.y, 0.0001);
        Point t2 = new Point(-55, 55.5);
        t.setY(1.55);
        assertEquals(1.55, t.y, 0.0001);
    }

    @Test
    public void getX() {
        Point t = new Point();
        t.setX(1.55);
        assertEquals(1.55, t.getX(), 0.0001);
    }

    @Test
    public void getY() {
        Point t = new Point();
        t.setY(-1550.69);
        assertEquals(-1550.69, t.getY(), 0.0001);
    }
}
