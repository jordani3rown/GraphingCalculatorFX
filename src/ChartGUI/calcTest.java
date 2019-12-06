package ChartGUI;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/*****************************************************************
 Class for further testing the calc class.
 @author    Cade Snuffer
 @version   1.0
 *****************************************************************/
public class calcTest {

    @Test
    public void intExpressions() {
        calc test = new calc();
        test.addToExpression("5!");
        assertEquals(120.0, test.evaluate(), 0.0001);
        test.addToExpression("+20202020");
        assertEquals(20202140.0, test.evaluate(), 0.0001);

        for (int i = -1000; i < 1000; i++) {
            test.clear();
            test.addToExpression(i + "+" + "(" + i + ")");
            assertEquals(i + i, test.evaluate(), 0.0001);

            test.clear();
            test.addToExpression(test.getPrevResult() + "*" + "(" + i + ")");
            assertEquals(((i + i) * i), test.evaluate(), 0.0001);

            test.clear();
            test.addToExpression(test.getPrevResult() + "^" + "(" + i + ")");
            assertEquals(Math.pow(((i + i) * i), i), test.evaluate(), 0.0001);
            test.clear();
        }
    }

    @Test
    public void floatExpressions() {
        calc test = new calc();
        test.addToExpression("2+20202020.2222");
        assertEquals(20202022.2222, test.evaluate(), 0.0001);
    }

    @Test
    public void undoAns() {
        calc test = new calc();
        Random i = new Random();
        ArrayList<String> undos = new ArrayList<String>();
        ArrayList<Double> ans = new ArrayList<Double>();
        while (test.getExpression().length() < 50000) {
            test.addToExpression(i.nextDouble() + "+" + i.nextDouble());
            undos.add(test.getExpression());
            ans.add(test.evaluate());
            assertEquals(ans.get(ans.size() - 1), test.getCurrentAns(), 0.0001);
        }
        String prev = undos.get(undos.size() - 1);
        while (!prev.equals("")) {
            assertEquals(prev, test.getPrevInput());
            undos.remove(undos.size() - 1);
            assertEquals(ans.get(ans.size() - 1), test.getPrevResult(), 0.0001);
            ans.remove(ans.size() - 1);
            if (!undos.isEmpty()) {
                prev = undos.get(undos.size() - 1);
            } else {
                prev = "";
            }
        }
    }
}
