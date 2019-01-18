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
import balajirajagopal.com.capitalgaincalculator.enums.DATETYPE;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcDisplayData;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcInputData;
import balajirajagopal.com.capitalgaincalculator.utils.CapitalGainCalculatorUtils;

public class GoldCalcActivity extends AppCompatActivity {

    private CapitalGainCalculatorUtils utils;
    private CapitalGainCalcInputData capitalGainCalcInputData;
    private CapitalGainCalcDisplayData capitalGainCalcDisplayData;
    private static KeyListener fairMarketValueEditTextKeyListener;
    private static CustomTextWatcher fairMarketValueEditTexTexWatcher;
    private static boolean fairMarketValueEditTextRequired = false;
    private static Intent intent;
    public static String GOLD_INPUT_DATA = "GOLD_INPUT_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_calc);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.goldCalcActivityAdView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        utils = new CapitalGainCalculatorUtils(false);

        intent = new Intent(this, GoldCalcReportActivity.class);

        setDatePickers();
        addWatcherToEditText();
        addButtonAction();
    }

    private void setDatePickers(){
        new CustomDatePicker(this,R.id.purchaseDateGoldCalcEditText,DATETYPE.PURCHASE_DATE);
        new CustomDatePicker(this,R.id.saleDateGoldCalcEditText, DATETYPE.SALE_DATE);
    }

    private void addWatcherToEditText(){

        EditText purchasePriceEditText = (EditText) findViewById(R.id.puchasePriceGoldCalcEditText);
        EditText purchaseExpenseEditText = (EditText) findViewById(R.id.purchaseExpenseGoldCalcEditText);
        EditText salePriceEditText = (EditText) findViewById(R.id.salePriceGoldCalcEditText);
        EditText saleExpenseEditText = (EditText) findViewById(R.id.saleExpenseGoldCalcEditText);
        EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceGoldCalcEditText);
        TextView fairMartketValueTextView = (TextView) findViewById(R.id.fairMarketPriceGoldCalcTextView);

        purchasePriceEditText.addTextChangedListener(new CustomTextWatcher(purchasePriceEditText,this));
        purchaseExpenseEditText.addTextChangedListener(new CustomTextWatcher(purchaseExpenseEditText, this));
        salePriceEditText.addTextChangedListener(new CustomTextWatcher(salePriceEditText, this));
        saleExpenseEditText.addTextChangedListener(new CustomTextWatcher(saleExpenseEditText, this));

        fairMarketValueEditTextKeyListener = fairMarketValueEditText.getKeyListener();
        fairMarketValueEditTexTexWatcher = new CustomTextWatcher(fairMarketValueEditText,this);
        fairMarketValueEditText.setKeyListener(null);
        fairMarketValueEditText.addTextChangedListener(fairMarketValueEditTexTexWatcher);

        fairMartketValueTextView.setText(getString(R.string.fair_market_share_value_textview) + getString(R.string.fair_market_share_value_purchasedate_textview));

    }

    private void addButtonAction(){

        Button calculateButton = (Button) findViewById(R.id.goldCalcButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapitalGainCalcInputData capitalGainCalcInputData = new CapitalGainCalcInputData();
                capitalGainCalcInputData.setSaleDate(((EditText) findViewById(R.id.saleDateGoldCalcEditText)).getText().toString());
                capitalGainCalcInputData.setPurchaseDate(((EditText) findViewById(R.id.purchaseDateGoldCalcEditText)).getText().toString());
                capitalGainCalcInputData.setSaleAmount(((EditText) findViewById(R.id.salePriceGoldCalcEditText)).getText().toString());
                capitalGainCalcInputData.setPurchaseAmount(((EditText) findViewById(R.id.puchasePriceGoldCalcEditText)).getText().toString());
                capitalGainCalcInputData.setSaleExpense(((EditText) findViewById(R.id.saleExpenseGoldCalcEditText)).getText().toString());
                capitalGainCalcInputData.setPurchaseExpense(((EditText) findViewById(R.id.purchaseExpenseGoldCalcEditText)).getText().toString());
                capitalGainCalcInputData.setFairMarketPrice(((EditText) findViewById(R.id.fairMarketPriceGoldCalcEditText)).getText().toString());
                capitalGainCalcInputData.setHeaderMessage(((TextView) findViewById(R.id.fairMarketPriceGoldCalcTextView)).getText().toString());
                capitalGainCalcInputData.setAssettype(ASSETTYPE.GOLD);

                intent.putExtra(GOLD_INPUT_DATA,capitalGainCalcInputData);

                startActivity(intent);
            }
        });
        calculateButton.setEnabled(false);

    }

    public void checkButtonCanBeEnabled(){

        Button calculateButton = (Button) findViewById(R.id.goldCalcButton);

        EditText purchasePriceEditText = (EditText) findViewById(R.id.puchasePriceGoldCalcEditText);
        EditText salePriceEditText = (EditText) findViewById(R.id.salePriceGoldCalcEditText);
        EditText purchaseYearEditText = (EditText) findViewById(R.id.purchaseDateGoldCalcEditText);
        EditText saleYearEditText = (EditText) findViewById(R.id.saleDateGoldCalcEditText);
        EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceGoldCalcEditText);

        if(purchaseYearEditText.getText().toString().length() > 0){
            enableFairMarketValueEditText(purchaseYearEditText.getText().toString());
        }

        if(purchasePriceEditText.getText().toString().length() == 0 ||
                purchaseYearEditText.getText().toString().length() == 0 ||
                saleYearEditText.getText().toString().length() == 0 ||
                salePriceEditText.getText().toString().length() == 0 ||
                (fairMarketValueEditTextRequired && fairMarketValueEditText.getText().toString().length() == 0)){
            calculateButton.setEnabled(false);
            return;
        }

        calculateButton.setEnabled(true);
    }


    public void enableFairMarketValueEditText(String purchaseDate){
        EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceGoldCalcEditText);
        if(purchaseDate.length() != 0 && utils.fairMarketPriceRequired(purchaseDate,getString(R.string.index_date_limit))) {
            fairMarketValueEditText.setKeyListener(fairMarketValueEditTextKeyListener);
            fairMarketValueEditTextRequired = true;
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
        EditText purchaseDateEditText = (EditText) findViewById(R.id.purchaseDateGoldCalcEditText);
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
        EditText saleDateEditText = (EditText) findViewById(R.id.saleDateGoldCalcEditText);
        EditText fairMarketValueEditText = (EditText) findViewById(R.id.fairMarketPriceGoldCalcEditText);
        if(saleDateEditText.getText().toString().length() > 0) {
            if (utils.differenceOfDates(purchaseDate, saleDateEditText.getText().toString()) > 0){
                saleDateEditText.setText("");
            }
        }

        if(!utils.fairMarketPriceRequired(purchaseDate,getString(R.string.index_date_limit))) {
            fairMarketValueEditText.removeTextChangedListener(fairMarketValueEditTexTexWatcher);
            fairMarketValueEditText.setText("");
            fairMarketValueEditText.addTextChangedListener(fairMarketValueEditTexTexWatcher);
            fairMarketValueEditText.setKeyListener(null);
            fairMarketValueEditTextRequired = false;
        }
    }
}
