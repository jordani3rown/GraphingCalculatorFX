package ChartGUI;

/*****************************************************************
 Graph Class that contains the logic of a graph that will be displayed
 @author    Jordan Brown, Jacob Rodriguez, Cade Snuffer
 @version   1.0
 *****************************************************************/
public class Graph {

    /** min and max values for the x and y axes */
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    /** double that contains the length of each hash mark */
    private double tickDistance;

    private Graph graph;

    /*****************************************************************
     Default constructor which sets the initial state of the graph
     *****************************************************************/
    public Graph() {
        // Set each axis min and max values to those in the setAxes method
        setAxes();
    }

    /*****************************************************************
     Method that sets the initial values for the graph components
     *****************************************************************/
    public void setAxes() {
        // Set the min and max values of the x axis
        setXMin(-20.0);
        setXMax(20.0);
        // Set the min and max values of the y axis
        setYMin(-20.0);
        setYMax(20.0);
        // Set the distance between hash marks
        setTickDistance(1.0);
    }

    /*****************************************************************
     Sets the smallest x value contained on the x axis
     *****************************************************************/
    public void setXMin(double xMin) {
        this.xMin = xMin;
    }

    /*****************************************************************
     Sets the largest x value contained on the x axis
     *****************************************************************/
    public void setXMax( double xMax) {
        this.xMax = xMax;
    }

    /*****************************************************************
     Sets the smallest y value contained on the y axis
     *****************************************************************/
    public void setYMin(double yMin) {
        this.yMin = yMin;
    }

    /*****************************************************************
     Sets the largest y value contained on the y axis
     *****************************************************************/
    public void setYMax( double yMax) {
        this.yMax = yMax;
    }

    /*****************************************************************
     Sets the distance between each hash mark
     *****************************************************************/
    public void setTickDistance(double tickDistance) {
        this.tickDistance = tickDistance;
    }

    /*****************************************************************
     Returns the smallest x value contained on the x axis
     *****************************************************************/
    public double getXMin() {
        return xMin;
    }

    /*****************************************************************
     Returns the largest x value contained on the x axis
     *****************************************************************/
    public double getXMax() {
        return xMax;
    }

    /*****************************************************************
     Returns the smallest y value contained on the y axis
     *****************************************************************/
    public double getYMin() {
        return yMin;
    }

    /*****************************************************************
     Returns the largest y value contained on the y axis
     *****************************************************************/
    public double getYMax() {
        return yMax;
    }

    public double getTickDistance() {
        return tickDistance;
    }





}
