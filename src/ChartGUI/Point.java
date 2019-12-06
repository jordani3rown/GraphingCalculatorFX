package ChartGUI;

/*****************************************************************
 Class for a point on the graphing pane of the application.
 @author    Jordan Brown, Jacob Rodriguez, Cade Snuffer
 @version   1.0
 *****************************************************************/
public class Point {

    /** Coordinate X. */
    public double x;

    /** Coordinate Y. */
    public double y;

    /*****************************************************************
     Default constructor for point object.
     *****************************************************************/
    public Point() {

    }

    /*****************************************************************
     Constructor for point with axis parameters.
     @param x coordinate
     @param y coordinate
     *****************************************************************/
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*****************************************************************
     Method for setting X coordinate.
     *****************************************************************/
    public void setX(double x) {
        this.x = x;
    }

    /*****************************************************************
     Method for setting Y coordinate.
     *****************************************************************/
    public void setY(double y) {
        this.y = y;
    }

    /*****************************************************************
     Method for retrieving X coordinate.
     *****************************************************************/
    public double getX() {
        return x;
    }

    /*****************************************************************
     Method for retrieving Y coordinate.
     *****************************************************************/
    public double getY() {
        return y;
    }
}
