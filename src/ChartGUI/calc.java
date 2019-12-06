package ChartGUI;

import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;

/*****************************************************************
 Class that handles the calculator's input functions.
 @author    Jordan Brown, Jacob Rodriguez, Cade Snuffer
 @version   1.0
 *****************************************************************/
public class calc {

    /** String used for storing user entered expressions. */
    private String expression;

    /** String used for storing specific expressions for later use. */
    private String stored;

    /** Double used for storing the last computed result. */
    private double currentAns;

    /** Array list used to store previously evaluated expressions. */
    private ArrayList<String> pastInput;

    /** Array list used to store previous results. */
    private ArrayList<Double> pastResult;

    /*****************************************************************
     Constructor for calculator's input and result text fields.
     *****************************************************************/
    public calc() {
        // Set current expression to blank
        expression = "";
        // Current storage is empty
        stored = "";
        // Set current answer to zero
        currentAns = 0.0;
        // No past input
        pastInput = new ArrayList<>();
        // No past results
        pastResult = new ArrayList<>();
    }

    /*****************************************************************
     Returns the stored expression.
     @return double
     *****************************************************************/
    public String getStored() {
        // Return stored whether empty or not
        return stored;
    }

    /*****************************************************************
     Evaluates and returns the expression.
     @return double
     *****************************************************************/
    public double evaluate() {
        // Create new Expression object with the input string
        Expression e = new Expression(expression);
        // Add the expression to list of past inputs
        pastInput.add(expression);
        // Calculate result of expression
        double result = e.calculate();
        // Result is saved as current answer
        currentAns = result;
        // Add the result to the list of past results
        pastResult.add(result);
        // Return the result
        return result;
    }

    /*****************************************************************
     Appends functions/symbols to the expression.
     @param in User's input
     @throws IllegalArgumentException
     *****************************************************************/
    public void addToExpression(String in) {
        // X triggers the stored expression to be added to the expression
        if (in.equals("X")) {
            expression = expression + stored;
            return;
        }
        // Addition operator is added to the expression
        if (in.equals("+")) {
            // Throws IllegalArgumentException if previous character is not a valid token for the operator
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        // Subtraction operator is added to the expression
        if (in.equals("-")) {
            // Throws IllegalArgumentException if previous character is not a valid token for the operator
            if (expression.length() == 0) {

            } else if (((expression.charAt(expression.length() - 1) != ')' && expression.charAt(expression.length() - 1) != '(') && !Character.isDigit(expression.charAt(expression.length() - 1)))) {
                throw new IllegalArgumentException();
            }
        }
        // Division operator is added to the expression
        if (in.equals("/")) {
            // Throws IllegalArgumentException if previous character is not a valid token for the operator
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        // Multiplication operator is added to the expression
        if (in.equals("*")) {
            // Throws IllegalArgumentException if previous character is not a valid token for the operator
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        // Decimal point is added to the expression
        if (in.equals(".")) {
            // Throws IllegalArgumentException if previous character is not a valid token for the operator
            if (expression.length() == 0 || !Character.isDigit(expression.charAt(expression.length() - 1)) || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        // Exponentiation operator is added to the expression
        if (in.equals("^")) {
            // Throws IllegalArgumentException if previous character is not a valid token for the operator
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        // Square operator is added to the expression
        if (in.equals("^2")) {
            // Throws IllegalArgumentException if previous character is not a valid token for the operator
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        // Factorial operator is added to the expression
        if (in.equals("!")) {
            // Throws IllegalArgumentException if previous character is not a valid token for the operator
            if (expression.length() == 0 || (!Character.isDigit(expression.charAt(expression.length() - 1)) && expression.charAt(expression.length() - 1) != ')') || expression.length() == 0) {
                throw new IllegalArgumentException();
            }
        }
        if (in.equals("NaN")) {
            throw new IllegalArgumentException();
        }
        expression = expression + in;
    }

    /*****************************************************************
     Clears the current expression/result.
     *****************************************************************/
    public void clear() {
        expression = "";
    }

    /*****************************************************************
     Acts as a backspace key.
     *****************************************************************/
    public void del() {
        // Avoid accessing any elements if array is empty
        if (expression.length() == 0) {
            return;
        }
        expression = expression.substring(0, expression.length() - 1);
    }

    /*****************************************************************
     Returns the expression.
     @return String
     *****************************************************************/
    public String getExpression() {
        return expression;
    }

    /*****************************************************************
     Returns the previous input.
     @return String
     *****************************************************************/
    public String getPrevInput() {
        // Avoid accessing any elements if array is empty
        if (pastInput.isEmpty()) {
            return "";
        }
        return pastInput.remove(pastInput.size() - 1);

    }

    /*****************************************************************
     Returns the last calculated answer.
     @return double
     *****************************************************************/
    public double getCurrentAns() {
        return currentAns;
    }

    /*****************************************************************
     Returns the previous result.
     @return double
     *****************************************************************/
    public double getPrevResult() {
        if (pastResult.isEmpty()) {
            return 0.00;
        }
        return pastResult.remove(pastResult.size() - 1);
    }

    public String polySolve(double a, double b, double c) {
        // Double for storing numerator value of the determinant
        double der;
        // Double for storing the square root of determinant
        double root;
        // If a is zero, the equation is not quadratic
        if (a == 0) {
            throw new IllegalArgumentException();
        }

        // Calculate determinant.
        der = Math.pow(b, 2.0) - 4 * a * c;
        // Calculate square root of determinant
        root = Math.sqrt(Math.abs(der));

        // List both real root is determinant is positive
        if (der > 0) {
            return "x = " + ((-b + root) / (2 * a) + "\nx = " + (-b - root) / (2 * a));
        } else { // List real and imaginary roots if determinant is negative
            return "x = " + (-b / (2 * a) + " + i(" + root / (2 * a) + ")\nx = "  + -b / (2 * a) + " - i(" + root / (2 * a)) + ")";
        }
    }

    /*****************************************************************
     Stores the variable.
     *****************************************************************/
    public void storeInVar() {
        stored = expression;
    }
}
