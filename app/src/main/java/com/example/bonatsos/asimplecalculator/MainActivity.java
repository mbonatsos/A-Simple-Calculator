package com.example.bonatsos.asimplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;

/**
 * MainActivity keeps CalculatorModel and textViews classes at sync
 */
public class MainActivity extends AppCompatActivity {

    private CalculatorModel model;
    private TextView topTextView;
    private TextView bottomTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up references
        topTextView = (TextView) findViewById(R.id.textView_main_expression);
        bottomTextView = (TextView) findViewById(R.id.textView_main_result);

        //set up keyboardView reference
        View keyboardView = findViewById(R.id.tableLayout_main_keyboard);
        //animate the keyboard view
        CalculatorStyle.animateView(keyboardView);
        //change view's fonts
        CalculatorStyle.overrideFonts(keyboardView, EasyFonts.robotoThin(keyboardView.getContext()));

        //instantiate model
        model = new CalculatorModel();
    }

    //Triggered by [0-9] buttons
    public void btnNumberClicked(View view) {
        CharSequence selectedNumber = ((Button) view).getText();

        boolean charactersWithinLimit = model.setNumber(selectedNumber);

        //if characters exceed the limit
        if (!charactersWithinLimit) {

            //show warning
            Toast.makeText(getApplicationContext(), R.string.warning_maxNumberCount, Toast.LENGTH_SHORT).show();
        }

        bottomTextView.setText(model.getBottomPlaceholder());
    }

    //Triggered by [/,+,-,*,POW] buttons
    public void btnOperatorClicked(View view) {
        CharSequence selectedOperator = ((Button) view).getText();
        model.setOperator(selectedOperator);
        bottomTextView.setText(model.getBottomPlaceholder());
        topTextView.setText(model.getTopPlaceholder());
    }

    //Triggered by CE button
    public void btnBackClicked(View view) {
        model.executeBack();
        bottomTextView.setText(model.getBottomPlaceholder());
    }

    //Triggered by SIGN button
    public void btnSignClicked(View view) {
        model.setSign();
        bottomTextView.setText(model.getBottomPlaceholder());
    }

    //Triggered by . button
    public void btnPointClicked(View view) {
        model.setDecimalPoint();
        bottomTextView.setText(model.getBottomPlaceholder());
    }

    //Triggered by = button
    public void btnResultClicked(View view) {

        model.executeResult();
        bottomTextView.setText(model.getBottomPlaceholder());
        topTextView.setText(model.getTopPlaceholder());
    }

    //Triggered by CLEAR button
    public void btnClearClicked(View view) {

        model.executeClear();
        bottomTextView.setText(model.getBottomPlaceholder());
        topTextView.setText(model.getTopPlaceholder());
    }

}
