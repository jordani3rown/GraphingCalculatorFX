package ChartGUI;

import org.mariuszgromada.math.mxparser.*;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class calc {

    private String expression;
    private ArrayList<String> pastInput;
    private ArrayList<Double> pastResult;

    public calc(){
        expression = "";
        pastInput= new ArrayList<>();
        pastResult = new ArrayList<>();
    }

    public double evaluate(){
        Expression e = new Expression(expression);
        pastInput.add(expression);
        double result = e.calculate();
        pastResult.add(result);
        return result;
    }

    public double solveEquation(){
        return 0;
    }

    public void addToExpression(String in){
        expression = expression + in;
    }

    public void clear(){
        expression = "";
    }
    public void del(){
        if(expression.length()==0){
            return;
        }
        expression = expression.substring(0,expression.length()-1);
    }
    public String getExpression(){
        return expression;
    }

    public String getPrevInput(){
        return pastInput.get(pastInput.size()-1);

    }

    public double getPrevResult(){
        return pastResult.get(pastResult.size()-1);
    }
}

