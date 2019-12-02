package ChartGUI;

import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;

/**
 *
 */
public class calc {

    private String expression;
    private String stored;
    private ArrayList<String> pastInput;
    private ArrayList<Double> pastResult;

    /**
     *
     */
    public calc() {
        expression = "";
        stored = "";
        pastInput = new ArrayList<>();
        pastResult = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public String getStored(){
        return stored;
    }

    /**
     * @return
     */
    public double evaluate() {
        Expression e = new Expression(expression);
        pastInput.add(expression);
        double result = e.calculate();
        pastResult.add(result);
        return result;
    }

    /**
     * @param in
     */
    public void addToExpression(String in) {
        if (in.equals("X")) {
            expression = expression + stored;
            return;
        }
        if (in.equals("+")) {
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if (in.equals("-")) {
            if(expression.length() == 0){

            }
            else if (((expression.charAt(expression.length() - 1) != ')' && expression.charAt(expression.length() - 1) != '(') && !Character.isDigit(expression.charAt(expression.length() - 1)))) {
                throw new IllegalArgumentException();
            }
        }
        if (in.equals("/")) {
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if (in.equals("*")) {
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if (in.equals(".")) {
            if (expression.length() == 0 || !Character.isDigit(expression.charAt(expression.length() - 1)) || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if (in.equals(")")) {

        }
        if (in.equals("^")) {
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if (in.equals("^2")) {
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if(in.equals("!")){
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if (in.equals("NaN")) {
            throw new IllegalArgumentException();
        }
        expression = expression + in;
    }

    /**
     *
     */
    public void clear() {
        expression = "";
    }

    /**
     *
     */
    public void del() {
        if (expression.length() == 0) {
            return;
        }
        expression = expression.substring(0, expression.length() - 1);
    }

    /**
     * @return
     */
    public String getExpression() {
        return expression;
    }

    /**
     * @return
     */
    public String getPrevInput() {
        if (pastInput.isEmpty()) {
            return "";
        }
        return pastInput.remove(pastInput.size() - 1);

    }

    /**
     * @return
     */
    public double getPrevResult() {
        if (pastResult.isEmpty()) {
            return 0.00;
        }
        return pastResult.remove(pastResult.size() - 1);
    }

    public String polySolve(double a, double b, double c){
        double dis;
        double root;
        if (a == 0) {
            throw new IllegalArgumentException();
        }

        dis = Math.pow(b, 2.0) - 4*a*c;
        root = Math.sqrt(Math.abs(dis));

        if (dis > 0){
            return "x = " + ((-b + root) / (2 * a) + "\nx = " +(-b - root) / (2 * a));
        }
        else{
            return "x = " + (-b / ( 2 * a ) + " + i(" + root/(2*a) + ")\nx = "  + -b / ( 2 * a ) + " - i(" + root/(2*a))+ ")";
        }
    }

    public void storeInVar(){
        stored = expression;
    }
}

