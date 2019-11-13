package ChartGUI;

public class dataTable {
    // Array of nodes that represent the data points for a single expression
    Point[] dataPoints;
    /** */
    String expression;
    /** */
    int expressionID;
    /** */
    double yMin;
    /** */
    double yMax;
    /** */
    double xMin;
    /** */
    double xMax;
    /** */
    Graph g;

    /*****************************************************************

     *****************************************************************/
    public dataTable(UserExpression data) {
        this.expression = data.expression;
        this.expressionID = data.expressionID;
    }

    /*****************************************************************

     *****************************************************************/
    public void setTableMinMax() {
        g = new Graph();
        this.yMin = g.getYMin();
        this.yMax = g.getYMax();
        this.xMin = g.getXMin();
        this.xMax = g.getXMax();
    }




}
