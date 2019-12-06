package ChartGUI;

import javafx.scene.chart.XYChart;

/**
 *
 */
public class UserExpression {
    /** */
    public int expressionID;
    /** */
    public String expression;
    /** */
    XYChart.Series expressionSeries;

    /*****************************************************************

     *****************************************************************/
    public UserExpression() {

    }

    /*****************************************************************

     *****************************************************************/
    public UserExpression(int expressionID, String expression) {
        setExpressionID(expressionID);
        setExpression(expression);
    }

    /*****************************************************************

     *****************************************************************/
    public void setExpressionID(int expressionID) {
        this.expressionID = expressionID;
    }

    /*****************************************************************

     *****************************************************************/
    public int getExpressionID() {
        return expressionID;
    }

    /*****************************************************************

     *****************************************************************/
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /*****************************************************************

     *****************************************************************/
    public String getExpression() {
        return expression;
    }

    public void setExpressionSeries(XYChart.Series series) {
        this.expressionSeries = series;
    }

}
