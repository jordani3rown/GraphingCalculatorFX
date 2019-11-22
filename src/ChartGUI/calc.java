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
        pastInput = new ArrayList<>();
        pastResult = new ArrayList<>();
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
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
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

    public void storeInVar(){
        stored = expression;
    }
}

