package balajirajagopal.com.capitalgaincalculator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MutualFundReport extends AppCompatActivity {

    private static Intent intent;
    public String MUTUAL_FUND_SALEYEAR;
    public String MUTUAL_FUND_SALEAMOUNT;
    public String MUTUAL_FUND_PURCHASEYEAR;
    public String MUTUAL_FUND_PURCHASEAMOUNT;
    public String MUTUAL_FUNDS_NUMBEROFUNITS;
    public String MUTUAL_FUNDS_SALEEXPENSE;
    public String MUTUAL_FUNDS_PURCHASEEXPENSE;
    public String MUTUAL_FUNDS_TYPE;
    public String MUTUAL_FUNDS_FAIRMARKETVALUE;
    public String MUTUAL_FUNDS_FAIRMARKET_HEADERMESSAGE;

    private int mutualFundResultTableLayoutIndex = 0;
    private TableLayout mutualFundResultTableLayout;
    private final String NOT_APPLICABLE="Nil";
    private Double totalSaleAmount;
    private Double totalPurchaseAmount;
    private String capitalGainAmountTextView;
    private Double capitalGainAmount;
    private String capitalGainAmountDisplayValue;
    private String capitalGainTaxPercentDisplayValue;
    private Double capitalGainTaxAmount;
    private String getCapitalGainTaxAmountDisplayValue;
    private Double netProfit;
    private String netProfitDisplayValue;
    private String gainTypeDisplayValue;
    private GAINTYPE gaintype;
    private static CapitalGainCalculatorUtils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_fund_report);

        utils = new CapitalGainCalculatorUtils();

        intent = getIntent();
        //Transfer input data from previous activity to this activity
        loadInputData();

        mutualFundResultTableLayout = (TableLayout) findViewById(R.id.mutualFundResultTableLayout);
        mutualFundResultTableLayout.removeAllViews();
        mutualFundResultTableLayout.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT));

        //Load the Asset Details into Table. Data is received as Input from previous Activity
        loadAssetDetails();

        //Create dummy space
        mutualFundResultTableLayout.addView(utils.createSpaceBetweenTables(this),mutualFundResultTableLayoutIndex++);

        //Load the Purchase Details into Table. Data is received as Input from previous Activity
        loadPurchaseDetails();

        //Create dummy space
        mutualFundResultTableLayout.addView(utils.createSpaceBetweenTables(this),mutualFundResultTableLayoutIndex++);

        //Load the Fair Market Value details
        if(MUTUAL_FUNDS_FAIRMARKETVALUE != null && MUTUAL_FUNDS_FAIRMARKETVALUE.length() != 0){
            loadFairMarketValueDetails();
            //Create dummy space
            mutualFundResultTableLayout.addView(utils.createSpaceBetweenTables(this),mutualFundResultTableLayoutIndex++);

        }

        //Load the Sale Details into Table. Data is received as Input from previous Activity
        loadSaleDetails();

        //Create dummy space
        mutualFundResultTableLayout.addView(utils.createSpaceBetweenTables(this),mutualFundResultTableLayoutIndex++);

        //Load the Capital Gain computed into Table. Data is received as Input from previous Activity
        computeCapitalGain();
    }

    private void loadAssetDetails(){
        int backGroundColorChange = 0;
        //Create the header row
        mutualFundResultTableLayout.addView(utils.createHeaderRow(new TableRow(this), new TextView(this), getString(R.string.assetdetails_header_textview)),mutualFundResultTableLayoutIndex++);

        TableRow assetTableRow = new TableRow(this);

        //Create data rows

        //Asset Type
        TextView assetTypeTextView = new TextView(this);
        TextView assetTypeDataTextView = new TextView(this);
        utils.setTextViewAttributes(assetTypeTextView,getString(R.string.assettype_tablerow_textview),backGroundColorChange,10, 1);
        int dataStringResourceID;
        if(MutualFundsCalcActivity.DEBT_MUTUAL_FUND.equals(MUTUAL_FUNDS_TYPE))
            dataStringResourceID = R.string.debt_mutualfund_assettype_textview;
        else
            dataStringResourceID = R.string.equity_mutualfund_assettype_textview;
        utils.setTextViewAttributes(assetTypeDataTextView,getString(dataStringResourceID),backGroundColorChange++,1, 10);

        assetTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        assetTableRow.addView(assetTypeTextView,0);
        assetTableRow.addView(assetTypeDataTextView,1);

        mutualFundResultTableLayout.addView(assetTableRow,mutualFundResultTableLayoutIndex++);

        //Number of Units
        TextView numberOfUnitsTextView = new TextView(this);
        TextView numberOfUnitsDataTextView = new TextView(this);
        utils.setTextViewAttributes(numberOfUnitsTextView,getString(R.string.numberofunits_tablerow_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(numberOfUnitsDataTextView,MUTUAL_FUNDS_NUMBEROFUNITS,backGroundColorChange++,1, 10);

        assetTableRow = new TableRow(this);
        assetTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        assetTableRow.addView(numberOfUnitsTextView,0);
        assetTableRow.addView(numberOfUnitsDataTextView,1);

        mutualFundResultTableLayout.addView(assetTableRow,mutualFundResultTableLayoutIndex++);
    }

    private void loadPurchaseDetails(){
        int backGroundColorChange = 0;
        boolean isPurchaseExpenseProvided = false;
        //Create the header row
        mutualFundResultTableLayout.addView(utils.createHeaderRow(new TableRow(this), new TextView(this), getString(R.string.purchasedetails_header_textview)),mutualFundResultTableLayoutIndex++);

        TableRow purchaseTableRow = new TableRow(this);

        //Create data rows

        //Purchase Year
        TextView purchaseYearTextView = new TextView(this);
        TextView purchaseYearDataTextView = new TextView(this);
        String purchaseYearData = "";
        String[] purchaseData = MUTUAL_FUND_PURCHASEYEAR.split("-");
        purchaseYearData = new StringBuffer("").append(purchaseData[0]).append("-").append(utils.getEnglishNameForIntegerMonth(Integer.parseInt(purchaseData[1]))).append("-").append(purchaseData[2]).toString();

        utils.setTextViewAttributes(purchaseYearTextView,getString(R.string.purchaseyear_tablerow_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(purchaseYearDataTextView,purchaseYearData,backGroundColorChange++,1, 10);

        purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchaseYearTextView,0);
        purchaseTableRow.addView(purchaseYearDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);


        //Purchase Price per Unit
        TextView purchasePricePerUnitTextView = new TextView(this);
        TextView purchasePricePerUnitDataTextView = new TextView(this);
        utils.setTextViewAttributes(purchasePricePerUnitTextView,getString(R.string.purchaseprice_perunit_tablerow_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(purchasePricePerUnitDataTextView,utils.rupeeSymbol + MUTUAL_FUND_PURCHASEAMOUNT,backGroundColorChange++,1, 10);

        purchaseTableRow = new TableRow(this);
        purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchasePricePerUnitTextView,0);
        purchaseTableRow.addView(purchasePricePerUnitDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);

        //Purchase Price for all  Unit
        TextView purchasePriceTotalUnitsTextView = new TextView(this);
        TextView purchasePriceTotalUnitsDataTextView = new TextView(this);
        utils.setTextViewAttributes(purchasePriceTotalUnitsTextView,getString(R.string.purchaseprice_costofunit_tablerow_textview),backGroundColorChange,10, 1);
        Double purchaseAmount = Double.parseDouble(utils.replaceComma(MUTUAL_FUND_PURCHASEAMOUNT));
        Long numberOfUnits = Long.parseLong(utils.replaceComma(MUTUAL_FUNDS_NUMBEROFUNITS));

        Double purchaseCost = purchaseAmount * numberOfUnits;

        utils.setTextViewAttributes(purchasePriceTotalUnitsDataTextView,utils.rupeeSymbol + utils.formatAmount(String.valueOf(purchaseCost)),backGroundColorChange++,1, 10);

        purchaseTableRow = new TableRow(this);
        purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchasePriceTotalUnitsTextView,0);
        purchaseTableRow.addView(purchasePriceTotalUnitsDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);


        //Purchase Expense

        if(MUTUAL_FUNDS_PURCHASEEXPENSE != null && !MUTUAL_FUNDS_PURCHASEEXPENSE.equals(""))
            isPurchaseExpenseProvided = true;
        TextView purchaseExpenseTextView = new TextView(this);
        TextView purchaseExpenseDataTextView = new TextView(this);
        utils.setTextViewAttributes(purchaseExpenseTextView,getString(R.string.purchase_expense_textview),backGroundColorChange,10, 1);
        String purchaseExpenseString =  isPurchaseExpenseProvided ? utils.rupeeSymbol + MUTUAL_FUNDS_PURCHASEEXPENSE : NOT_APPLICABLE;
        utils.setTextViewAttributes(purchaseExpenseDataTextView,purchaseExpenseString,backGroundColorChange++,1, 10);

        purchaseTableRow = new TableRow(this);
        purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchaseExpenseTextView,0);
        purchaseTableRow.addView(purchaseExpenseDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);

        //Total Purchase cost
        TextView purchaseTotalCostTextView = new TextView(this);
        TextView purchaseTotalCostDataTextView = new TextView(this);
        utils.setTextViewAttributes(purchaseTotalCostTextView,getString(R.string.purchaseprice_total_tablerow_textview),backGroundColorChange,10, 1);
        Double purchaseExpense = isPurchaseExpenseProvided ? Double.parseDouble(utils.replaceComma(MUTUAL_FUNDS_PURCHASEEXPENSE)) : new Double(0);
        totalPurchaseAmount = purchaseCost + purchaseExpense;
        utils.setTextViewAttributes(purchaseTotalCostDataTextView,utils.rupeeSymbol + utils.formatAmount(String.valueOf(totalPurchaseAmount)),backGroundColorChange++,1, 10);

        purchaseTableRow = new TableRow(this);
        purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchaseTotalCostTextView,0);
        purchaseTableRow.addView(purchaseTotalCostDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);
    }

    private void loadSaleDetails(){
        int backGroundColorChange = 0;
        boolean saleExpenseProvided = false;
        //Create the header row
        mutualFundResultTableLayout.addView(utils.createHeaderRow(new TableRow(this), new TextView(this), getString(R.string.saledetails_header_textview)),mutualFundResultTableLayoutIndex++);

        TableRow saleTableRow = new TableRow(this);

        //Create data rows

        //Purchase Year
        TextView saleYearTextView = new TextView(this);
        TextView saleYearDataTextView = new TextView(this);

        String saleYearData = "";
        String[] saleData = MUTUAL_FUND_SALEYEAR.split("-");
        saleYearData = new StringBuffer("").append(saleData[0]).append("-").append(utils.getEnglishNameForIntegerMonth(Integer.parseInt(saleData[1]))).append("-").append(saleData[2]).toString();


        utils.setTextViewAttributes(saleYearTextView,getString(R.string.saleyear_tablerow_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(saleYearDataTextView,saleYearData,backGroundColorChange++,1, 10);

        saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(saleYearTextView,0);
        saleTableRow.addView(saleYearDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);


        //Purchase Price per Unit
        TextView salePricePerUnitTextView = new TextView(this);
        TextView salePricePerUnitDataTextView = new TextView(this);
        utils.setTextViewAttributes(salePricePerUnitTextView,getString(R.string.saleprice_perunit_tablerow_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(salePricePerUnitDataTextView,utils.rupeeSymbol + MUTUAL_FUND_SALEAMOUNT,backGroundColorChange++,1, 10);

        saleTableRow = new TableRow(this);
        saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(salePricePerUnitTextView,0);
        saleTableRow.addView(salePricePerUnitDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);

        //Purchase Price for all  Unit
        TextView salePriceTotalUnitsTextView = new TextView(this);
        TextView salePriceTotalUnitsDataTextView = new TextView(this);
        utils.setTextViewAttributes(salePriceTotalUnitsTextView,getString(R.string.saleprice_costofunit_tablerow_textview),backGroundColorChange,10, 1);
        Double saleAmount = Double.parseDouble(utils.replaceComma(MUTUAL_FUND_SALEAMOUNT));
        Long numberOfUnits = Long.parseLong(utils.replaceComma(MUTUAL_FUNDS_NUMBEROFUNITS));

        Double saleCost = saleAmount * numberOfUnits;

        utils.setTextViewAttributes(salePriceTotalUnitsDataTextView,utils.rupeeSymbol + utils.formatAmount(String.valueOf(saleCost)),backGroundColorChange++,1, 10);

        saleTableRow = new TableRow(this);
        saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(salePriceTotalUnitsTextView,0);
        saleTableRow.addView(salePriceTotalUnitsDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);


        //Purchase Expense
        if(MUTUAL_FUNDS_SALEEXPENSE != null && !MUTUAL_FUNDS_SALEEXPENSE.equals(""))
            saleExpenseProvided = true;
        TextView saleExpenseTextView = new TextView(this);
        TextView saleExpenseDataTextView = new TextView(this);
        utils.setTextViewAttributes(saleExpenseTextView,getString(R.string.sale_expense_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(saleExpenseDataTextView,saleExpenseProvided ? utils.rupeeSymbol + MUTUAL_FUNDS_SALEEXPENSE : NOT_APPLICABLE,backGroundColorChange++,1, 10);

        saleTableRow = new TableRow(this);
        saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(saleExpenseTextView,0);
        saleTableRow.addView(saleExpenseDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);

        //Total Purchase cost
        TextView saleTotalCostTextView = new TextView(this);
        TextView saleTotalCostDataTextView = new TextView(this);
        utils.setTextViewAttributes(saleTotalCostTextView,getString(R.string.saleprice_total_tablerow_textview),backGroundColorChange,10, 1);
        Double saleExpense = saleExpenseProvided ? Double.parseDouble(utils.replaceComma(MUTUAL_FUNDS_SALEEXPENSE)) : new Double(0);
        totalSaleAmount = saleCost + saleExpense;
        utils.setTextViewAttributes(saleTotalCostDataTextView,utils.rupeeSymbol + utils.formatAmount(String.valueOf(totalSaleAmount)),backGroundColorChange++,1, 10);

        saleTableRow = new TableRow(this);
        saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(saleTotalCostTextView,0);
        saleTableRow.addView(saleTotalCostDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);

    }

    private void computeCapitalGain(){
        int backGroundColorChange = 0;
        //Create the header row
        mutualFundResultTableLayout.addView(utils.createHeaderRow(new TableRow(this), new TextView(this), getString(R.string.capitalgaindetails_header_textview)),mutualFundResultTableLayoutIndex++);

        TableRow capitalGainTableRow = new TableRow(this);

        //perform all the computation here
        calculateGainDetails();

        //Create data rows

        //Duration of Time asset is held
        TextView durationHeldTextView = new TextView(this);
        TextView durationHeldDataTextView = new TextView(this);
        utils.setTextViewAttributes(durationHeldTextView,getString(R.string.capitalgain_time_diff_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(durationHeldDataTextView,utils.calculateDifferenceInPurchaseAndSaleDate(MUTUAL_FUND_PURCHASEYEAR,MUTUAL_FUND_SALEYEAR),backGroundColorChange++,1, 10);

        capitalGainTableRow = new TableRow(this);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.addView(durationHeldTextView,0);
        capitalGainTableRow.addView(durationHeldDataTextView,1);

        mutualFundResultTableLayout.addView(capitalGainTableRow,mutualFundResultTableLayoutIndex++);


        //Capital Gain Type
        TextView gainTypeTextView = new TextView(this);
        TextView gainTypeDataTextView = new TextView(this);
        utils.setTextViewAttributes(gainTypeTextView,getString(R.string.capitalgain_type_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(gainTypeDataTextView,gainTypeDisplayValue,backGroundColorChange++,1, 10);

        capitalGainTableRow = new TableRow(this);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.addView(gainTypeTextView,0);
        capitalGainTableRow.addView(gainTypeDataTextView,1);

        mutualFundResultTableLayout.addView(capitalGainTableRow,mutualFundResultTableLayoutIndex++);

        //Capital Gain Amount

        TextView gainAmountTextView = new TextView(this);
        TextView gainAmountDataTextView = new TextView(this);
        utils.setTextViewAttributes(gainAmountTextView,capitalGainAmountTextView,backGroundColorChange,10, 1);
        utils.setTextViewAttributes(gainAmountDataTextView,capitalGainAmountDisplayValue,backGroundColorChange++,1, 10);

        capitalGainTableRow = new TableRow(this);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.addView(gainAmountTextView,0);
        capitalGainTableRow.addView(gainAmountDataTextView,1);

        mutualFundResultTableLayout.addView(capitalGainTableRow,mutualFundResultTableLayoutIndex++);

        //Capital Gain Tax percent

        TextView gainTaxPercentTextView = new TextView(this);
        TextView gainTaxPercentDataTextView = new TextView(this);
        utils.setTextViewAttributes(gainTaxPercentTextView,getString(R.string.capitalgain_tax_percent_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(gainTaxPercentDataTextView,capitalGainTaxPercentDisplayValue,backGroundColorChange++,1, 10);

        capitalGainTableRow = new TableRow(this);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.addView(gainTaxPercentTextView,0);
        capitalGainTableRow.addView(gainTaxPercentDataTextView,1);

        mutualFundResultTableLayout.addView(capitalGainTableRow,mutualFundResultTableLayoutIndex++);

        //Capital Gain Tax amount

        TextView gainTaxAmountTextView = new TextView(this);
        TextView gainTaxAmountDataTextView = new TextView(this);
        utils.setTextViewAttributes(gainTaxAmountTextView,getString(R.string.capitalgain_tax_amount_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(gainTaxAmountDataTextView,getCapitalGainTaxAmountDisplayValue,backGroundColorChange++,1, 10);

        capitalGainTableRow = new TableRow(this);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.addView(gainTaxAmountTextView,0);
        capitalGainTableRow.addView(gainTaxAmountDataTextView,1);

        mutualFundResultTableLayout.addView(capitalGainTableRow,mutualFundResultTableLayoutIndex++);

        //Net profit

        TextView netProfitTextView = new TextView(this);
        TextView netProfitDataTextView = new TextView(this);
        utils.setTextViewAttributes(netProfitTextView,getString(R.string.capitalgain_net_profit_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(netProfitDataTextView,netProfitDisplayValue,backGroundColorChange++,1, 10);

        capitalGainTableRow = new TableRow(this);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.addView(netProfitTextView,0);
        capitalGainTableRow.addView(netProfitDataTextView,1);

        mutualFundResultTableLayout.addView(capitalGainTableRow,mutualFundResultTableLayoutIndex++);

    }

    private void loadFairMarketValueDetails(){
        int backGroundColorChange = 0;
        //Create the header row
        mutualFundResultTableLayout.addView(utils.createHeaderRow(new TableRow(this), new TextView(this), MUTUAL_FUNDS_FAIRMARKET_HEADERMESSAGE),mutualFundResultTableLayoutIndex++);

        TableRow fairMarketPriceTableRow = new TableRow(this);

        //Create data rows

        //Fair market value per unit
        TextView fairMarketPricePerUnitTextView = new TextView(this);
        TextView fairMarketPricePerUnitDataTextView = new TextView(this);
        utils.setTextViewAttributes(fairMarketPricePerUnitTextView,getString(R.string.fairmarket_price_per_unit_tablerow_textview),backGroundColorChange,10, 1);
        utils.setTextViewAttributes(fairMarketPricePerUnitDataTextView,utils.rupeeSymbol + MUTUAL_FUNDS_FAIRMARKETVALUE,backGroundColorChange++,1, 10);

        fairMarketPriceTableRow = new TableRow(this);
        fairMarketPriceTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        fairMarketPriceTableRow.addView(fairMarketPricePerUnitTextView,0);
        fairMarketPriceTableRow.addView(fairMarketPricePerUnitDataTextView,1);

        mutualFundResultTableLayout.addView(fairMarketPriceTableRow,mutualFundResultTableLayoutIndex++);

        //Total fair market value
        TextView fairMarketPriceTextView = new TextView(this);
        TextView fairMarketPriceDataTextView = new TextView(this);
        utils.setTextViewAttributes(fairMarketPriceTextView,getString(R.string.fairmarket_total_price_tablerow_textview),backGroundColorChange,10, 1);

        Double fairMarketPriceAmount = Double.parseDouble(utils.replaceComma(MUTUAL_FUNDS_FAIRMARKETVALUE));
        Long numberOfUnits = Long.parseLong(utils.replaceComma(MUTUAL_FUNDS_NUMBEROFUNITS));

        totalPurchaseAmount = fairMarketPriceAmount * numberOfUnits;

        utils.setTextViewAttributes(fairMarketPriceDataTextView,utils.rupeeSymbol + utils.formatAmount(String.valueOf(totalPurchaseAmount)),backGroundColorChange++,1, 10);

        fairMarketPriceTableRow = new TableRow(this);
        fairMarketPriceTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        fairMarketPriceTableRow.addView(fairMarketPriceTextView,0);
        fairMarketPriceTableRow.addView(fairMarketPriceDataTextView,1);

        mutualFundResultTableLayout.addView(fairMarketPriceTableRow,mutualFundResultTableLayoutIndex++);
    }

    private void loadInputData(){
        MUTUAL_FUND_SALEYEAR = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUND_SALEYEAR);
        MUTUAL_FUND_SALEAMOUNT = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUND_SALEAMOUNT);
        MUTUAL_FUND_PURCHASEYEAR = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUND_PURCHASEYEAR);
        MUTUAL_FUND_PURCHASEAMOUNT = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUND_PURCHASEAMOUNT);
        MUTUAL_FUNDS_NUMBEROFUNITS = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUNDS_NUMBEROFUNITS);
        MUTUAL_FUNDS_SALEEXPENSE = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUNDS_SALEEXPENSE);
        MUTUAL_FUNDS_PURCHASEEXPENSE = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUNDS_PURCHASEEXPENSE);
        MUTUAL_FUNDS_TYPE = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUND_TYPE);
        MUTUAL_FUNDS_FAIRMARKETVALUE = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUNDS_FAIRMARKETVALUE);
        MUTUAL_FUNDS_FAIRMARKET_HEADERMESSAGE = intent.getStringExtra(MutualFundsCalcActivity.MUTUAL_FUNDS_FAIRMARKET_HEADERMESSAGE);
    }

    /*
    * Computes following attributes
    * Capital Gain Type
    * Capital Gain Amount
    * Capital Gain Tax percent
    * Capital Gain Tax amount
    * Net profit
    * */
    private void calculateGainDetails(){
        Double taxPercent;
        //Debt Mutual Fund
        if(MutualFundsCalcActivity.DEBT_MUTUAL_FUND.equals(MUTUAL_FUNDS_TYPE)){
            utils.calculateDifferenceInPurchaseAndSaleDate(MUTUAL_FUND_PURCHASEYEAR,MUTUAL_FUND_SALEYEAR);
            //Short term computation
            if(utils.yearDiff < 3){
                capitalGainAmount = totalSaleAmount - totalPurchaseAmount;
                gaintype = capitalGainAmount < 0 ? GAINTYPE.SHORT_TERM_LOSS : GAINTYPE.SHORT_TERM_GAIN;
                netProfit = capitalGainAmount;

                if(GAINTYPE.SHORT_TERM_GAIN.equals(gaintype)){
                    gainTypeDisplayValue = getString(R.string.capitalgain_shot_term_gain_textview);
                    capitalGainAmountTextView = getString(R.string.capitalgain_gain_amount_textview);
                }
                else{
                    gainTypeDisplayValue = getString(R.string.capitalgain_shot_term_loss_textview);
                    capitalGainAmountTextView = getString(R.string.capitalgain_loss_amount_textview);
                }
                capitalGainTaxPercentDisplayValue = getString(R.string.capitalgain_tax_info_textview);
                getCapitalGainTaxAmountDisplayValue = getString(R.string.capitalgain_tax_info_textview);

            }
            //Long term computation
            else{
                String purchaseDate = utils.differenceOfDates(MUTUAL_FUND_PURCHASEYEAR,getString(R.string.index_date_limit)) > 0 ? MUTUAL_FUND_PURCHASEYEAR : getString(R.string.index_date_limit);
                String saleDate = utils.differenceOfDates(MUTUAL_FUND_SALEYEAR,getString(R.string.index_date_limit)) > 0 ? MUTUAL_FUND_SALEYEAR : getString(R.string.index_date_limit);
                Double indexedPurchaseAmount = totalPurchaseAmount * (utils.getIndexValue(saleDate,this) / utils.getIndexValue(purchaseDate, this));
                capitalGainAmount = totalSaleAmount - indexedPurchaseAmount;
                gaintype = capitalGainAmount < 0 ? GAINTYPE.LONG_TERM_LOSS : GAINTYPE.LONG_TERM_GAIN;
                if(GAINTYPE.LONG_TERM_GAIN.equals(gaintype)){
                    taxPercent = Double.parseDouble(getString(R.string.mutual_fund_debt_long_term_percent))/100;
                    capitalGainTaxAmount = capitalGainAmount * taxPercent;
                    capitalGainAmountTextView = getString(R.string.capitalgain_gain_amount_textview);
                    gainTypeDisplayValue = getString(R.string.capitalgain_long_term_gain_textview);
                    netProfit = capitalGainAmount - capitalGainTaxAmount;
                    capitalGainTaxPercentDisplayValue = getString(R.string.mutual_fund_debt_long_term_percent) + "%";
                }
                else{
                    gainTypeDisplayValue = getString(R.string.capitalgain_long_term_loss_textview);
                    capitalGainAmountTextView = getString(R.string.capitalgain_loss_amount_textview);
                    capitalGainTaxAmount = 0.00;
                    capitalGainTaxPercentDisplayValue = getString(R.string.zero_percent);
                    netProfit = capitalGainAmount;
                }
                getCapitalGainTaxAmountDisplayValue = utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainTaxAmount));
            }
        }
        //Equity Mutual Fund
        else{
            utils.calculateDifferenceInPurchaseAndSaleDate(MUTUAL_FUND_PURCHASEYEAR,MUTUAL_FUND_SALEYEAR);
            capitalGainAmount = totalSaleAmount - totalPurchaseAmount;
            //Short term computation
            if(utils.yearDiff < 1){
                gaintype = capitalGainAmount < 0 ? GAINTYPE.SHORT_TERM_LOSS : GAINTYPE.SHORT_TERM_GAIN;
                if(GAINTYPE.SHORT_TERM_GAIN.equals(gaintype)){
                    taxPercent = Double.parseDouble(getString(R.string.mutual_fund_equity_short_term_percent))/100;
                    capitalGainTaxAmount = capitalGainAmount * taxPercent;
                    gainTypeDisplayValue = getString(R.string.capitalgain_shot_term_gain_textview);
                    capitalGainAmountTextView = getString(R.string.capitalgain_gain_amount_textview);
                    capitalGainTaxPercentDisplayValue = getString(R.string.mutual_fund_equity_short_term_percent) + "%";
                    netProfit = capitalGainAmount - capitalGainTaxAmount;
                }
                else{
                    gainTypeDisplayValue = getString(R.string.capitalgain_shot_term_loss_textview);
                }
            }
            //Long term computation
            else{
                gaintype = capitalGainAmount < 0 ? GAINTYPE.LONG_TERM_LOSS : GAINTYPE.LONG_TERM_GAIN;
                if(GAINTYPE.LONG_TERM_GAIN.equals(gaintype)){
                    if(capitalGainAmount > Double.parseDouble(getString(R.string.mutual_fund_equity_long_term_amount_limit))){
                        taxPercent = Double.parseDouble(getString(R.string.mutual_fund_equity_long_term_percent))/100;
                        capitalGainTaxAmount = capitalGainAmount * taxPercent;
                        netProfit = capitalGainAmount - capitalGainTaxAmount;
                    }
                    else{
                        capitalGainTaxAmount = 0.00;
                        capitalGainTaxPercentDisplayValue = getString(R.string.zero_percent);
                        netProfit = capitalGainAmount;
                    }
                    gainTypeDisplayValue = getString(R.string.capitalgain_long_term_gain_textview);
                    capitalGainAmountTextView = getString(R.string.capitalgain_gain_amount_textview);
                }
                else{
                    gainTypeDisplayValue = getString(R.string.capitalgain_long_term_loss_textview);
                }
            }

            if(GAINTYPE.SHORT_TERM_LOSS.equals(gaintype) || GAINTYPE.LONG_TERM_LOSS.equals(gaintype)){
                capitalGainAmountTextView = getString(R.string.capitalgain_loss_amount_textview);
                capitalGainTaxAmount = 0.00;
                capitalGainTaxPercentDisplayValue = getString(R.string.zero_percent);
                netProfit = capitalGainAmount;
            }
            getCapitalGainTaxAmountDisplayValue = utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainTaxAmount));
        }

        capitalGainAmountDisplayValue = utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainAmount));
        netProfitDisplayValue = utils.rupeeSymbol + utils.formatAmount(String.valueOf(netProfit));

    }

    @Override
    public void onBackPressed(){
        Intent homeIntent = new Intent(this,CalculatorHomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}
