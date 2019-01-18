package balajirajagopal.com.capitalgaincalculator.common;

import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import balajirajagopal.com.capitalgaincalculator.R;
import balajirajagopal.com.capitalgaincalculator.activity.GoldCalcReportActivity;
import balajirajagopal.com.capitalgaincalculator.activity.MutualFundReport;
import balajirajagopal.com.capitalgaincalculator.activity.SharesCalcReportActivity;
import balajirajagopal.com.capitalgaincalculator.enums.ASSETTYPE;
import balajirajagopal.com.capitalgaincalculator.enums.ASSET_SUBTYPE;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcDisplayData;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcInputData;
import balajirajagopal.com.capitalgaincalculator.utils.CapitalGainCalculatorUtils;

public class ReportGen {

    private Activity activity;
    private CapitalGainCalcInputData capitalGainCalcInputData;
    private CapitalGainCalcDisplayData capitalGainCalcDisplayData;
    private CapitalGainCalculatorUtils utils;
    private Intent intent;
    private TableLayout tableLayout;
    private int tableLayoutIndex;
    private boolean isNumberOfUnitProvided;
    private boolean isFairMarketValueProvided;

    public ReportGen(Activity activity, CapitalGainCalcInputData capitalGainCalcInputData, CapitalGainCalcDisplayData capitalGainCalcDisplayData) {
        this.activity = activity;
        this.capitalGainCalcInputData = capitalGainCalcInputData;
        this.capitalGainCalcDisplayData = capitalGainCalcDisplayData;
    }

    public void generateReport(){
        utils = new CapitalGainCalculatorUtils(true);

        //Get the Intent
        intent = activity.getIntent();
        tableLayoutIndex = 0;

        if(activity instanceof MutualFundReport){
            tableLayout = activity.findViewById(R.id.mutualFundResultTableLayout);
        }
        else if(activity instanceof GoldCalcReportActivity){
            tableLayout = activity.findViewById(R.id.goldCalcReportTableLayout);
        }
        else if(activity instanceof SharesCalcReportActivity){
            tableLayout = activity.findViewById(R.id.sharesCalcReportTableLayout);
        }

        tableLayout.removeAllViews();
        tableLayout.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT));

        //Load the Asset Details into Table. Data is received as Input from the caller Activity
        loadAssetDetails();

        //Create dummy space
        tableLayout.addView(utils.createSpaceBetweenTables(activity),tableLayoutIndex++);

        //Load the Purchase Details into Table
        loadPurchaseDetails();

        //Create dummy space
        tableLayout.addView(utils.createSpaceBetweenTables(activity),tableLayoutIndex++);


        //Load the Fair Market Value details
        if(capitalGainCalcInputData.getFairMarketPrice() != null && capitalGainCalcInputData.getFairMarketPrice().length() != 0){
            loadFairMarketValueDetails();
            //Create dummy space
            tableLayout.addView(utils.createSpaceBetweenTables(activity),tableLayoutIndex++);

        }

        //Load the Sale Details into Table
        loadSaleDetails();

        //Create dummy space
        tableLayout.addView(utils.createSpaceBetweenTables(activity),tableLayoutIndex++);

        //Load the Capital Gain computed into Table.
        loadCapitalGainDetails();

        //Create dummy space
        tableLayout.addView(utils.createSpaceBetweenTables(activity),tableLayoutIndex++);

        loadAdViewDetails();

    }

    private void loadAssetDetails(){

        int backGroundColorChange = 0;
        int dataStringResourceID = 0;

        //Create the header row
        tableLayout.addView(utils.createHeaderRow(new TableRow(activity), new TextView(activity), activity.getString(R.string.assetdetails_header_textview)),tableLayoutIndex++);

        TableRow assetTableRow = new TableRow(activity);

        //Create data rows

        //Asset Type
        TextView assetTypeTextView = new TextView(activity);
        TextView assetTypeDataTextView = new TextView(activity);
        utils.setTextViewAttributes(assetTypeTextView,activity.getString(R.string.assettype_tablerow_textview),10, 1);

        if(ASSETTYPE.MUTUAL_FUNDS.equals(capitalGainCalcInputData.getAssettype())) {
            if(ASSET_SUBTYPE.MUTUAL_FUNDS_DEBT.equals(capitalGainCalcInputData.getAssetSubtype()))
                dataStringResourceID = R.string.debt_mutualfund_assettype_textview;
            else
                dataStringResourceID = R.string.equity_mutualfund_assettype_textview;
        }
        else if(ASSETTYPE.GOLD.equals(capitalGainCalcInputData.getAssettype())){
            dataStringResourceID = R.string.gold_assesttype_display_textview;
        }
        else if(ASSETTYPE.SHARES.equals(capitalGainCalcInputData.getAssettype())){
            if(ASSET_SUBTYPE.SHARES_LISTED.equals(capitalGainCalcInputData.getAssetSubtype()))
                dataStringResourceID = R.string.listed_shares_assesttype_display_textview;
            else
                dataStringResourceID = R.string.unlisted_shares_assesttype_display_textview;
        }

        utils.setTextViewAttributes(assetTypeDataTextView,activity.getString(dataStringResourceID),1, 10);

        assetTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        assetTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        assetTableRow.addView(assetTypeTextView,0);
        assetTableRow.addView(assetTypeDataTextView,1);

        tableLayout.addView(assetTableRow,tableLayoutIndex++);

        isNumberOfUnitsApplicable();

        if(isNumberOfUnitProvided) {

            //Number of Units
            TextView numberOfUnitsTextView = new TextView(activity);
            TextView numberOfUnitsDataTextView = new TextView(activity);
            utils.setTextViewAttributes(numberOfUnitsTextView, activity.getString(R.string.numberofunits_tablerow_textview), 10, 1);
            utils.setTextViewAttributes(numberOfUnitsDataTextView, capitalGainCalcInputData.getNumberOfUnits(), 1, 10);

            assetTableRow = new TableRow(activity);
            assetTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            assetTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            assetTableRow.addView(numberOfUnitsTextView, 0);
            assetTableRow.addView(numberOfUnitsDataTextView, 1);

            tableLayout.addView(assetTableRow, tableLayoutIndex++);
        }
    }

    private void loadPurchaseDetails(){
        int backGroundColorChange = 0;
        boolean isPurchaseExpenseProvided = false;

        //Create the header row
        tableLayout.addView(utils.createHeaderRow(new TableRow(activity), new TextView(activity), activity.getString(R.string.purchasedetails_header_textview)),tableLayoutIndex++);

        TableRow purchaseTableRow = new TableRow(activity);

        //Create data rows

        //Purchase Year
        TextView purchaseYearTextView = new TextView(activity);
        TextView purchaseYearDataTextView = new TextView(activity);
        String purchaseYearData = "";
        String[] purchaseData = capitalGainCalcInputData.getPurchaseDate().split("-");
        purchaseYearData = new StringBuffer("").append(purchaseData[0]).append("-").append(utils.getEnglishNameForIntegerMonth(Integer.parseInt(purchaseData[1]))).append("-").append(purchaseData[2]).toString();

        utils.setTextViewAttributes(purchaseYearTextView,activity.getString(R.string.purchaseyear_tablerow_textview),10, 1);
        utils.setTextViewAttributes(purchaseYearDataTextView,purchaseYearData,1, 10);

        purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        purchaseTableRow.addView(purchaseYearTextView,0);
        purchaseTableRow.addView(purchaseYearDataTextView,1);

        tableLayout.addView(purchaseTableRow,tableLayoutIndex++);

        Double purchaseCost;

        if(isNumberOfUnitProvided) {
            //Purchase Price per Unit
            TextView purchasePricePerUnitTextView = new TextView(activity);
            TextView purchasePricePerUnitDataTextView = new TextView(activity);
            utils.setTextViewAttributes(purchasePricePerUnitTextView, activity.getString(R.string.purchaseprice_perunit_tablerow_textview), 10, 1);
            utils.setTextViewAttributes(purchasePricePerUnitDataTextView, utils.rupeeSymbol + utils.formatAmount(utils.replaceComma(capitalGainCalcInputData.getPurchaseAmount())), 1, 10);

            purchaseTableRow = new TableRow(activity);
            purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            purchaseTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            purchaseTableRow.addView(purchasePricePerUnitTextView, 0);
            purchaseTableRow.addView(purchasePricePerUnitDataTextView, 1);

            tableLayout.addView(purchaseTableRow, tableLayoutIndex++);

            //Purchase Price for all  Unit
            TextView purchasePriceTotalUnitsTextView = new TextView(activity);
            TextView purchasePriceTotalUnitsDataTextView = new TextView(activity);
            utils.setTextViewAttributes(purchasePriceTotalUnitsTextView, activity.getString(R.string.purchaseprice_costofunit_tablerow_textview), 10, 1);
            Double purchaseAmount = Double.parseDouble(utils.replaceComma(capitalGainCalcInputData.getPurchaseAmount()));
            Long numberOfUnits = Long.parseLong(utils.replaceComma(capitalGainCalcInputData.getNumberOfUnits()));

            purchaseCost = purchaseAmount * numberOfUnits;

            utils.setTextViewAttributes(purchasePriceTotalUnitsDataTextView, utils.rupeeSymbol + utils.formatAmount(String.valueOf(purchaseCost)), 1, 10);

            purchaseTableRow = new TableRow(activity);
            purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            purchaseTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            purchaseTableRow.addView(purchasePriceTotalUnitsTextView, 0);
            purchaseTableRow.addView(purchasePriceTotalUnitsDataTextView, 1);

            tableLayout.addView(purchaseTableRow, tableLayoutIndex++);
        }
        else{
            TextView purchasePriceTextView = new TextView(activity);
            TextView purchasePriceDataTextView = new TextView(activity);
            utils.setTextViewAttributes(purchasePriceTextView, activity.getString(R.string.purchaseprice_tablerow_textview), 10, 1);
            utils.setTextViewAttributes(purchasePriceDataTextView, utils.rupeeSymbol + utils.formatAmount(utils.replaceComma(capitalGainCalcInputData.getPurchaseAmount())), 1, 10);

            purchaseCost = Double.parseDouble(utils.replaceComma(capitalGainCalcInputData.getPurchaseAmount()));;

            purchaseTableRow = new TableRow(activity);
            purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            purchaseTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            purchaseTableRow.addView(purchasePriceTextView, 0);
            purchaseTableRow.addView(purchasePriceDataTextView, 1);

            tableLayout.addView(purchaseTableRow, tableLayoutIndex++);
        }

        //Purchase Expense

        isPurchaseExpenseProvided = isExpenseProvided(capitalGainCalcInputData.getPurchaseExpense());
        TextView purchaseExpenseTextView = new TextView(activity);
        TextView purchaseExpenseDataTextView = new TextView(activity);
        utils.setTextViewAttributes(purchaseExpenseTextView,activity.getString(R.string.purchase_expense_textview),10, 1);
        String purchaseExpenseString =  isPurchaseExpenseProvided ? utils.rupeeSymbol + utils.formatAmount(utils.replaceComma(capitalGainCalcInputData.getPurchaseExpense())) : activity.getString(R.string.not_applicable);
        utils.setTextViewAttributes(purchaseExpenseDataTextView,purchaseExpenseString,1, 10);

        purchaseTableRow = new TableRow(activity);
        purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        purchaseTableRow.addView(purchaseExpenseTextView,0);
        purchaseTableRow.addView(purchaseExpenseDataTextView,1);

        tableLayout.addView(purchaseTableRow,tableLayoutIndex++);

        //Total Purchase cost
        TextView purchaseTotalCostTextView = new TextView(activity);
        TextView purchaseTotalCostDataTextView = new TextView(activity);
        utils.setTextViewAttributes(purchaseTotalCostTextView,activity.getString(R.string.purchaseprice_total_tablerow_textview),10, 1);
        Double purchaseExpense = isPurchaseExpenseProvided ? Double.parseDouble(utils.replaceComma(capitalGainCalcInputData.getPurchaseExpense())) : new Double(0);
        capitalGainCalcDisplayData.setTotalPurchaseAmount(purchaseCost + purchaseExpense);
        utils.setTextViewAttributes(purchaseTotalCostDataTextView,utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainCalcDisplayData.getTotalPurchaseAmount())),1, 10);

        purchaseTableRow = new TableRow(activity);
        purchaseTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        purchaseTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        purchaseTableRow.addView(purchaseTotalCostTextView,0);
        purchaseTableRow.addView(purchaseTotalCostDataTextView,1);

        tableLayout.addView(purchaseTableRow,tableLayoutIndex++);
    }

    private void loadFairMarketValueDetails(){
        int backGroundColorChange = 0;

        //Create the header row
        tableLayout.addView(utils.createHeaderRow(new TableRow(activity), new TextView(activity), capitalGainCalcInputData.getHeaderMessage()),tableLayoutIndex++);

        TableRow fairMarketPriceTableRow = new TableRow(activity);

        //Create data rows

        if(isNumberOfUnitProvided) {
            //Fair market value per unit
            TextView fairMarketPricePerUnitTextView = new TextView(activity);
            TextView fairMarketPricePerUnitDataTextView = new TextView(activity);
            utils.setTextViewAttributes(fairMarketPricePerUnitTextView, activity.getString(R.string.fairmarket_price_per_unit_tablerow_textview), 10, 1);
            utils.setTextViewAttributes(fairMarketPricePerUnitDataTextView, utils.rupeeSymbol + utils.formatAmount(utils.replaceComma(capitalGainCalcInputData.getFairMarketPrice())), 1, 10);

            fairMarketPriceTableRow = new TableRow(activity);
            fairMarketPriceTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            fairMarketPriceTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            fairMarketPriceTableRow.addView(fairMarketPricePerUnitTextView, 0);
            fairMarketPriceTableRow.addView(fairMarketPricePerUnitDataTextView, 1);

            tableLayout.addView(fairMarketPriceTableRow, tableLayoutIndex++);

            //Total fair market value
            TextView fairMarketPriceTextView = new TextView(activity);
            TextView fairMarketPriceDataTextView = new TextView(activity);
            utils.setTextViewAttributes(fairMarketPriceTextView, activity.getString(R.string.fairmarket_total_price_tablerow_textview), 10, 1);

            Double fairMarketPriceAmount = Double.parseDouble(utils.replaceComma(capitalGainCalcInputData.getFairMarketPrice()));
            Long numberOfUnits = Long.parseLong(utils.replaceComma(capitalGainCalcInputData.getNumberOfUnits()));

            capitalGainCalcDisplayData.setTotalPurchaseAmount(fairMarketPriceAmount * numberOfUnits);

            utils.setTextViewAttributes(fairMarketPriceDataTextView, utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainCalcDisplayData.getTotalPurchaseAmount())), 1, 10);

            fairMarketPriceTableRow = new TableRow(activity);
            fairMarketPriceTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            fairMarketPriceTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            fairMarketPriceTableRow.addView(fairMarketPriceTextView, 0);
            fairMarketPriceTableRow.addView(fairMarketPriceDataTextView, 1);

            tableLayout.addView(fairMarketPriceTableRow, tableLayoutIndex++);
        }
        else{
            TextView fairMarketPriceTextView = new TextView(activity);
            TextView fairMarketPriceDataTextView = new TextView(activity);
            utils.setTextViewAttributes(fairMarketPriceTextView, activity.getString(R.string.purchaseprice_tablerow_textview), 10, 1);
            utils.setTextViewAttributes(fairMarketPriceDataTextView, utils.rupeeSymbol + utils.formatAmount(utils.replaceComma(capitalGainCalcInputData.getFairMarketPrice())), 1, 10);

            capitalGainCalcDisplayData.setTotalPurchaseAmount(Double.parseDouble(capitalGainCalcInputData.getFairMarketPrice()));

            fairMarketPriceTableRow = new TableRow(activity);
            fairMarketPriceTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            fairMarketPriceTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            fairMarketPriceTableRow.addView(fairMarketPriceTextView, 0);
            fairMarketPriceTableRow.addView(fairMarketPriceDataTextView, 1);

            tableLayout.addView(fairMarketPriceTableRow, tableLayoutIndex++);
        }
    }

    private void loadSaleDetails(){
        int backGroundColorChange = 0;
        boolean saleExpenseProvided = false;
        Double saleCost = new Double(0);

        //Create the header row
        tableLayout.addView(utils.createHeaderRow(new TableRow(activity), new TextView(activity), activity.getString(R.string.saledetails_header_textview)),tableLayoutIndex++);

        TableRow saleTableRow = new TableRow(activity);

        //Create data rows

        //Sale Year
        TextView saleYearTextView = new TextView(activity);
        TextView saleYearDataTextView = new TextView(activity);

        String saleYearData = "";
        String[] saleData = capitalGainCalcInputData.getSaleDate().split("-");
        saleYearData = new StringBuffer("").append(saleData[0]).append("-").append(utils.getEnglishNameForIntegerMonth(Integer.parseInt(saleData[1]))).append("-").append(saleData[2]).toString();


        utils.setTextViewAttributes(saleYearTextView,activity.getString(R.string.saleyear_tablerow_textview),10, 1);
        utils.setTextViewAttributes(saleYearDataTextView,saleYearData,1, 10);

        saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        saleTableRow.addView(saleYearTextView,0);
        saleTableRow.addView(saleYearDataTextView,1);

        tableLayout.addView(saleTableRow,tableLayoutIndex++);

        if(isNumberOfUnitProvided) {
            //Purchase Price per Unit
            TextView salePricePerUnitTextView = new TextView(activity);
            TextView salePricePerUnitDataTextView = new TextView(activity);
            utils.setTextViewAttributes(salePricePerUnitTextView, activity.getString(R.string.saleprice_perunit_tablerow_textview), 10, 1);
            utils.setTextViewAttributes(salePricePerUnitDataTextView, utils.rupeeSymbol + utils.formatAmount(utils.replaceComma(capitalGainCalcInputData.getSaleAmount())), 1, 10);

            saleTableRow = new TableRow(activity);
            saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            saleTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            saleTableRow.addView(salePricePerUnitTextView, 0);
            saleTableRow.addView(salePricePerUnitDataTextView, 1);

            tableLayout.addView(saleTableRow, tableLayoutIndex++);

            //Sale Price for all  Unit
            TextView salePriceTotalUnitsTextView = new TextView(activity);
            TextView salePriceTotalUnitsDataTextView = new TextView(activity);
            utils.setTextViewAttributes(salePriceTotalUnitsTextView, activity.getString(R.string.saleprice_costofunit_tablerow_textview), 10, 1);
            Double saleAmount = Double.parseDouble(utils.replaceComma(capitalGainCalcInputData.getSaleAmount()));
            Long numberOfUnits = Long.parseLong(utils.replaceComma(capitalGainCalcInputData.getNumberOfUnits()));

            saleCost = saleAmount * numberOfUnits;

            utils.setTextViewAttributes(salePriceTotalUnitsDataTextView, utils.rupeeSymbol + utils.formatAmount(String.valueOf(saleCost)), 1, 10);

            saleTableRow = new TableRow(activity);
            saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            saleTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            saleTableRow.addView(salePriceTotalUnitsTextView, 0);
            saleTableRow.addView(salePriceTotalUnitsDataTextView, 1);

            tableLayout.addView(saleTableRow, tableLayoutIndex++);
        }
        else{
            TextView salePriceTextView = new TextView(activity);
            TextView salePriceDataTextView = new TextView(activity);
            utils.setTextViewAttributes(salePriceTextView, activity.getString(R.string.saleprice_tablerow_textview), 10, 1);
            utils.setTextViewAttributes(salePriceDataTextView, utils.rupeeSymbol + utils.formatAmount(utils.replaceComma(capitalGainCalcInputData.getSaleAmount())), 1, 10);

            saleCost = Double.parseDouble(utils.replaceComma(capitalGainCalcInputData.getSaleAmount()));;

            saleTableRow = new TableRow(activity);
            saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            saleTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
            saleTableRow.addView(salePriceTextView, 0);
            saleTableRow.addView(salePriceDataTextView, 1);

            tableLayout.addView(saleTableRow, tableLayoutIndex++);
        }

        //Sale Expense
        saleExpenseProvided = isExpenseProvided(capitalGainCalcInputData.getSaleExpense());
        TextView saleExpenseTextView = new TextView(activity);
        TextView saleExpenseDataTextView = new TextView(activity);
        utils.setTextViewAttributes(saleExpenseTextView,activity.getString(R.string.sale_expense_textview),10, 1);
        utils.setTextViewAttributes(saleExpenseDataTextView,saleExpenseProvided ? utils.rupeeSymbol + utils.formatAmount(utils.replaceComma(capitalGainCalcInputData.getSaleExpense())) : activity.getString(R.string.not_applicable),1, 10);

        saleTableRow = new TableRow(activity);
        saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        saleTableRow.addView(saleExpenseTextView,0);
        saleTableRow.addView(saleExpenseDataTextView,1);

        tableLayout.addView(saleTableRow,tableLayoutIndex++);

        //Total Sale cost
        TextView saleTotalCostTextView = new TextView(activity);
        TextView saleTotalCostDataTextView = new TextView(activity);
        utils.setTextViewAttributes(saleTotalCostTextView,activity.getString(R.string.saleprice_total_tablerow_textview),10, 1);
        Double saleExpense = saleExpenseProvided ? Double.parseDouble(utils.replaceComma(capitalGainCalcInputData.getSaleExpense())) : new Double(0);
        capitalGainCalcDisplayData.setTotalSaleAmount(saleCost + saleExpense);
        utils.setTextViewAttributes(saleTotalCostDataTextView,utils.rupeeSymbol + utils.formatAmount(String.valueOf(capitalGainCalcDisplayData.getTotalSaleAmount())),1, 10);

        saleTableRow = new TableRow(activity);
        saleTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        saleTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        saleTableRow.addView(saleTotalCostTextView,0);
        saleTableRow.addView(saleTotalCostDataTextView,1);

        tableLayout.addView(saleTableRow,tableLayoutIndex++);
    }

    private void loadCapitalGainDetails(){
        int backGroundColorChange = 0;

        //Create the header row
        tableLayout.addView(utils.createHeaderRow(new TableRow(activity), new TextView(activity), activity.getString(R.string.capitalgaindetails_header_textview)),tableLayoutIndex++);

        TableRow capitalGainTableRow = new TableRow(activity);

        //perform all the computation here
        if(activity instanceof MutualFundReport) {
            capitalGainCalcDisplayData = ((MutualFundReport) (activity)).calculateGainDetails(capitalGainCalcInputData, capitalGainCalcDisplayData);
        }
        else if(activity instanceof GoldCalcReportActivity){
            capitalGainCalcDisplayData = ((GoldCalcReportActivity) activity).calculateGainDetails(capitalGainCalcInputData, capitalGainCalcDisplayData);
        }
        else if(activity instanceof SharesCalcReportActivity){
            capitalGainCalcDisplayData = ((SharesCalcReportActivity) activity).calculateGainDetails(capitalGainCalcInputData, capitalGainCalcDisplayData);
        }

        //Create data rows

        //Duration of Time asset is held
        TextView durationHeldTextView = new TextView(activity);
        TextView durationHeldDataTextView = new TextView(activity);
        utils.setTextViewAttributes(durationHeldTextView,activity.getString(R.string.capitalgain_time_diff_textview),10, 1);
        utils.setTextViewAttributes(durationHeldDataTextView,utils.calculateDifferenceInPurchaseAndSaleDate(capitalGainCalcInputData.getPurchaseDate(),capitalGainCalcInputData.getSaleDate()),1, 10);

        capitalGainTableRow = new TableRow(activity);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        capitalGainTableRow.addView(durationHeldTextView,0);
        capitalGainTableRow.addView(durationHeldDataTextView,1);

        tableLayout.addView(capitalGainTableRow,tableLayoutIndex++);


        //Capital Gain Type
        TextView gainTypeTextView = new TextView(activity);
        TextView gainTypeDataTextView = new TextView(activity);
        utils.setTextViewAttributes(gainTypeTextView,activity.getString(R.string.capitalgain_type_textview),10, 1);
        utils.setTextViewAttributes(gainTypeDataTextView,capitalGainCalcDisplayData.getGainTypeDisplayValue(),1, 10);

        capitalGainTableRow = new TableRow(activity);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        capitalGainTableRow.addView(gainTypeTextView,0);
        capitalGainTableRow.addView(gainTypeDataTextView,1);

        tableLayout.addView(capitalGainTableRow,tableLayoutIndex++);

        //Capital Gain Amount

        TextView gainAmountTextView = new TextView(activity);
        TextView gainAmountDataTextView = new TextView(activity);
        utils.setTextViewAttributes(gainAmountTextView,capitalGainCalcDisplayData.getCapitalGainAmountTextView(),10, 1);
        utils.setTextViewAttributes(gainAmountDataTextView,capitalGainCalcDisplayData.getCapitalGainAmountDisplayValue(),1, 10);

        capitalGainTableRow = new TableRow(activity);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        capitalGainTableRow.addView(gainAmountTextView,0);
        capitalGainTableRow.addView(gainAmountDataTextView,1);

        tableLayout.addView(capitalGainTableRow,tableLayoutIndex++);

        //Capital Gain Tax percent

        TextView gainTaxPercentTextView = new TextView(activity);
        TextView gainTaxPercentDataTextView = new TextView(activity);
        utils.setTextViewAttributes(gainTaxPercentTextView,activity.getString(R.string.capitalgain_tax_percent_textview),10, 1);
        utils.setTextViewAttributes(gainTaxPercentDataTextView,capitalGainCalcDisplayData.getCapitalGainTaxPercentDisplayValue(),1, 10);

        capitalGainTableRow = new TableRow(activity);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        capitalGainTableRow.addView(gainTaxPercentTextView,0);
        capitalGainTableRow.addView(gainTaxPercentDataTextView,1);

        tableLayout.addView(capitalGainTableRow,tableLayoutIndex++);

        //Capital Gain Tax amount

        TextView gainTaxAmountTextView = new TextView(activity);
        TextView gainTaxAmountDataTextView = new TextView(activity);
        utils.setTextViewAttributes(gainTaxAmountTextView,activity.getString(R.string.capitalgain_tax_amount_textview),10, 1);
        utils.setTextViewAttributes(gainTaxAmountDataTextView,capitalGainCalcDisplayData.getGetCapitalGainTaxAmountDisplayValue(),1, 10);

        capitalGainTableRow = new TableRow(activity);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        capitalGainTableRow.addView(gainTaxAmountTextView,0);
        capitalGainTableRow.addView(gainTaxAmountDataTextView,1);

        tableLayout.addView(capitalGainTableRow,tableLayoutIndex++);

        //Net profit

        TextView netProfitTextView = new TextView(activity);
        TextView netProfitDataTextView = new TextView(activity);
        utils.setTextViewAttributes(netProfitTextView,activity.getString(R.string.capitalgain_net_profit_textview),10, 1);
        utils.setTextViewAttributes(netProfitDataTextView,capitalGainCalcDisplayData.getNetProfitDisplayValue(),1, 10);

        capitalGainTableRow = new TableRow(activity);
        capitalGainTableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        capitalGainTableRow.setBackgroundColor(utils.getBackGroundColorForReport(backGroundColorChange++));
        capitalGainTableRow.addView(netProfitTextView,0);
        capitalGainTableRow.addView(netProfitDataTextView,1);

        tableLayout.addView(capitalGainTableRow,tableLayoutIndex++);
    }

    private void loadAdViewDetails(){
        TableRow adViewTableRow = new TableRow(activity);
        String adUnitID = "";

        if(activity instanceof MutualFundReport) {
            adUnitID = activity.getString(R.string.banner_ad_unit_id_mutual_fund_report_page);
        }
        else if(activity instanceof GoldCalcReportActivity){
            adUnitID = activity.getString(R.string.banner_ad_unit_id_gold_calc_report_page);
        }
        else if(activity instanceof SharesCalcReportActivity){
            adUnitID = activity.getString(R.string.banner_ad_unit_id_shares_calc_report_page);
        }

        LinearLayout adLayout = new LinearLayout(activity);
        AdView reportAdView = new AdView(activity);
        reportAdView.setAdSize(AdSize.BANNER);
        reportAdView.setAdUnitId(adUnitID);
        AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        reportAdView.loadAd(adRequest);

        LinearLayout.LayoutParams linearLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);
        adLayout.addView(reportAdView,linearLayoutParams);

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);
        params.span = 2;
        params.setMargins(10,10,10,0);
        adViewTableRow.addView(adLayout,0, params);

        tableLayout.addView(adViewTableRow,tableLayoutIndex++);
    }

    private void isNumberOfUnitsApplicable(){
        isNumberOfUnitProvided = capitalGainCalcInputData.getNumberOfUnits() != null && capitalGainCalcInputData.getNumberOfUnits().length() > 0 ? true : false;
    }

    private boolean isExpenseProvided(String expenseAmount){
        return expenseAmount != null && expenseAmount.length() > 0 ? true : false;
    }
}
