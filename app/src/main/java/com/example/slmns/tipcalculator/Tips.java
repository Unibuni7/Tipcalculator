package com.example.slmns.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class Tips extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat ProcentFormat =
            NumberFormat.getPercentInstance();


    private double Amount = 0.0; // bill amount entered by the user
    private double Procent = 0.15; // initial tip percentage
    private TextView Amount_text; // shows formatted bill amount
    private TextView Procent_text; // shows tip percentage
    private EditText Tip_text; // shows calculated tip amount
    private EditText Total_text; // shows calculated total bill amount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        // get references to programmatically manipulated TextViews
        Amount_text = (EditText) findViewById(R.id.Amountnum);
        Procent_text = (TextView) findViewById(R.id.Procenttxt);
        Tip_text = (EditText) findViewById(R.id.Tipnum);
        Total_text = (EditText) findViewById(R.id.Totalnum);
        Tip_text.setText(currencyFormat.format(0));
        Total_text.setText(currencyFormat.format(0));

        // set amountEditText's TextWatcher
        //TextWatcher is used to keep watch on the EditText content while user inputs the data.
        // It allows you to keep track on each character when entered on EditText.
        EditText AmountEditText =
                (EditText) findViewById(R.id.Amounttxt);
        AmountEditText.addTextChangedListener((TextWatcher) AmountEditText);

        EditText TipEditText =
                (EditText) findViewById(R.id.Tipnum);
        TipEditText.addTextChangedListener((TextWatcher) TipEditText);

        EditText TotalEditText =
                (EditText) findViewById(R.id.Totalnum);
        TotalEditText.addTextChangedListener((TextWatcher) TotalEditText);

        // set percentSeekBar's OnSeekBarChangeListener
        SeekBar Procentseekbar =
                (SeekBar) findViewById(R.id.Procentbar);
        Procentseekbar.setOnSeekBarChangeListener(seekBarListener);
    }

    private void calculate() {
        // format Procent and display in Procent textview
        Procent_text.setText(ProcentFormat.format(Procent));

        // calculate the tip and total
        double tip = Amount * Procent;
        double total = Amount + tip;

        // display tip and total formatted as currency
        Tip_text.setText(currencyFormat.format(tip));
        Total_text.setText(currencyFormat.format(total));
    }

    // listener object for the SeekBar's progress changed events
    private final SeekBar.OnSeekBarChangeListener seekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Procent = progress / 100.0; // set percent based on progress
                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };


    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get bill amount and display currency formatted value
                Amount = Double.parseDouble(s.toString()) / 100.0;
                Amount_text.setText(currencyFormat.format(Amount));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                Amount_text.setText("");
                Amount = 0.0;
            }

            calculate(); // update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };
}
