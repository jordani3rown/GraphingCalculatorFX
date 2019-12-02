package ChartGUI;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class calcUnitTest {

    @Test
    public void evaluate() {
        calc temp = new calc();
        temp.addToExpression("4*3*4+2*2");
        assertEquals(52.0, temp.evaluate(), 0.0001);
        temp.clear();
        temp.addToExpression("cos(21.78)/sin(21)+(8*sqrt(16))");
        assertEquals(30.83131, temp.evaluate(), 0.0001);
        temp.clear();
        temp.addToExpression("e^(25)+ln(59.5)");
        assertEquals(7.20048993414718e10, temp.evaluate(), 0.0001);
    }

    @Test
    public void addToExpression() {
        calc g = new calc();
        assertEquals("", g.getExpression());
        g.addToExpression("4");
        assertEquals("4", g.getExpression());
        g.addToExpression("+(4+2)");
        assertEquals("4+(4+2)", g.getExpression());
        g.addToExpression("X");
        assertEquals("4+(4+2)", g.getExpression());
        g.storeInVar();
        g.addToExpression("X");
        assertEquals("4+(4+2)4+(4+2)", g.getExpression());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nanThrows(){
        calc temp = new calc();
        temp.addToExpression("NaN");
    }

    @Test(expected = IllegalArgumentException.class)
    public void squareThrows(){
        calc temp = new calc();
        temp.addToExpression("^2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void expThrows(){
        calc temp = new calc();
        temp.addToExpression("^");
    }

    @Test(expected = IllegalArgumentException.class)
    public void dotThrows(){
        calc temp = new calc();
        temp.addToExpression(".");
    }

    @Test(expected = IllegalArgumentException.class)
    public void multThrows(){
        calc temp = new calc();
        temp.addToExpression("*");
    }

    @Test(expected = IllegalArgumentException.class)
    public void divThrows(){
        calc temp = new calc();
        temp.addToExpression("/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void minThrows(){
        calc temp = new calc();
        temp.addToExpression("+");
        temp.addToExpression("-");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addThrows(){
        calc temp = new calc();
        temp.addToExpression("+");
    }

    @Test
    public void clear() {
        calc temp = new calc();
        assertEquals("", temp.getExpression());
        temp.clear();
        assertEquals("", temp.getExpression());
        temp.addToExpression("4+(4+2)4+(4+2)");
        assertEquals("4+(4+2)4+(4+2)", temp.getExpression());
        temp.clear();
        assertEquals("", temp.getExpression());
    }

    @Test
    public void del() {
        calc temp = new calc();
        temp.addToExpression("15");
        temp.del();
        assertEquals("1", temp.getExpression());
        temp.del();
        assertEquals("", temp.getExpression());
        temp.del();
        assertEquals("", temp.getExpression());
    }

    @Test
    public void getExpression() {
        calc temp = new calc();
        assertEquals("", temp.getExpression());
    }

    @Test
    public void getPrevInput() {
        calc temp = new calc();
        assertEquals("", temp.getPrevInput());
        temp.addToExpression("4+3");
        temp.evaluate();
        assertEquals("4+3", temp.getPrevInput());
        assertEquals("", temp.getPrevInput());
        temp.clear();
        temp.addToExpression("15*2*(3-5)");
        temp.evaluate();
        temp.clear();
        temp.addToExpression("14/5");
        temp.evaluate();
        assertEquals("14/5", temp.getPrevInput());
        assertEquals("15*2*(3-5)", temp.getPrevInput());
        assertEquals("", temp.getPrevInput());
    }

    @Test
    public void getPrevResult() {
        calc temp = new calc();
        assertEquals(0.0, temp.getPrevResult(), 0.0001);
        temp.addToExpression("15*(15)+cos(pi)");
        temp.evaluate();
        assertEquals(224.0, temp.getPrevResult(),0.0001);
        assertEquals(0.0, temp.getPrevResult(),0.0001);
        temp.clear();
        temp.addToExpression("15*2*(3-5)+ln(2)");
        temp.evaluate();
        temp.clear();
        temp.addToExpression("30/2");
        temp.evaluate();
        assertEquals(15.0, temp.getPrevResult(),0.0001);
        assertEquals(-59.30685, temp.getPrevResult(),0.0001);
        assertEquals(0.0, temp.getPrevResult(),0.0001);
    }

    @Test
    public void polySolve() {
        calc temp = new calc();
        assertEquals("x = 3.0\nx = -2.0", temp.polySolve(1,-1,-6));
        assertEquals("x = -1.0\nx = -6.0", temp.polySolve(1,7,6));
        assertEquals("x = -2.0 + i(1.0)\nx = -2.0 - i(1.0)", temp.polySolve(1,4,5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void polyThrows(){
        calc temp = new calc();
        temp.polySolve(0,3,4);
    }

    @Test
    public void storeInVar() {
        calc temp = new calc();
        assertEquals("", temp.getStored());
        temp.addToExpression("2500+26.809*(29/2)");
        temp.storeInVar();
        assertEquals("2500+26.809*(29/2)", temp.getExpression());
        assertEquals("2500+26.809*(29/2)", temp.getStored());
    }
}