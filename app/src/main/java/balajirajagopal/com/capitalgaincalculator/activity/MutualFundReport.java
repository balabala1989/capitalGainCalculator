package balajirajagopal.com.capitalgaincalculator.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import balajirajagopal.com.capitalgaincalculator.common.ReportGen;
import balajirajagopal.com.capitalgaincalculator.enums.ASSET_SUBTYPE;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcDisplayData;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcInputData;
import balajirajagopal.com.capitalgaincalculator.utils.CapitalGainCalculatorUtils;
import balajirajagopal.com.capitalgaincalculator.enums.GAINTYPE;
import balajirajagopal.com.capitalgaincalculator.R;

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

    private int mutualFundResultTableLayoutIndex = 2;
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


    private CapitalGainCalcInputData capitalGainCalcInputData;
    private CapitalGainCalcDisplayData capitalGainCalcDisplayData;
    private ReportGen reportGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_fund_report);



        utils = new CapitalGainCalculatorUtils();

        intent = getIntent();

        capitalGainCalcInputData = (CapitalGainCalcInputData) intent.getParcelableExtra(MutualFundsCalcActivity.MUTUAL_FUND_INPUT_DATA);
        capitalGainCalcDisplayData = new CapitalGainCalcDisplayData();
        reportGen = new ReportGen(this,capitalGainCalcInputData, capitalGainCalcDisplayData);
        reportGen.generateReport();



        /*//Transfer input data from previous activity to this activity
        loadInputData();

        *//*mutualFundResultTableLayout = (TableLayout) findViewById(R.id.mutualFundResultTableLayout);
        mutualFundResultTableLayout.removeAllViews();
        mutualFundResultTableLayout.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT));

        //Load the Asset Details into Table. Data is received as Input from previous Activity
        loadAssetDetails();
          *//*
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

        //Create dummy space
        mutualFundResultTableLayout.addView(utils.createSpaceBetweenTables(this),mutualFundResultTableLayoutIndex++);*/

    }

    /*private void loadAssetDetails(){
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
    }*/

    /*
    * Computes following attributes
    * Capital Gain Type
    * Capital Gain Amount
    * Capital Gain Tax percent
    * Capital Gain Tax amount
    * Net profit
    * */
    public CapitalGainCalcDisplayData calculateGainDetails(CapitalGainCalcInputData capitalGainCalcInputData, CapitalGainCalcDisplayData capitalGainCalcDisplayData){
        Double taxPercent;

        //Debt Mutual Fund
        if(ASSET_SUBTYPE.MUTUAL_FUNDS_DEBT.equals(capitalGainCalcInputData.getAssetSubtype())){
            utils.calculateDifferenceInPurchaseAndSaleDate(capitalGainCalcInputData.getPurchaseDate(),capitalGainCalcInputData.getSaleDate());
            //Short term computation
            if(utils.yearDiff < 3){
                capitalGainCalcDisplayData.setCapitalGainAmount(capitalGainCalcDisplayData.getTotalSaleAmount() - capitalGainCalcDisplayData.getTotalPurchaseAmount());
                capitalGainCalcDisplayData.setGaintype(capitalGainCalcDisplayData.getCapitalGainAmount() < 0 ? GAINTYPE.SHORT_TERM_LOSS : GAINTYPE.SHORT_TERM_GAIN);
                capitalGainCalcDisplayData.setNetProfit(capitalGainCalcDisplayData.getCapitalGainAmount());

                if(GAINTYPE.SHORT_TERM_GAIN.equals(capitalGainCalcDisplayData.getGaintype())){
                    capitalGainCalcDisplayData.setGainTypeDisplayValue(getString(R.string.capitalgain_shot_term_gain_textview));
                    capitalGainCalcDisplayData.setCapitalGainAmountTextView(getString(R.string.capitalgain_gain_amount_textview));
                }
                else{
                    capitalGainCalcDisplayData.setGainTypeDisplayValue(getString(R.string.capitalgain_shot_term_loss_textview));
                    capitalGainCalcDisplayData.setCapitalGainAmountTextView(getString(R.string.capitalgain_loss_amount_textview));
                }
                capitalGainCalcDisplayData.setCapitalGainTaxPercentDisplayValue(getString(R.string.capitalgain_tax_info_textview));
                capitalGainCalcDisplayData.setGetCapitalGainTaxAmountDisplayValue(getString(R.string.capitalgain_tax_info_textview));

            }
            //Long term computation
            else{
                String purchaseDate = utils.differenceOfDates(capitalGainCalcInputData.getPurchaseDate(),getString(R.string.index_date_limit)) > 0 ? capitalGainCalcInputData.getPurchaseDate() : getString(R.string.index_date_limit);
                String saleDate = utils.differenceOfDates(capitalGainCalcInputData.getSaleDate(),getString(R.string.index_date_limit)) > 0 ? capitalGainCalcInputData.getSaleDate() : getString(R.string.index_date_limit);
                Double indexedPurchaseAmount = capitalGainCalcDisplayData.getTotalPurchaseAmount() * (utils.getIndexValue(saleDate,this) / utils.getIndexValue(purchaseDate, this));
                capitalGainCalcDisplayData.setCapitalGainAmount(capitalGainCalcDisplayData.getTotalSaleAmount() - indexedPurchaseAmount);
                capitalGainCalcDisplayData.setGaintype(capitalGainCalcDisplayData.getCapitalGainAmount() < 0 ? GAINTYPE.LONG_TERM_LOSS : GAINTYPE.LONG_TERM_GAIN);
                if(GAINTYPE.LONG_TERM_GAIN.equals(capitalGainCalcDisplayData.getGaintype())){
                    taxPercent = Double.parseDouble(getString(R.string.mutual_fund_debt_long_term_percent))/100;
                    capitalGainCalcDisplayData.setCapitalGainTaxAmount(capitalGainCalcDisplayData.getCapitalGainAmount() * taxPercent);
                    capitalGainCalcDisplayData.setCapitalGainAmountTextView(getString(R.string.capitalgain_gain_amount_textview));
                    capitalGainCalcDisplayData.setGainTypeDisplayValue(getString(R.string.capitalgain_long_term_gain_textview));
                    capitalGainCalcDisplayData.setNetProfit(capitalGainCalcDisplayData.getCapitalGainAmount() - capitalGainCalcDisplayData.getCapitalGainTaxAmount());
                    capitalGainCalcDisplayData.setCapitalGainTaxPercentDisplayValue(getString(R.string.mutual_fund_debt_long_term_percent) + "%");
                }
                else{
                    capitalGainCalcDisplayData.setGainTypeDisplayValue(getString(R.string.capitalgain_long_term_loss_textview));
                    capitalGainCalcDisplayData.setCapitalGainAmountTextView(getString(R.string.capitalgain_loss_amount_textview));
                    capitalGainCalcDisplayData.setCapitalGainTaxAmount(new Double(0));
                    capitalGainCalcDisplayData.setCapitalGainTaxPercentDisplayValue(getString(R.string.zero_percent));
                    capitalGainCalcDisplayData.setNetProfit(capitalGainCalcDisplayData.getCapitalGainAmount());
                }
                capitalGainCalcDisplayData.setGetCapitalGainTaxAmountDisplayValue(utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainCalcDisplayData.getCapitalGainTaxAmount())));
            }
        }
        //Equity Mutual Fund
        else{
            utils.calculateDifferenceInPurchaseAndSaleDate(capitalGainCalcInputData.getPurchaseDate(),capitalGainCalcInputData.getSaleDate());
            capitalGainCalcDisplayData.setCapitalGainAmount(capitalGainCalcDisplayData.getTotalSaleAmount() - capitalGainCalcDisplayData.getTotalPurchaseAmount());
            //Short term computation
            if(utils.yearDiff < 1){
                capitalGainCalcDisplayData.setGaintype(capitalGainCalcDisplayData.getCapitalGainAmount() < 0 ? GAINTYPE.SHORT_TERM_LOSS : GAINTYPE.SHORT_TERM_GAIN);
                if(GAINTYPE.SHORT_TERM_GAIN.equals(capitalGainCalcDisplayData.getGaintype())){
                    taxPercent = Double.parseDouble(getString(R.string.mutual_fund_equity_short_term_percent))/100;
                    capitalGainCalcDisplayData.setCapitalGainTaxAmount(capitalGainCalcDisplayData.getCapitalGainAmount() * taxPercent);
                    capitalGainCalcDisplayData.setGainTypeDisplayValue(getString(R.string.capitalgain_shot_term_gain_textview));
                    capitalGainCalcDisplayData.setCapitalGainAmountTextView(getString(R.string.capitalgain_gain_amount_textview));
                    capitalGainCalcDisplayData.setCapitalGainTaxPercentDisplayValue(getString(R.string.mutual_fund_equity_short_term_percent) + "%");
                    capitalGainCalcDisplayData.setNetProfit(capitalGainCalcDisplayData.getCapitalGainAmount() - capitalGainCalcDisplayData.getCapitalGainTaxAmount());
                }
                else{
                    capitalGainCalcDisplayData.setGainTypeDisplayValue(getString(R.string.capitalgain_shot_term_loss_textview));
                }
            }
            //Long term computation
            else{
                capitalGainCalcDisplayData.setGaintype(capitalGainCalcDisplayData.getCapitalGainAmount() < 0 ? GAINTYPE.LONG_TERM_LOSS : GAINTYPE.LONG_TERM_GAIN);
                if(GAINTYPE.LONG_TERM_GAIN.equals(capitalGainCalcDisplayData.getGaintype())){
                    if(capitalGainCalcDisplayData.getCapitalGainAmount() > Double.parseDouble(getString(R.string.mutual_fund_equity_long_term_amount_limit))){
                        taxPercent = Double.parseDouble(getString(R.string.mutual_fund_equity_long_term_percent))/100;
                        capitalGainCalcDisplayData.setCapitalGainTaxAmount(capitalGainCalcDisplayData.getCapitalGainAmount() * taxPercent);
                        capitalGainCalcDisplayData.setNetProfit(capitalGainCalcDisplayData.getCapitalGainAmount() - capitalGainCalcDisplayData.getCapitalGainTaxAmount());
                    }
                    else{
                        capitalGainCalcDisplayData.setCapitalGainTaxAmount(new Double(0));
                        capitalGainCalcDisplayData.setCapitalGainTaxPercentDisplayValue(getString(R.string.zero_percent));
                        capitalGainCalcDisplayData.setNetProfit(capitalGainCalcDisplayData.getCapitalGainAmount());
                    }
                    capitalGainCalcDisplayData.setGainTypeDisplayValue(getString(R.string.capitalgain_long_term_gain_textview));
                    capitalGainCalcDisplayData.setCapitalGainAmountTextView(getString(R.string.capitalgain_gain_amount_textview));
                }
                else{
                    capitalGainCalcDisplayData.setGainTypeDisplayValue(getString(R.string.capitalgain_long_term_loss_textview));
                }
            }

            if(GAINTYPE.SHORT_TERM_LOSS.equals(capitalGainCalcDisplayData.getGaintype()) || GAINTYPE.LONG_TERM_LOSS.equals(capitalGainCalcDisplayData.getGaintype())){
                capitalGainCalcDisplayData.setCapitalGainAmountTextView(getString(R.string.capitalgain_loss_amount_textview));
                capitalGainCalcDisplayData.setCapitalGainTaxAmount(new Double(0));
                capitalGainCalcDisplayData.setCapitalGainTaxPercentDisplayValue(getString(R.string.zero_percent));
                capitalGainCalcDisplayData.setNetProfit(capitalGainCalcDisplayData.getCapitalGainAmount());
            }
            capitalGainCalcDisplayData.setGetCapitalGainTaxAmountDisplayValue(utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainCalcDisplayData.getCapitalGainTaxAmount())));
        }

        capitalGainCalcDisplayData.setCapitalGainAmountDisplayValue(utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainCalcDisplayData.getCapitalGainAmount())));
        capitalGainCalcDisplayData.setNetProfitDisplayValue(utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainCalcDisplayData.getNetProfit())));

        return capitalGainCalcDisplayData;
    }

    @Override
    public void onBackPressed(){
        Intent homeIntent = new Intent(this,CalculatorHomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}
