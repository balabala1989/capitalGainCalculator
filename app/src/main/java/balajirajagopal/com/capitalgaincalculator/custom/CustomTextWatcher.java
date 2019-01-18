package balajirajagopal.com.capitalgaincalculator.custom;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import balajirajagopal.com.capitalgaincalculator.activity.GoldCalcActivity;
import balajirajagopal.com.capitalgaincalculator.activity.SharesCalcActivity;
import balajirajagopal.com.capitalgaincalculator.utils.CapitalGainCalculatorUtils;
import balajirajagopal.com.capitalgaincalculator.activity.MutualFundsCalcActivity;

public class CustomTextWatcher implements TextWatcher {
    EditText editText;
    AppCompatActivity classObject;
    private String previousCleanString;
    private static final int MAX_LENGTH = 15;
    private static final int MAX_DECIMAL = 2;

    public CustomTextWatcher(EditText editText, AppCompatActivity classObject) {
        this.editText = editText;
        this.classObject = classObject;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString();
        CapitalGainCalculatorUtils utils = new CapitalGainCalculatorUtils(false);
        // cleanString this the string which not contain prefix and ,
        String cleanString = str.replaceAll("[,]", "");
        // for prevent afterTextChanged recursive call
        if (cleanString.equals(previousCleanString) || cleanString.isEmpty()) {
            return;
        }
        previousCleanString = cleanString;

        String formattedString = utils.formatAmount(cleanString);

        editText.removeTextChangedListener(this); // Remove listener
        editText.setText(formattedString);
        handleSelection();
        editText.addTextChangedListener(this); // Add back the listener
        if (classObject instanceof MutualFundsCalcActivity){
            ((MutualFundsCalcActivity) classObject).checkButtonCanBeEnabled();
        }
        else if(classObject instanceof GoldCalcActivity){
            ((GoldCalcActivity) classObject).checkButtonCanBeEnabled();
        }
        else if(classObject instanceof SharesCalcActivity){
            ((SharesCalcActivity) classObject).checkButtonCanBeEnabled();
        }
    }

    private void handleSelection() {
        if (editText.getText().length() <= MAX_LENGTH) {
            editText.setSelection(editText.getText().length());
        } else {
            editText.setSelection(MAX_LENGTH);
        }
    }
}
