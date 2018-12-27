package balajirajagopal.com.capitalgaincalculator.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import balajirajagopal.com.capitalgaincalculator.R;
import balajirajagopal.com.capitalgaincalculator.custom.CustomDatePicker;
import balajirajagopal.com.capitalgaincalculator.custom.CustomTextWatcher;
import balajirajagopal.com.capitalgaincalculator.enums.ASSETTYPE;
import balajirajagopal.com.capitalgaincalculator.enums.ASSET_SUBTYPE;
import balajirajagopal.com.capitalgaincalculator.enums.DATETYPE;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcInputData;
import balajirajagopal.com.capitalgaincalculator.utils.CapitalGainCalculatorUtils;

public class SharesCalcActivity extends AppCompatActivity {

    private static Intent intent;
    public static final String SHARES_INPUT_DATA = "SHARES_INPUT_DATA";
    private static KeyListener fairMarketValueEditTextKeyListener;
    private static CustomTextWatcher fairMarketValueEditTexTexWatcher;
    private static CapitalGainCalculatorUtils utils;
    private static boolean fairMarketValueEditTextRequired = false;
    private ASSET_SUBTYPE assetSubtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shares_calc);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.shareCalcAdView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        utils = new CapitalGainCalculatorUtils(false);

        intent = new Intent(this,SharesCalcReportActivity.class);

        addRadioGroupAction();
        setDatePickers();
        addButtonActions();
        addWatcherToEditText();
    }

    private void addRadioGroupAction(){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sharesTypeRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.listedSharesRadioButton:
                        assetSubtype = ASSET_SUBTYPE.SHARES_LISTED;
                        break;
                    case R.id.unlistedSharesRadioButton:
                        assetSubtype = ASSET_SUBTYPE.SHARES_UNLISTED;
                        break;
                }
                checkButtonCanBeEnabled();
            }
        });
    }

    private void setDatePickers(){

        new CustomDatePicker(this,R.id.purchaseDateSharesEditText, DATETYPE.PURCHASE_DATE);
        new CustomDatePicker(this,R.id.saleDateSharesEditText, DATETYPE.SALE_DATE);

    }

    public  void addButtonActions(){
        Button calculateButton = (Button) findViewById(R.id.shareCalcButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapitalGainCalcInputData capitalGainCalcInputData = new CapitalGainCalcInputData();
                capitalGainCalcInputData.setSaleDate(((EditText) findViewById(R.id.saleDateSharesEditText)).getText().toString());
                capitalGainCalcInputData.setPurchaseDate(((EditText) findViewById(R.id.purchaseDateSharesEditText)).getText().toString());
                capitalGainCalcInputData.setSaleAmount(((EditText) findViewById(R.id.salePriceSharesEditText)).getText().toString());
                capitalGainCalcInputData.setPurchaseAmount(((EditText) findViewById(R.id.purchasePriceSharesEditText)).getText().toString());
                capitalGainCalcInputData.setSaleExpense(((EditText) findViewById(R.id.saleExpenseSharesEditText)).getText().toString());
                capitalGainCalcInputData.setPurchaseExpense(((EditText) findViewById(R.id.purchaseExpenseSharesEditText)).getText().toString());
                capitalGainCalcInputData.setFairMarketPrice(((EditText) findViewById(R.id.fairMarketPriceSharesEditText)).getText().toString());
                capitalGainCalcInputData.setNumberOfUnits(((EditText) findViewById(R.id.numberOfUnitsSharesEditText)).getText().toString());
                capitalGainCalcInputData.setHeaderMessage(((TextView) findViewById(R.id.fairMarketPriceSharesTextView)).getText().toString());
                capitalGainCalcInputData.setAssettype(ASSETTYPE.SHARES);
                capitalGainCalcInputData.setAssetSubtype(assetSubtype);

                intent.putExtra(SHARES_INPUT_DATA,capitalGainCalcInputData);

                startActivity(intent);
            }
        });
        calculateButton.setEnabled(false);
    }

    public void checkButtonCanBeEnabled(){
        boolean radioButtonChecked = false;

        Button calculateButton = (Button) findViewById(R.id.shareCalcButton);
        RadioButton listedShareRadioButton = (RadioButton) findViewById(R.id.listedSharesRadioButton);
        RadioButton unlistedShareRadioButton = (RadioButton) findViewById(R.id.unlistedSharesRadioButton);
        if(listedShareRadioButton.isChecked())
            radioButtonChecked = true;
        if(unlistedShareRadioButton.isChecked())
            radioButtonChecked = true;

        EditText numberOfUnitsEditText = (EditText) findViewById(R.id.numberOfUnitsSharesEditText);
        EditText purchasePriceEditText = (EditText) findViewById(R.id.purchasePriceSharesEditText);
        EditText salePriceEditText = (EditText) findViewById(R.id.salePriceSharesEditText);
        EditText purchaseYearEditText = (EditText) findViewById(R.id.purchaseDateSharesEditText);
        EditText saleYearEditText = (EditText) findViewById(R.id.saleDateSharesEditText);
        EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceSharesEditText);

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
        EditText numberOfUnitsEditText = (EditText) findViewById(R.id.numberOfUnitsSharesEditText);
        EditText purchasePriceEditText = (EditText) findViewById(R.id.purchasePriceSharesEditText);
        EditText purchaseExpenseEditText = (EditText) findViewById(R.id.purchaseExpenseSharesEditText);
        EditText salePriceEditText = (EditText) findViewById(R.id.salePriceSharesEditText);
        EditText saleExpenseEditText = (EditText) findViewById(R.id.saleExpenseSharesEditText);
        EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceSharesEditText);

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
        RadioButton listedRadioButton = (RadioButton) findViewById(R.id.equityMutualFund);
        EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceEditText);
        TextView fairMarketValueTextView = (TextView) findViewById(R.id.fairMarketValueTextView);
        if(purchaseDate.length() != 0) {
            if (listedRadioButton.isChecked() && utils.fairMarketPriceRequired(purchaseDate, getString(R.string.mutual_funds_equity_and_listed_shares_index_date_limit))) {
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
        EditText purchaseDateEditText = (EditText) findViewById(R.id.purchaseDateSharesEditText);
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
        EditText saleDateEditText = (EditText) findViewById(R.id.saleDateSharesEditText);
        EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceSharesEditText);
        RadioButton listedRadioButton = (RadioButton) findViewById(R.id.listedSharesRadioButton);
        if(saleDateEditText.getText().toString().length() > 0) {
            if (utils.differenceOfDates(purchaseDate, saleDateEditText.getText().toString()) > 0){
                saleDateEditText.setText("");
            }
        }

        if(!(listedRadioButton.isChecked() && utils.fairMarketPriceRequired(purchaseDate, getString(R.string.mutual_funds_equity_and_listed_shares_index_date_limit))) && !utils.fairMarketPriceRequired(purchaseDate,getString(R.string.index_date_limit))) {
            fairMarketValueEditText.removeTextChangedListener(fairMarketValueEditTexTexWatcher);
            fairMarketValueEditText.setText("");
            fairMarketValueEditText.addTextChangedListener(fairMarketValueEditTexTexWatcher);
            fairMarketValueEditText.setKeyListener(null);
            fairMarketValueEditTextRequired = false;
        }
    }
}
