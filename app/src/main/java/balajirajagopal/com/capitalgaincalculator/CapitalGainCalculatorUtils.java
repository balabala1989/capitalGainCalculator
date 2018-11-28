package balajirajagopal.com.capitalgaincalculator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class CapitalGainCalculatorUtils {

    private static final int BASEYEAR = 2001;
    private static final String BEFORE2001 = "Before 2001";

    /* Calculate the Sale Year value
     * In India Financial year is from Apr to Mar.
     * So if the app is opened before March make year as Current Year -1 and Currenct Year otherwise
     * make the year as current year and current year + 1
     */
    public static String[] calculateSaleYear()
    {
        String[] spinnerValue;

        String saleYearSpinnerValue = "";
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        if(month > 2)
            saleYearSpinnerValue = String.valueOf(year) + "-" + String.valueOf(year + 1);
        else
            saleYearSpinnerValue = String.valueOf(year - 1) + "-" + String.valueOf(year);

        spinnerValue = new  String[2];
        spinnerValue[0] = "";
        spinnerValue[1] = saleYearSpinnerValue;

        return spinnerValue;
    }

    /* Calculate the Purchase Year value
     * In India 2001-2002 is set as indexation base. So before 2001 there is no indexation
     * so restrict the year displayed to that. If it changes in future, please change here
     */
    public static String[] caluclatePurchaseYears()
    {
        String[] spinnerYears;
        ArrayList<String> yearList = new ArrayList<String>();
        int calculateYear = BASEYEAR;
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        yearList.add("");
        yearList.add(BEFORE2001);
        while(calculateYear < year){
            yearList.add(String.valueOf(calculateYear) + "-" + String.valueOf(calculateYear + 1));
            calculateYear++;
        }
        if(month > 2)
            yearList.add(String.valueOf(calculateYear) + "-" + String.valueOf(calculateYear + 1));

        spinnerYears = new String[yearList.size()];
        int index = 0;
        for(String item : yearList)
        {
            spinnerYears[index++] = item;
        }
        return spinnerYears;
    }

    /*
     * Creates the header row for the result of the capital gain computation
     * Will be used by all the result page. Result page creates a table like
     * appearance for the result combining input and result of the computation     *
     */

    public static TableRow createHeaderRow(TableRow headerTableRow, TextView headerTextView, String stringMessage){
        //Set text view attributes
        headerTextView.setText(stringMessage);
        headerTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        headerTextView.setBackgroundColor(Color.parseColor("#FF00574B"));
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
        headerTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20F);
        Typeface fontFace = FontCache.get("copse.ttf", headerTextView.getContext());
        headerTextView.setTypeface(fontFace,Typeface.BOLD_ITALIC);

        //set table row attributes
        headerTableRow.setLayoutParams(new TableRow.LayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)));
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 2;
        params.setMargins(10,10,10,0);
        headerTableRow.addView(headerTextView,0, params);

        return headerTableRow;
    }

    /*
     * Sets textview attributes for data rows.
     */

    public static void setTextViewAttributes(TextView textView, String stringMessage, int backgroundColorChanger, int leftMargin, int rightMargin){
        textView.setText(stringMessage);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(leftMargin,1,rightMargin,1);
        textView.setLayoutParams(params);
        if(backgroundColorChanger % 2 == 0)
            textView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        else
            textView.setBackgroundColor(Color.parseColor("#3D008577"));
        textView.setGravity(Gravity.LEFT);
        textView.setTextColor(Color.parseColor("#FF000000"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16F);
        Typeface fontFace = FontCache.get("enriqueta.ttf", textView.getContext());
        textView.setTypeface(fontFace,Typeface.NORMAL);
    }

    /*
    * Creates a dummt textview with specific height. This will act as space between two table details
    **/

    public static TableRow createSpaceBetweenTables(Context context){
        TableRow emptyTableRow = new TableRow(context);
        emptyTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView emptyTextView = new TextView(context);
        emptyTextView.setBackgroundResource(R.drawable.cell_border);

        linearLayout.addView(emptyTextView);
        emptyTableRow.addView(linearLayout);

        return emptyTableRow;
    }
}
