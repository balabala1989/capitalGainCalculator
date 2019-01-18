package balajirajagopal.com.capitalgaincalculator.custom;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;

import balajirajagopal.com.capitalgaincalculator.activity.GoldCalcActivity;
import balajirajagopal.com.capitalgaincalculator.activity.SharesCalcActivity;
import balajirajagopal.com.capitalgaincalculator.enums.DATETYPE;
import balajirajagopal.com.capitalgaincalculator.activity.MutualFundsCalcActivity;

public class CustomDatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Activity activity;
    private EditText editText;
    private int day;
    private int month;
    private int year;
    /*
    * Reason for */
    private DATETYPE natureOfEditText;

    public CustomDatePicker(Activity activity, int editTexTResourceID, DATETYPE natureOfEditText) {
        this.activity = activity;
        this.editText = (EditText) activity.findViewById(editTexTResourceID);
        this.editText.setOnClickListener(this);
        this.natureOfEditText = natureOfEditText;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        this.day = dayOfMonth;
        this.month = month;
        this.year = year;
        updateEditBox();
    }

    @Override
    public void onClick(View v) {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialogBox = new DatePickerDialog(activity,this,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        Calendar cal = Calendar.getInstance();
        if(DATETYPE.SALE_DATE.equals(this.natureOfEditText)) {
            if(activity instanceof MutualFundsCalcActivity){
                dialogBox = ((MutualFundsCalcActivity) activity).setMinimumSaleDate(dialogBox);
            }
            else if(activity instanceof GoldCalcActivity){
                dialogBox = ((GoldCalcActivity) activity).setMinimumSaleDate(dialogBox);
            }
            else if (activity instanceof SharesCalcActivity){
                dialogBox = ((SharesCalcActivity) activity).setMinimumSaleDate(dialogBox);
            }
        }

        dialogBox.getDatePicker().setMaxDate(cal.getTimeInMillis());
        dialogBox.show();

    }

    private void updateEditBox(){
        this.editText.setText(String.valueOf(this.day) + "-" + String.valueOf(this.month + 1) + "-" + String.valueOf(this.year)) ;

        if(DATETYPE.PURCHASE_DATE.equals(this.natureOfEditText)){
            if(activity instanceof MutualFundsCalcActivity){
                ((MutualFundsCalcActivity) activity).resetSaleDateOnChangeOfPurchaseDate(editText.getText().toString());
                ((MutualFundsCalcActivity) activity).enableFairMarketValueEditText(editText.getText().toString());
            }
            else if(activity instanceof GoldCalcActivity){
                ((GoldCalcActivity) activity).resetSaleDateOnChangeOfPurchaseDate(editText.getText().toString());
                ((GoldCalcActivity) activity).enableFairMarketValueEditText(editText.getText().toString());
            }
            else if(activity instanceof SharesCalcActivity){
                ((SharesCalcActivity) activity).resetSaleDateOnChangeOfPurchaseDate(editText.getText().toString());
                ((SharesCalcActivity) activity).enableFairMarketValueEditText(editText.getText().toString());
            }
        }
    }


}
