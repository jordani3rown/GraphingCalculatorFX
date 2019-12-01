package ChartGUI;

import javafx.scene.chart.XYChart;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserExpressionUnitTest {

    @Test
    public void setExpressionID() {
        UserExpression s = new UserExpression(55, "t1");
        s.setExpressionID(21);
        assertEquals(21, s.expressionID);
    }

    @Test
    public void getExpressionID() {
        UserExpression s = new UserExpression();
        s.setExpressionID(-51);
        assertEquals(-51, s.getExpressionID());
    }

    @Test
    public void setExpression() {
        UserExpression s = new UserExpression();
        s.setExpression("test21");
        assertEquals("test21", s.expression);
    }

    @Test
    public void getExpression() {
        UserExpression s = new UserExpression();
        s.setExpression("test21testtest");
        assertEquals("test21testtest", s.getExpression());
    }

    @Test
    public void setExpressionSeries() {
        UserExpression s = new UserExpression();
        XYChart.Series test = new XYChart.Series<Number, Number>();
        s.setExpressionSeries(test);
        assertEquals(test, s.expressionSeries);
    }
}