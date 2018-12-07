package balajirajagopal.com.capitalgaincalculator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;

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
        }
    }


}
