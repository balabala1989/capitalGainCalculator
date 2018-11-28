package balajirajagopal.com.capitalgaincalculator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private static int mutualFundResultTableLayoutIndex = 0;
    private static TableLayout mutualFundResultTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_fund_report);

        intent = getIntent();
        //Transfer input data from previous activity to this activity
        loadInputData();

        mutualFundResultTableLayout = (TableLayout) findViewById(R.id.mutualFundResultTableLayout);
        mutualFundResultTableLayout.removeAllViews();

        //Load the Asset Details into Table. Data is received as Input from previous Activity
        loadAssetDetails();

        //Create dummy space
        mutualFundResultTableLayout.addView(CapitalGainCalculatorUtils.createSpaceBetweenTables(this),mutualFundResultTableLayoutIndex++);

        //Load the Purchase Details into Table. Data is received as Input from previous Activity
        loadPurchaseDetails();

        //Create dummy space
        mutualFundResultTableLayout.addView(CapitalGainCalculatorUtils.createSpaceBetweenTables(this),mutualFundResultTableLayoutIndex++);

        //Load the Sale Details into Table. Data is received as Input from previous Activity
        loadSaleDetails();

        //Create dummy space
        mutualFundResultTableLayout.addView(CapitalGainCalculatorUtils.createSpaceBetweenTables(this),mutualFundResultTableLayoutIndex++);

        //Load the Capital Gain computed into Table. Data is received as Input from previous Activity
        computeCapitalGain();
    }

    private void loadAssetDetails(){
        int backGroundColorChange = 0;
        //Create the header row
        mutualFundResultTableLayout.addView(CapitalGainCalculatorUtils.createHeaderRow(new TableRow(this), new TextView(this), getString(R.string.assetdetails_header_textview)),mutualFundResultTableLayoutIndex++);

        TableRow assetTableRow = new TableRow(this);

        //Create data rows

        //Asset Type
        TextView assetTypeTextView = new TextView(this);
        TextView assetTypeDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(assetTypeTextView,getString(R.string.assettype_tablerow_textview),backGroundColorChange,10, 1);
        int dataStringResourceID;
        if(MutualFundsCalcActivity.DEBT_MUTUAL_FUND.equals(MUTUAL_FUNDS_TYPE))
            dataStringResourceID = R.string.debt_mutualfund_assettype_textview;
        else
            dataStringResourceID = R.string.equity_mutualfund_assettype_textview;
        CapitalGainCalculatorUtils.setTextViewAttributes(assetTypeDataTextView,getString(dataStringResourceID),backGroundColorChange++,1, 10);

        assetTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        assetTableRow.addView(assetTypeTextView,0);
        assetTableRow.addView(assetTypeDataTextView,1);

        mutualFundResultTableLayout.addView(assetTableRow,mutualFundResultTableLayoutIndex++);

        //Number of Units
        TextView numberOfUnitsTextView = new TextView(this);
        TextView numberOfUnitsDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(numberOfUnitsTextView,getString(R.string.numberofunits_tablerow_textview),backGroundColorChange,10, 1);
        CapitalGainCalculatorUtils.setTextViewAttributes(numberOfUnitsDataTextView,MUTUAL_FUNDS_NUMBEROFUNITS,backGroundColorChange++,1, 10);

        assetTableRow = new TableRow(this);
        assetTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        assetTableRow.addView(numberOfUnitsTextView,0);
        assetTableRow.addView(numberOfUnitsDataTextView,1);

        mutualFundResultTableLayout.addView(assetTableRow,mutualFundResultTableLayoutIndex++);
    }

    private void loadPurchaseDetails(){
        int backGroundColorChange = 0;
        //Create the header row
        mutualFundResultTableLayout.addView(CapitalGainCalculatorUtils.createHeaderRow(new TableRow(this), new TextView(this), getString(R.string.purchasedetails_header_textview)),mutualFundResultTableLayoutIndex++);

        TableRow purchaseTableRow = new TableRow(this);

        //Create data rows

        //Purchase Year
        TextView purchaseYearTextView = new TextView(this);
        TextView purchaseYearDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(purchaseYearTextView,getString(R.string.purchaseyear_tablerow_textview),backGroundColorChange,10, 1);
        CapitalGainCalculatorUtils.setTextViewAttributes(purchaseYearDataTextView,MUTUAL_FUND_PURCHASEYEAR,backGroundColorChange++,1, 10);

        purchaseTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchaseYearTextView,0);
        purchaseTableRow.addView(purchaseYearDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);


        //Purchase Price per Unit
        TextView purchasePricePerUnitTextView = new TextView(this);
        TextView purchasePricePerUnitDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(purchasePricePerUnitTextView,getString(R.string.purchaseprice_perunit_tablerow_textview),backGroundColorChange,10, 1);
        CapitalGainCalculatorUtils.setTextViewAttributes(purchasePricePerUnitDataTextView,MUTUAL_FUND_PURCHASEAMOUNT,backGroundColorChange++,1, 10);

        purchaseTableRow = new TableRow(this);
        purchaseTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchasePricePerUnitTextView,0);
        purchaseTableRow.addView(purchasePricePerUnitDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);

        //Purchase Price for all  Unit
        TextView purchasePriceTotalUnitsTextView = new TextView(this);
        TextView purchasePriceTotalUnitsDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(purchasePriceTotalUnitsTextView,getString(R.string.purchaseprice_costofunit_tablerow_textview),backGroundColorChange,10, 1);
        Long purchaseAmount = Long.parseLong(MUTUAL_FUND_PURCHASEAMOUNT);
        Long numberOfUnits = Long.parseLong(MUTUAL_FUNDS_NUMBEROFUNITS);

        Long purchaseCost = purchaseAmount * numberOfUnits;

        CapitalGainCalculatorUtils.setTextViewAttributes(purchasePriceTotalUnitsDataTextView,String.valueOf(purchaseCost),backGroundColorChange++,1, 10);

        purchaseTableRow = new TableRow(this);
        purchaseTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchasePriceTotalUnitsTextView,0);
        purchaseTableRow.addView(purchasePriceTotalUnitsDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);


        //Purchase Expense
        TextView purchaseExpenseTextView = new TextView(this);
        TextView purchaseExpenseDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(purchaseExpenseTextView,getString(R.string.purchase_expense_textview),backGroundColorChange,10, 1);
        CapitalGainCalculatorUtils.setTextViewAttributes(purchaseExpenseDataTextView,MUTUAL_FUNDS_PURCHASEEXPENSE,backGroundColorChange++,1, 10);

        purchaseTableRow = new TableRow(this);
        purchaseTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchaseExpenseTextView,0);
        purchaseTableRow.addView(purchaseExpenseDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);

        //Total Purchase cost
        TextView purchaseTotalCostTextView = new TextView(this);
        TextView purchaseTotalCostDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(purchaseTotalCostTextView,getString(R.string.purchaseprice_total_tablerow_textview),backGroundColorChange,10, 1);
        Long purchaseExpense = Long.parseLong(MUTUAL_FUNDS_PURCHASEEXPENSE);
        CapitalGainCalculatorUtils.setTextViewAttributes(purchaseTotalCostDataTextView,String.valueOf(purchaseCost + purchaseExpense),backGroundColorChange++,1, 10);

        purchaseTableRow = new TableRow(this);
        purchaseTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.addView(purchaseTotalCostTextView,0);
        purchaseTableRow.addView(purchaseTotalCostDataTextView,1);

        mutualFundResultTableLayout.addView(purchaseTableRow,mutualFundResultTableLayoutIndex++);
    }

    private void loadSaleDetails(){
        int backGroundColorChange = 0;
        //Create the header row
        mutualFundResultTableLayout.addView(CapitalGainCalculatorUtils.createHeaderRow(new TableRow(this), new TextView(this), getString(R.string.saledetails_header_textview)),mutualFundResultTableLayoutIndex++);

        TableRow saleTableRow = new TableRow(this);

        //Create data rows

        //Purchase Year
        TextView saleYearTextView = new TextView(this);
        TextView saleYearDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(saleYearTextView,getString(R.string.saleyear_tablerow_textview),backGroundColorChange,10, 1);
        CapitalGainCalculatorUtils.setTextViewAttributes(saleYearDataTextView,MUTUAL_FUND_SALEYEAR,backGroundColorChange++,1, 10);

        saleTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(saleYearTextView,0);
        saleTableRow.addView(saleYearDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);


        //Purchase Price per Unit
        TextView salePricePerUnitTextView = new TextView(this);
        TextView salePricePerUnitDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(salePricePerUnitTextView,getString(R.string.saleprice_perunit_tablerow_textview),backGroundColorChange,10, 1);
        CapitalGainCalculatorUtils.setTextViewAttributes(salePricePerUnitDataTextView,MUTUAL_FUND_SALEAMOUNT,backGroundColorChange++,1, 10);

        saleTableRow = new TableRow(this);
        saleTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(salePricePerUnitTextView,0);
        saleTableRow.addView(salePricePerUnitDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);

        //Purchase Price for all  Unit
        TextView salePriceTotalUnitsTextView = new TextView(this);
        TextView salePriceTotalUnitsDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(salePriceTotalUnitsTextView,getString(R.string.saleprice_costofunit_tablerow_textview),backGroundColorChange,10, 1);
        Long saleAmount = Long.parseLong(MUTUAL_FUND_SALEAMOUNT);
        Long numberOfUnits = Long.parseLong(MUTUAL_FUNDS_NUMBEROFUNITS);

        Long saleCost = saleAmount * numberOfUnits;

        CapitalGainCalculatorUtils.setTextViewAttributes(salePriceTotalUnitsDataTextView,String.valueOf(saleCost),backGroundColorChange++,1, 10);

        saleTableRow = new TableRow(this);
        saleTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(salePriceTotalUnitsTextView,0);
        saleTableRow.addView(salePriceTotalUnitsDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);


        //Purchase Expense
        TextView saleExpenseTextView = new TextView(this);
        TextView saleExpenseDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(saleExpenseTextView,getString(R.string.sale_expense_textview),backGroundColorChange,10, 1);
        CapitalGainCalculatorUtils.setTextViewAttributes(saleExpenseDataTextView,MUTUAL_FUNDS_SALEEXPENSE,backGroundColorChange++,1, 10);

        saleTableRow = new TableRow(this);
        saleTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(saleExpenseTextView,0);
        saleTableRow.addView(saleExpenseDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);

        //Total Purchase cost
        TextView saleTotalCostTextView = new TextView(this);
        TextView saleTotalCostDataTextView = new TextView(this);
        CapitalGainCalculatorUtils.setTextViewAttributes(saleTotalCostTextView,getString(R.string.saleprice_total_tablerow_textview),backGroundColorChange,10, 1);
        Long saleExpense = Long.parseLong(MUTUAL_FUNDS_SALEEXPENSE);
        CapitalGainCalculatorUtils.setTextViewAttributes(saleTotalCostDataTextView,String.valueOf(saleCost + saleExpense),backGroundColorChange++,1, 10);

        saleTableRow = new TableRow(this);
        saleTableRow.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.addView(saleTotalCostTextView,0);
        saleTableRow.addView(saleTotalCostDataTextView,1);

        mutualFundResultTableLayout.addView(saleTableRow,mutualFundResultTableLayoutIndex++);

    }

    private void computeCapitalGain(){
        int backGroundColorChange = 0;
        //Create the header row
        mutualFundResultTableLayout.addView(CapitalGainCalculatorUtils.createHeaderRow(new TableRow(this), new TextView(this), getString(R.string.capitalgaindetails_header_textview)),mutualFundResultTableLayoutIndex++);

        TableRow saleTableRow = new TableRow(this);

        //Create data rows

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
    }


}
