package ChartGUI;

import org.junit.Test;

import static org.junit.Assert.*;

public class calcTest {

    @Test
    public void intExpressions(){
        calc test = new calc();
        test.addToExpression("5!");
        assertEquals(120.0, test.evaluate(), 0.0001);
    }

    @Test
    public void floatExpressions(){
        calc test = new calc();

    }

    @Test
    public void negativeExpressions(){
        calc test = new calc();

    }

    @Test
    public void errorChecking1(){
        calc test = new calc();

    }

    @Test
    public void errorChecking2(){
        calc test = new calc();

    }

    @Test
    public void pastExpressions(){
        calc test = new calc();

    }

    @Test
    public void pastResults(){
        calc test = new calc();

    }
}