package ChartGUI;

import org.mariuszgromada.math.mxparser.*;

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

    public void addToExpression(String in){
        if(in.equals("+")) {
            if (expression.length() == 0 || !Character.isDigit(expression.charAt(expression.length() - 1)) || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if(in.equals("-")) {
            if (expression.length() == 0 || !Character.isDigit(expression.charAt(expression.length() - 1)) || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if(in.equals("/")) {
            if (expression.length() == 0 || !Character.isDigit(expression.charAt(expression.length() - 1)) || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if(in.equals("*")) {
            if (expression.length() == 0 || !Character.isDigit(expression.charAt(expression.length() - 1)) || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if(in.equals(".")) {
            if (expression.length() == 0 || !Character.isDigit(expression.charAt(expression.length() - 1)) || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if(in.equals(")")){

        }
        if(in.equals("^")) {
            if (expression.length() == 0 || !Character.isDigit(expression.charAt(expression.length() - 1)) || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if(in.equals("^2")) {
            if (expression.length() == 0 || !Character.isDigit(expression.charAt(expression.length() - 1))) {
                throw new IllegalArgumentException();
            }
        }
        if(in.equals("NaN")){
            throw new IllegalArgumentException();
        }
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
        if(pastInput.isEmpty()){
            return "";
        }
        return pastInput.get(pastInput.size()-1);

    }

    public double getPrevResult(){
        if(pastResult.isEmpty()){
            return 0.00;
        }
        return pastResult.get(pastResult.size()-1);
    }
}

