package com.example.bonatsos.asimplecalculator;

import org.junit.Test;

import static org.junit.Assert.*;


public class CalculatorModelTest {

    @Test
    public void executeBackOnNumber() throws Exception {

        CharSequence input = "0.5";
        CharSequence output;
        CharSequence expected = "0.";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("0");
        model.setDecimalPoint();
        model.setNumber("5");

        model.executeBack();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void executeBackOnNumberBeginningWithZeroAndHaveDecimalPoint() throws Exception {

        CharSequence input = "0.5";
        CharSequence output;
        CharSequence expected = "";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("0");
        model.setDecimalPoint();
        model.setNumber("5");

        model.executeBack();
        model.executeBack();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void executeBackTwice() throws Exception {

        CharSequence input = "";
        CharSequence output;
        CharSequence expected = "";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("0");
        model.setDecimalPoint();
        model.setNumber("5");

        model.executeBack();
        model.executeBack();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setSignOnNumber() throws Exception {

        //CharSequence input = "6059";
        CharSequence output;
        CharSequence expected = "-6059";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("6");
        model.setNumber("0");
        model.setNumber("5");
        model.setNumber("9");
        model.setSign();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setSignOnNumberTwice() throws Exception {

        //CharSequence input = "6059";
        CharSequence output;
        CharSequence expected = "6059";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("6");
        model.setNumber("0");
        model.setNumber("5");
        model.setNumber("9");
        model.setSign();
        model.setSign();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setSignOnEmpty() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "";

        CalculatorModel model = new CalculatorModel();

        model.setSign();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setSignOnZero() throws Exception {

        //CharSequence input = "0";
        CharSequence output;
        CharSequence expected = "0";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("0");
        model.setSign();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setDecimalPointOnEmpty() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "0.";

        CalculatorModel model = new CalculatorModel();

        model.setDecimalPoint();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setMultipleDecimalPoints() throws Exception {

        //CharSequence input = "532.5";
        CharSequence output;
        CharSequence expected = "532.5";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("5");
        model.setNumber("3");
        model.setNumber("2");
        model.setDecimalPoint();
        model.setNumber("5");
        model.setDecimalPoint();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void testOperationsWithSign() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "244.9";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("5");
        model.setOperator("+");
        model.setNumber("100");
        model.setOperator("/");
        model.setNumber("50");
        model.setOperator("POW");
        model.setNumber("5");
        model.setSign();
        model.setOperator("*");
        model.setNumber("10000");
        model.executeResult();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void backToEmptyAndThenSetOperator() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "11.0";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("6");
        model.setOperator("+");
        model.setNumber("5");
        model.executeBack();
        model.setOperator("+");
        model.setNumber("5");
        model.executeResult();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setSignAndBack() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "7.0";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("5");
        model.setSign();
        model.executeBack();
        model.setOperator("+");
        model.setNumber("6");
        model.setOperator("-");
        model.setNumber("10");
        model.setSign();
        model.executeBack();
        model.executeResult();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void pointToSignAndBack() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "-1.0";

        CalculatorModel model = new CalculatorModel();

        model.setNumber("5");
        model.setOperator("+");
        model.setNumber("6");
        model.setDecimalPoint();
        model.setSign();
        model.executeBack();
        model.executeResult();
        model.executeResult();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void sequenceOfZeroesAndDecimal() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "0.13889";
        CalculatorModel model = new CalculatorModel();

        model.setNumber("0");
        model.setNumber("0");
        model.setNumber("6");
        model.setDecimalPoint();
        model.setSign();
        model.setOperator("+");
        model.setNumber("6");
        model.setSign();
        model.executeBack();
        model.executeResult();
        model.executeBack();
        model.setSign();
        model.setOperator("/");
        model.setDecimalPoint();
        model.executeBack();
        model.executeResult();
        model.executeResult();
        model.setOperator("POW");
        model.setNumber("2");
        model.setSign();
        model.setOperator("+");
        model.executeResult();
        model.setOperator("*");
        model.setNumber("0");
        model.setNumber("0");
        model.setNumber("0");
        model.setNumber("0");
        model.setNumber("5");
        model.executeResult();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void executeResultOnInfinite() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "5.0";
        CalculatorModel model = new CalculatorModel();

        model.setNumber("5");
        model.setOperator("/");
        model.setNumber("0");
        model.executeResult();
        model.setSign();
        model.setDecimalPoint();
        model.executeResult();
        model.setOperator("+");
        model.setNumber("2");
        model.setOperator("+");
        model.setNumber("3");
        model.executeResult();


        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void executeResultWithMaxCharacters() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "Infinity";
        CalculatorModel model = new CalculatorModel();

        for (int i = 0; i < 30; i++) {
            model.setNumber("5");
        }
        model.setOperator("POW");

        model.setNumber("999999");

        model.executeResult();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setOperatorOnInfinity() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "-Infinity";
        CalculatorModel model = new CalculatorModel();

        model.setNumber("5");
        model.setOperator("/");
        model.setNumber("0");
        model.setOperator("+");
        model.setNumber("1");
        model.setSign();
        model.setOperator("/");
        model.setNumber("0");
        model.setOperator("*");

        model.executeResult();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setOperatorOnNaN() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "NaN";
        CalculatorModel model = new CalculatorModel();

        model.setNumber("0");
        model.setOperator("/");
        model.setNumber("0");
        model.setOperator("+");
        model.executeResult();

        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }

    @Test
    public void setDecimalOnInfinity() throws Exception {

        //CharSequence input = "";
        CharSequence output;
        CharSequence expected = "1.0";
        CalculatorModel model = new CalculatorModel();

        model.setNumber("0");
        model.setOperator("/");
        model.setNumber("0");
        model.setOperator("+");
        model.setDecimalPoint();
        model.executeBack();
        model.setDecimalPoint();
        model.setNumber("5");
        model.setOperator("POW");
        model.setNumber("0");
        model.executeResult();


        output = model.getBottomPlaceholder();
        assertEquals(expected, output);
    }


}