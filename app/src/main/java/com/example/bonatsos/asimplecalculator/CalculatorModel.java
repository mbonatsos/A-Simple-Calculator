package com.example.bonatsos.asimplecalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CalculatorModel class contains the calculator logic.
 */
public class CalculatorModel {

    //Operations
    private static final CharSequence ADDITION = "+";
    private static final CharSequence SUBTRACTION = "-";
    private static final CharSequence MULTIPLICATION = "*";
    private static final CharSequence DIVISION = "/";
    private static final CharSequence POW = "POW";
    private static final CharSequence NONE = "NONE";

    private static final String INFINITY = "Infinity";
    private static final String NEGATIVE_INFINITY = "-Infinity";
    private static final String NAN = "NaN";

    //Preferences
    private static final RoundingMode MODE = RoundingMode.HALF_UP;
    private static final int SCALE = 5;
    private static final int MAX_CHARACTER_COUNT = 15;

    private double result; //store the result of the calculation
    private double firstNumber; //used as first operand for the mathematical operation
    private double secondNumber; //used as first operand for the mathematical operation
    private CharSequence operator; //used as operator for the mathematical operation

    /**
     * These two strings are constructed via the user's interaction with the interface.
     * Contoller (MainActivity) parses these strings to the View. (TextView)
     */
    private String topPlaceholder = "";
    private String bottomPlaceholder = "";

    public CharSequence getTopPlaceholder() {
        return topPlaceholder;
    }
    public CharSequence getBottomPlaceholder() {
        return bottomPlaceholder;
    }


    public CalculatorModel() {

        //initialize values
        result = Double.NaN;
        firstNumber = Double.NaN;
        secondNumber = Double.NaN;
        operator = NONE;
    }

    /**
     * Constructs the bottomPlaceholder based on the given parameter.
     *
     * @param selectedNumber the number that the user clicked [0-9]
     * @return false if bottomPlaceholder's length exceeds the maximum character limit.
     */
    public boolean setNumber(CharSequence selectedNumber) {

        //If it exceeds the character limit
        if (bottomPlaceholder.length() > MAX_CHARACTER_COUNT) {
            return false;
        }

        //if it isn't a finite number
        if (!isFiniteNumber(bottomPlaceholder)) {
            //set the number to the string
            bottomPlaceholder = selectedNumber.toString();
        } else if (bottomPlaceholder.equals("0")) {
            //if it is equal to zero
            //append the number with a decimal point
            bottomPlaceholder = bottomPlaceholder.concat("." + selectedNumber);
        } else {
            //if it is any other number
            //append to the number to the string
            bottomPlaceholder = bottomPlaceholder.concat(selectedNumber.toString());
        }
        return true;
    }

    /**
     * Sets the operator and constructs the topPlaceholder string.
     *
     * It also calculates the result if both
     * bottomPlaceholder and topPlaceholder contain elements.
     *
     * @param selectedOperator the operator that user clicked [*,/,+,-,POW]
     */
    public void setOperator(CharSequence selectedOperator) {

        if (bottomPlaceholder.length() == 0 && topPlaceholder.length() == 0) {
            return;
        }

        //if bottomPlaceholder isn't a finite number or is equal to "-"
        if (!isFiniteNumber(bottomPlaceholder) || bottomPlaceholder.equals("-")) {
            return;
        }

        //If both strings have elements
        if (topPlaceholder.length() > 0 && bottomPlaceholder.length() > 0) {

            //store the elements of bottomPlaceholder to secondNumber variable
            secondNumber = Double.parseDouble(bottomPlaceholder.toString());
            //calculate the result variable
            calculate();
            //set the selected operator
            operator = selectedOperator;

            //If the result is not a valid number
            if (Double.isNaN(result) || Double.isInfinite(result)) {
                //clear top string
                topPlaceholder = "";
                //parse the result from calculation to bottomPlaceholder
                bottomPlaceholder = Double.toString(result);
                resetValues();
                return;
            } else {  //if it is a valid number
                //store the result
                firstNumber = result;
                //clear bottom string
                bottomPlaceholder = "";
            }
        } else if (topPlaceholder.length() > 0 && bottomPlaceholder.length() == 0) {
            //set the new operator
            operator = selectedOperator;
        } else if (topPlaceholder.length() == 0 && bottomPlaceholder.length() > 0) {
            //set the new operator
            operator = selectedOperator;
            //store the content of bottomPlaceholder to firstNumber
            firstNumber = Double.parseDouble(bottomPlaceholder.toString());
            //clear bottomPlaceholder
            bottomPlaceholder = "";
        }

        //construct the topPlaceHolder
        if (operator.equals(POW)) {
            topPlaceholder = "(" + Double.toString(firstNumber) + ")^";
        } else {
            topPlaceholder = Double.toString(firstNumber) + operator;
        }
    }

    /**
     * Removes the last element of bottomPlaceholder's string.
     * <p>
     * If the string has this pattern -2 or -5 or
     * If the string is equal to 0. or -0. or
     * If the string is not a finite number , the whole string (bottomPlaceholder) is cleared
     */
    public void executeBack() {

        if (bottomPlaceholder.length() == 0) {
            return;
        }

        //If it is a finite number
        if (isFiniteNumber(bottomPlaceholder)) {

            //and there is a pattern [-number] ex -1 or -5
            if (bottomPlaceholder.length() == 2 && bottomPlaceholder.charAt(0) == '-') {
                //clear string
                bottomPlaceholder = "";
            } else if (bottomPlaceholder.equals("0.") || bottomPlaceholder.equals("-0.")) {
                //or it is equal to "0." or "-0."
                //clear string
                bottomPlaceholder = "";
            } else {
                //remove the last element
                bottomPlaceholder = bottomPlaceholder.subSequence(0, bottomPlaceholder.length() - 1).toString();
            }

        } else {
            //if it is an infinite number
            //clear string
            bottomPlaceholder = "";
        }
    }

    /**
     * Clears the bottomPlaceholder and topPlaceholder.
     * Sets firstNumber, secondNumber and result to NaN
     * Finally sets the operator to NONE
     */
    public void executeClear() {

        if (bottomPlaceholder.length() > 0 || topPlaceholder.length() > 0) {

            resetValues();
            bottomPlaceholder = "";
            topPlaceholder = "";
        }
    }

    /**
     * Appends or removes the minus sign in the beginning of bottomPlaceholder's string
     * Does nothing if the string is equal to zero or if it is not a finite number
     */
    public void setSign() {

        //If the string is equal to zero or is infinite number
        if (bottomPlaceholder.equals("0") || !isFiniteNumber(bottomPlaceholder)) {
            return;
        }

        if (bottomPlaceholder.length() > 0) {
            //if it doesn't begin with '-'
            if (bottomPlaceholder.charAt(0) != '-') {
                //set '-' at the beginning
                bottomPlaceholder = "-" + bottomPlaceholder;
            } else {
                //remove the first element '-'
                bottomPlaceholder = bottomPlaceholder.subSequence(1, bottomPlaceholder.length()).toString();
            }
        }
    }

    /**
     * Sets a decimal point in the bottomPlaceholder
     * if appropriate conditions are met.
     */
    public void setDecimalPoint() {

        //If the string is equal to "-" or it is an infinite number
        if (bottomPlaceholder.equals("-") || !isFiniteNumber(bottomPlaceholder)) {
            return;
        }

        if (bottomPlaceholder.length() > 0) {
            //If it doesn't contain any '.' char
            if (bottomPlaceholder.indexOf(".") == -1) {

                //append decimal point to the string
                bottomPlaceholder = bottomPlaceholder.concat(".");
            }
        } else {
            //if the string is empty
            bottomPlaceholder = "0.";
        }
    }

    /**
     * Calculates the result and
     * sets the result to bottomPlaceHolder.
     * Then it clears the topPlaceHolder string and resets the values
     */
    public void executeResult() {

        //if either of the strings are empty or bottomPlaceholder is equal to '-'
        if (topPlaceholder.length() == 0 || bottomPlaceholder.length() == 0 || bottomPlaceholder.equals("-")) {
            return;
        }

        if (topPlaceholder.length() > 0 && bottomPlaceholder.length() > 0) {

            //store the content of bottomPlaceHolder to secondNumber
            secondNumber = Double.parseDouble(bottomPlaceholder.toString());
            //calculate the new result
            calculate();
            //store that result variable to the string
            bottomPlaceholder = Double.toString(result);
            //clear top string
            topPlaceholder = "";
            resetValues();
        }
    }

    /**
     * This method sets the result variable.
     * <p>
     * It uses firstNumber and secondNumber as operands.
     * The operation is determined by the value of operator.
     * <p>
     * If an error occurs, result is set to NaN.
     */
    private void calculate() {
        try {
            if (operator.equals(ADDITION)) {
                result = firstNumber + secondNumber;
            } else if (operator.equals(SUBTRACTION)) {
                result = firstNumber - secondNumber;
            } else if (operator.equals(MULTIPLICATION)) {
                result = firstNumber * secondNumber;
            } else if (operator.equals(DIVISION)) {
                result = firstNumber / secondNumber;
            } else if (operator.equals(POW)) {
                result = Math.pow(firstNumber, secondNumber);
            }

            //if the result is finite
            if (!Double.isInfinite(result)) {
                //round the result
                result = BigDecimal.valueOf(result).setScale(SCALE, MODE).doubleValue();
            }
        } catch (NumberFormatException ex) {
            result = Double.NaN;
        }
    }

    /**
     * Checks whether the given parameter is a finite or not
     *
     * @param str string to evaluate
     * @return true if the number is Finite
     */
    private boolean isFiniteNumber(String str) {

        if (str.equals(NEGATIVE_INFINITY) || str.equals(INFINITY) || str.equals(NAN)) {
            return false;
        } else {
            return true;
        }
    }

    private void resetValues() {
        firstNumber = Double.NaN;
        secondNumber = Double.NaN;
        result = Double.NaN;
        operator = NONE;
    }

}

