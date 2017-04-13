package com.example.slmns.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class Tips extends AppCompatActivity {

    // currency and percent formatter objects.
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double BillAmount = 0.0; // bill amount entered by the user
    private double Percent = 0.15; // initial tip percentage
    private TextView AmountTextView; // shows formatted bill amount
    private TextView PercentTextView; // shows tip percentage
    private TextView TipTextView; // shows calculated tip amount
    private TextView TotalTextView; // shows calculated total bill amount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        // get references to programmatically manipulated TextViews
        AmountTextView = (TextView) findViewById(R.id.AmountTextView);
        PercentTextView = (TextView) findViewById(R.id.PercentTipTextview);
        TipTextView = (TextView) findViewById(R.id.TipTextView);
        TotalTextView = (TextView) findViewById(R.id.TotalTextView);
        TipTextView.setText(currencyFormat.format(0));
        TotalTextView.setText(currencyFormat.format(0));

        // set amountEditText's TextWatcher
        //This method is called to notify you that, somewhere within s, the text has been changed.
        // It is legitimate to make further changes to s from this callback, but be careful not to get yourself into an infinite loop,
        // because any changes you make will cause this method to be called again recursively.
        EditText amountEditText =
                (EditText) findViewById(R.id.AmountEditText);
        amountEditText.addTextChangedListener(AmountEditTextWatcher);


        // set percentSeekBar's OnSeekBarChangeListener
        //A callback that notifies clients when the progress level has been changed.
        // This includes changes that were initiated by the user through a touch gesture or arrow key/trackball
        // as well as changes that were initiated programmatically.
        SeekBar percentSeekBar =
                (SeekBar) findViewById(R.id.PercentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    // calculate and display tip and total amounts
    private void calculate() {
        // format percent and display in percentTextView
        PercentTextView.setText(percentFormat.format(Percent));

        // calculate the tip and total
        double tip = BillAmount * Percent;
        double total = BillAmount + tip;

        // display tip and total formatted as currency
        TipTextView.setText(currencyFormat.format(tip));
        TotalTextView.setText(currencyFormat.format(total));
    }

    // listener object for the SeekBar's progress changed events
    private final SeekBar.OnSeekBarChangeListener seekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    Percent = progress / 100.0; // set percent based on progress
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
    private final TextWatcher AmountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get bill amount and display currency formatted value
                BillAmount = Double.parseDouble(s.toString()) / 100.0;
                AmountTextView.setText(currencyFormat.format(BillAmount));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                AmountTextView.setText("");
                BillAmount = 0.0;
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };
}







