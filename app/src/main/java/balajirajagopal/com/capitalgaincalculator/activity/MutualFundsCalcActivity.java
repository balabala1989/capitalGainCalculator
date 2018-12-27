package balajirajagopal.com.capitalgaincalculator.activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import balajirajagopal.com.capitalgaincalculator.enums.ASSETTYPE;
import balajirajagopal.com.capitalgaincalculator.enums.ASSET_SUBTYPE;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcInputData;
import balajirajagopal.com.capitalgaincalculator.utils.CapitalGainCalculatorUtils;
import balajirajagopal.com.capitalgaincalculator.custom.CustomDatePicker;
import balajirajagopal.com.capitalgaincalculator.custom.CustomTextWatcher;
import balajirajagopal.com.capitalgaincalculator.enums.DATETYPE;
import balajirajagopal.com.capitalgaincalculator.R;

public class MutualFundsCalcActivity extends AppCompatActivity {

    private static Intent intent;
    public static final String MUTUAL_FUND_INPUT_DATA = "MUTUAL_FUND_INPUT_DATA";
    private static KeyListener fairMarketValueEditTextKeyListener;
    private static CustomTextWatcher fairMarketValueEditTexTexWatcher;
    private static CapitalGainCalculatorUtils utils;
    private static boolean fairMarketValueEditTextRequired = false;
    private ASSET_SUBTYPE assetSubtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_funds_calc);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.mutualFundCalAdView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        utils = new CapitalGainCalculatorUtils(false);

        intent = new Intent(this,MutualFundReport.class);
        addRadioGroupAction();
        setDatePickers();
        addButtonActions();
        addWatcherToEditText();

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mutual_funds_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

   private void addRadioGroupAction(){
       RadioGroup radioGroup = (RadioGroup) findViewById(R.id.typeRadioGroup);
       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.debtMutualFund:
                       assetSubtype = ASSET_SUBTYPE.MUTUAL_FUNDS_DEBT;
                       break;
                   case R.id.equityMutualFund:
                       assetSubtype = ASSET_SUBTYPE.MUTUAL_FUNDS_EQUITY;
                       break;
               }
               checkButtonCanBeEnabled();
           }
       });
   }

   private void setDatePickers(){

       new CustomDatePicker(this,R.id.purchaseDateEditText, DATETYPE.PURCHASE_DATE);
       new CustomDatePicker(this,R.id.saleDateEditText, DATETYPE.SALE_DATE);

   }

   public  void addButtonActions(){
       Button calculateButton = (Button) findViewById(R.id.mutualFundCalcButton);
       calculateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                CapitalGainCalcInputData capitalGainCalcInputData = new CapitalGainCalcInputData();
                capitalGainCalcInputData.setSaleDate(((EditText) findViewById(R.id.saleDateEditText)).getText().toString());
                capitalGainCalcInputData.setPurchaseDate(((EditText) findViewById(R.id.purchaseDateEditText)).getText().toString());
                capitalGainCalcInputData.setSaleAmount(((EditText) findViewById(R.id.salePriceEditText)).getText().toString());
                capitalGainCalcInputData.setPurchaseAmount(((EditText) findViewById(R.id.purchaseAmountEditText)).getText().toString());
                capitalGainCalcInputData.setSaleExpense(((EditText) findViewById(R.id.saleExpenseEditText)).getText().toString());
                capitalGainCalcInputData.setPurchaseExpense(((EditText) findViewById(R.id.purchaseExpenseEditText)).getText().toString());
                capitalGainCalcInputData.setFairMarketPrice(((EditText) findViewById(R.id.fairMarketPriceEditText)).getText().toString());
                capitalGainCalcInputData.setNumberOfUnits(((EditText) findViewById(R.id.numberOfUnitEditText)).getText().toString());
                capitalGainCalcInputData.setHeaderMessage(((TextView) findViewById(R.id.fairMarketValueTextView)).getText().toString());
                capitalGainCalcInputData.setAssettype(ASSETTYPE.MUTUAL_FUNDS);
                capitalGainCalcInputData.setAssetSubtype(assetSubtype);

                intent.putExtra(MUTUAL_FUND_INPUT_DATA,capitalGainCalcInputData);

                startActivity(intent);
           }
       });
       calculateButton.setEnabled(false);
   }

    public void checkButtonCanBeEnabled(){
       boolean radioButtonChecked = false;

       Button calculateButton = (Button) findViewById(R.id.mutualFundCalcButton);
       RadioButton debtRadioButton = (RadioButton) findViewById(R.id.debtMutualFund);
       RadioButton equityRadioButton = (RadioButton) findViewById(R.id.equityMutualFund);
       if(debtRadioButton.isChecked())
           radioButtonChecked = true;
       if(equityRadioButton.isChecked())
           radioButtonChecked = true;

       EditText numberOfUnitsEditText = (EditText) findViewById(R.id.numberOfUnitEditText);
       EditText purchasePriceEditText = (EditText) findViewById(R.id.purchaseAmountEditText);
       EditText salePriceEditText = (EditText) findViewById(R.id.salePriceEditText);
       EditText purchaseYearEditText = (EditText) findViewById(R.id.purchaseDateEditText);
       EditText saleYearEditText = (EditText) findViewById(R.id.saleDateEditText);
       EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceEditText);

       if(purchaseYearEditText.getText().toString().length() > 0){
           enableFairMarketValueEditText(purchaseYearEditText.getText().toString());
       }

       if(!radioButtonChecked ||
               numberOfUnitsEditText.getText().toString().length() == 0 ||
               purchasePriceEditText.getText().toString().length() == 0 ||
               purchaseYearEditText.getText().toString().length() == 0 ||
               saleYearEditText.getText().toString().length() == 0 ||
               salePriceEditText.getText().toString().length() == 0 ||
               (fairMarketValueEditTextRequired && fairMarketValueEditText.getText().toString().length() == 0)){
           calculateButton.setEnabled(false);
           return;
       }

       calculateButton.setEnabled(true);
    }

    private void addWatcherToEditText(){
       EditText numberOfUnitsEditText = (EditText) findViewById(R.id.numberOfUnitEditText);
       EditText purchasePriceEditText = (EditText) findViewById(R.id.purchaseAmountEditText);
       EditText purchaseExpenseEditText = (EditText) findViewById(R.id.purchaseExpenseEditText);
       EditText salePriceEditText = (EditText) findViewById(R.id.salePriceEditText);
       EditText saleExpenseEditText = (EditText) findViewById(R.id.saleExpenseEditText);
       EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceEditText);

       numberOfUnitsEditText.addTextChangedListener(new CustomTextWatcher(numberOfUnitsEditText,this));
       purchasePriceEditText.addTextChangedListener(new CustomTextWatcher(purchasePriceEditText,this));
       purchaseExpenseEditText.addTextChangedListener(new CustomTextWatcher(purchaseExpenseEditText,this));
       salePriceEditText.addTextChangedListener(new CustomTextWatcher(salePriceEditText,this));
       saleExpenseEditText.addTextChangedListener(new CustomTextWatcher(saleExpenseEditText,this));
       fairMarketValueEditTextKeyListener = fairMarketValueEditText.getKeyListener();
       fairMarketValueEditTexTexWatcher = new CustomTextWatcher(fairMarketValueEditText,this);
       fairMarketValueEditText.setKeyListener(null);
       fairMarketValueEditText.addTextChangedListener(fairMarketValueEditTexTexWatcher);
    }

    public void enableFairMarketValueEditText(String purchaseDate){
        RadioButton equityRadioButton = (RadioButton) findViewById(R.id.equityMutualFund);
        EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceEditText);
        TextView fairMarketValueTextView = (TextView) findViewById(R.id.fairMarketValueTextView);
        if(purchaseDate.length() != 0) {
            if (equityRadioButton.isChecked() && utils.fairMarketPriceRequired(purchaseDate, getString(R.string.mutual_funds_equity_and_listed_shares_index_date_limit))) {
                fairMarketValueTextView.setText(getString(R.string.fair_market_share_value_textview) + " " + getString(R.string.fair_market_share_value_equity_textview));
                fairMarketValueEditText.setKeyListener(fairMarketValueEditTextKeyListener);
                fairMarketValueEditTextRequired = true;
            }
            if(utils.fairMarketPriceRequired(purchaseDate,getString(R.string.index_date_limit))){
                fairMarketValueTextView.setText(getString(R.string.fair_market_share_value_textview) + " " + getString(R.string.fair_market_share_value_purchasedate_textview));
                fairMarketValueEditText.setKeyListener(fairMarketValueEditTextKeyListener);
                fairMarketValueEditTextRequired = true;
            }

        }
        else{
            fairMarketValueEditText.removeTextChangedListener(fairMarketValueEditTexTexWatcher);
            fairMarketValueEditText.setText("");
            fairMarketValueEditText.addTextChangedListener(fairMarketValueEditTexTexWatcher);
            fairMarketValueEditText.setKeyListener(null);
            fairMarketValueEditTextRequired = false;
        }
    }

    public DatePickerDialog setMinimumSaleDate(DatePickerDialog dialog){
       EditText purchaseDateEditText = (EditText) findViewById(R.id.purchaseDateEditText);
       if(purchaseDateEditText.getText().toString().length() > 0){
           SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
           Date minimumSaleDate = null;
           try {
               minimumSaleDate = format.parse(purchaseDateEditText.getText().toString());
           } catch (ParseException e) {
               e.printStackTrace();
               return dialog;
           }
           dialog.getDatePicker().setMinDate(minimumSaleDate.getTime());
       }
       return dialog;
    }

    public void resetSaleDateOnChangeOfPurchaseDate(String purchaseDate){
       EditText saleDateEditText = (EditText) findViewById(R.id.saleDateEditText);
       EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceEditText);
       RadioButton equityRadioButton = (RadioButton) findViewById(R.id.equityMutualFund);
       if(saleDateEditText.getText().toString().length() > 0) {
           if (utils.differenceOfDates(purchaseDate, saleDateEditText.getText().toString()) > 0){
               saleDateEditText.setText("");
           }
       }

       if(!(equityRadioButton.isChecked() && utils.fairMarketPriceRequired(purchaseDate, getString(R.string.mutual_funds_equity_and_listed_shares_index_date_limit))) && !utils.fairMarketPriceRequired(purchaseDate,getString(R.string.index_date_limit))) {
           fairMarketValueEditText.removeTextChangedListener(fairMarketValueEditTexTexWatcher);
           fairMarketValueEditText.setText("");
           fairMarketValueEditText.addTextChangedListener(fairMarketValueEditTexTexWatcher);
           fairMarketValueEditText.setKeyListener(null);
           fairMarketValueEditTextRequired = false;
       }
    }
}
