package ChartGUI;

import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void setAxes() {
        Graph x = new Graph();
        x.setAxes();
        assertEquals(-20.0, x.getYMin(), 0.0001);
        assertEquals(20.0, x.getYMax(), 0.0001);
        assertEquals(20.0, x.getXMax(), 0.0001);
        assertEquals(-20.0, x.getXMin(), 0.0001);

    }

    @Test
    public void setXMin() {
        Graph x = new Graph();
        x.setXMin(1.5);
        assertEquals(1.5, x.getXMin(), 0.0001);
    }

    @Test
    public void setXMax() {
        Graph x = new Graph();
        x.setXMax(-1.5);
        assertEquals(-1.5, x.getXMax(), 0.0001);
    }

    @Test
    public void setYMin() {
        Graph x = new Graph();
        x.setYMin(150);
        assertEquals(150, x.getYMin(), 0.0001);
    }

    @Test
    public void setYMax() {
        Graph x = new Graph();
        x.setYMax(-2.556);
        assertEquals(-2.556, x.getYMax(), 0.0001);
    }

    @Test
    public void setTickDistance() {
        Graph x = new Graph();
        x.setTickDistance(50);
        assertEquals(50.0, x.getTickDistance(), 0.0001);
    }

}