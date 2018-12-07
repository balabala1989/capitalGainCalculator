package balajirajagopal.com.capitalgaincalculator.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import balajirajagopal.com.capitalgaincalculator.common.FontCache;
import balajirajagopal.com.capitalgaincalculator.common.IndexationCache;
import balajirajagopal.com.capitalgaincalculator.R;

public class CapitalGainCalculatorUtils {

    private final int BASEYEAR = 2001;
    private final int MAX_DECIMAL = 2;
    private final String BEFORE2001 = "Before 2001";
    public  final String rupeeSymbol = "Rs. ";
    private static String[] monthsInString = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static Integer[] monthsInInteger = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private static HashMap<Integer,String> intToStringMonth = new HashMap<Integer,String>();
    private static HashMap<String, Integer> stringToIntMonth =  new HashMap<String, Integer>();
    public long yearDiff = 0;
    public long monthDiff = 0;
    public long daysDiff = 0;
    public long duration = 0;

    static{
        if(intToStringMonth.isEmpty()) {
            for (Integer i : monthsInInteger) {
                intToStringMonth.put(i, monthsInString[i-1]);
            }
        }

        if(stringToIntMonth.isEmpty()) {
            for (Integer i : monthsInInteger) {
                stringToIntMonth.put(monthsInString[i-1],i);
            }
        }
    }


   /* Calculate the Sale Year value
     * In India Financial year is from Apr to Mar.
     * So if the app is opened before March make year as Current Year -1 and Currenct Year otherwise
     * make the year as current year and current year + 1
     */
    public String[] calculateSaleYear()
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
    public String[] caluclatePurchaseYears()
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

    public TableRow createHeaderRow(TableRow headerTableRow, TextView headerTextView, String stringMessage){
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
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);
        params.span = 2;
        params.setMargins(10,10,10,0);
        headerTableRow.addView(headerTextView,0, params);

        return headerTableRow;
    }

    /*
     * Sets textview attributes for data rows.
     */

    public void setTextViewAttributes(TextView textView, String stringMessage, int backgroundColorChanger, int leftMargin, int rightMargin){
        textView.setText(stringMessage);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);
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

    public TableRow createSpaceBetweenTables(Context context){
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


    /*
     * Replaces comma in the edittext with empty character so that can be converted to number
     */

    public String replaceComma(String stringText){
        return stringText.replaceAll(",", "");
    }

    /*
     * Format amount to comma separated for visual representation
     */

    public String formatAmount(String amountString){
        String formattedString;
        if (amountString.contains(".")) {
            formattedString = formatDecimal(amountString);
        } else {
            formattedString = formatInteger(amountString);
        }

        return formattedString;
    }

    /*
    * Convert integer month to String/English month. Example - 1 - Jan, 2 - Feb etc
    */

    public String getEnglishNameForIntegerMonth(Integer month){
        return intToStringMonth.get(month);
    }

    /*
     * Convert String/English month to Integer. Example - Jan - 1, Feb - 2 etc
     */

    public Integer getIntegerForEnglishMonth(String month){
        return stringToIntMonth.get(month);
    }

    /*
    *  Computes the difference between two dates - purchase and sale date
    */
    public String calculateDifferenceInPurchaseAndSaleDate(String purchaseDate, String saleDate){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date purchaseDay = null;
        Date saleDay = null;

        try {
            purchaseDay = format.parse(purchaseDate);
            saleDay = format.parse(saleDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        duration = saleDay.getTime() - purchaseDay.getTime();
        long diffInDays = TimeUnit.DAYS.convert(duration,TimeUnit.MILLISECONDS);

        yearDiff = diffInDays / 365;
        diffInDays = diffInDays % 365;

        monthDiff = diffInDays / 30;
        diffInDays = diffInDays % 30;

        daysDiff = diffInDays;

        StringBuffer stringBuffer = new StringBuffer();

        if(yearDiff > 0)
            stringBuffer.append(String.valueOf(yearDiff)).append(yearDiff > 1 ? " year(s), " : " year, ");
        if(monthDiff > 0)
            stringBuffer.append(String.valueOf(monthDiff)).append(monthDiff > 1 ? " month(s), " : " month, ");

        stringBuffer.append(String.valueOf(daysDiff)).append(daysDiff > 1 ? " day(s)" : " day");

        String displayValue = stringBuffer.toString();
        displayValue = displayValue.charAt(displayValue.length() - 2) == ',' ? displayValue.substring(0,displayValue.length() - 2) : displayValue;
        return displayValue;
    }

    /*
    *  Computes to find if the fair market price had to be enabled or not
    */

    public boolean fairMarketPriceRequired(String purchaseDate, String indexDate){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date purchaseDay = null;
        Date indexDay = null;

        try {
            purchaseDay = format.parse(purchaseDate);
            indexDay = format.parse(indexDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diffInDuration = indexDay.getTime() - purchaseDay.getTime();
        if(diffInDuration > 0)
            return true;
        else
            return false;
    }

    /*
    * Computes difference of two dates and returns the duration between them
    */

    public long differenceOfDates(String firstDate, String secondDate){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date first = null;
        Date second = null;

        try {
            first = format.parse(firstDate);
            second = format.parse(secondDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

        long duration = first.getTime() - second.getTime();

        return duration;
    }

    private String formatInteger(String str) {
        BigDecimal parsed = new BigDecimal(str);
        DecimalFormat formatter =
                new DecimalFormat( "##,##,##,##,###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(parsed);
    }

    private String formatDecimal(String str) {
        if (str.equals(".")) {
            return ".";
        }
        BigDecimal parsed = new BigDecimal(str);
        // example pattern #,###.00
        DecimalFormat formatter = new DecimalFormat("##,##,##,##,###." + getDecimalPattern(str),
                new DecimalFormatSymbols(Locale.US));
        formatter.setRoundingMode(RoundingMode.DOWN);
        return formatter.format(parsed);
    }

    /**
     * It will return suitable pattern for format decimal
     * For example: 10.2 -> return 0 | 10.23 -> return 00, | 10.235 -> return 000
     */
    private String getDecimalPattern(String str) {
        int decimalCount = str.length() - str.indexOf(".") - 1;
        StringBuilder decimalPattern = new StringBuilder();
        for (int i = 0; i < decimalCount && i < MAX_DECIMAL; i++) {
            decimalPattern.append("0");
        }
        return decimalPattern.toString();
    }

    /*
    * Get the cost inflation index if a date is provided
    */

    public Long getIndexValue(String date, Context context){

        String[] splitDate = date.split("-");
        Long month = Long.parseLong(splitDate[1]);
        Long year = Long.parseLong(splitDate[2]);
        String yearIndex;
        if(month >= 4) //Check if the month is apr and beyond
        {
            yearIndex = String.valueOf(year) + "-" + String.valueOf(year + 1);
        }
        else
        {
            yearIndex = String.valueOf(year - 1) + "-" + String.valueOf(year);
        }

        return IndexationCache.get(yearIndex,context);
    }
}
