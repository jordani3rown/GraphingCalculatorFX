package ChartGUI;

public class dataTable {
    // Array of nodes that represent the data points for a single expression
    Point[] dataPoints;

    String expression;

    int expressionID;


    public dataTable(UserExpression data) {
        this.expression = data.expression;
        this.expressionID = data.expressionID;
    }

    public dataTable(int identity) {

    }


}
