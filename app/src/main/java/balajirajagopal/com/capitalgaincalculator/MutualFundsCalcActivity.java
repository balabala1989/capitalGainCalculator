package balajirajagopal.com.capitalgaincalculator;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MutualFundsCalcActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String MUTUAL_FUND_TYPE = "MUTUAL_FUND_TYPE";
    public static final String DEBT_MUTUAL_FUND = "D";
    public static final String EQUITY_MUTUAL_FUND = "E";
    private static Intent intent;

    public static final String MUTUAL_FUND_SALEYEAR = "MUTUAL_FUND_SALEYEAR";
    public static final String MUTUAL_FUND_SALEAMOUNT = "MUTUAL_FUND_SALEAMOUNT";
    public static final String MUTUAL_FUND_PURCHASEYEAR = "MUTUAL_FUND_PURCHASEYEAR";
    public static final String MUTUAL_FUND_PURCHASEAMOUNT = "MUTUAL_FUND_PURCHASEAMOUNT";
    public static final String MUTUAL_FUNDS_NUMBEROFUNITS = "MUTUAL_FUND_NUMBEROFUNITS";
    public static final String MUTUAL_FUNDS_SALEEXPENSE = "MUTUAL_FUND_SALEEXPENSE";
    public static final String MUTUAL_FUNDS_PURCHASEEXPENSE = "MUTUAL_FUND_PURCHASEEXPENSE";

    public static final String EMPTY_STRING = "";

    private static final int HIDDEN_ITEM_INDEX = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_funds_calc);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.mutualFundCalAdView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        intent = new Intent(this,MutualFundReport.class);
        initialize();
        addRadioGroupAction();
        loadSpinnerValues();
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
                       intent.putExtra(MUTUAL_FUND_TYPE,DEBT_MUTUAL_FUND);
                       break;
                   case R.id.equityMutualFund:
                       intent.putExtra(MUTUAL_FUND_TYPE,EQUITY_MUTUAL_FUND);
                       break;
                   default:
                       intent.putExtra(MUTUAL_FUND_TYPE,"");
               }
               checkButtonCanBeEnabled();
           }
       });
   }

   private void loadSpinnerValues(){

       Spinner saleYearSpinner = (Spinner) findViewById(R.id.saleYearSpinner);
       CustomSpinnerAdapter saleYearAdapter = new CustomSpinnerAdapter(MutualFundsCalcActivity.this, R.layout.support_simple_spinner_dropdown_item,CapitalGainCalculatorUtils.calculateSaleYear(),HIDDEN_ITEM_INDEX);
       saleYearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
       saleYearSpinner.setAdapter(saleYearAdapter);
       saleYearSpinner.setOnItemSelectedListener(this);

       Spinner purchaseYearSpinner = (Spinner) findViewById(R.id.purchaseYearSpinner);
       CustomSpinnerAdapter purchaseYearAdapter = new CustomSpinnerAdapter(MutualFundsCalcActivity.this, R.layout.support_simple_spinner_dropdown_item,CapitalGainCalculatorUtils.caluclatePurchaseYears(),HIDDEN_ITEM_INDEX);
       purchaseYearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
       purchaseYearSpinner.setAdapter(purchaseYearAdapter);
       purchaseYearSpinner.setOnItemSelectedListener(this);
   }

   private void initialize()
   {
       intent.putExtra(MUTUAL_FUND_TYPE,EMPTY_STRING);
       intent.putExtra(MUTUAL_FUND_PURCHASEYEAR,EMPTY_STRING);
       intent.putExtra(MUTUAL_FUND_PURCHASEAMOUNT,EMPTY_STRING);
       intent.putExtra(MUTUAL_FUND_SALEYEAR,EMPTY_STRING);
       intent.putExtra(MUTUAL_FUND_SALEAMOUNT,EMPTY_STRING);
       intent.putExtra(MUTUAL_FUNDS_SALEEXPENSE,EMPTY_STRING);
       intent.putExtra(MUTUAL_FUNDS_PURCHASEEXPENSE,EMPTY_STRING);
       intent.putExtra(MUTUAL_FUNDS_NUMBEROFUNITS,EMPTY_STRING);
   }

   public  void addButtonActions(){
       Button calculateButton = (Button) findViewById(R.id.mutualFundCalcButton);
       calculateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                intent.putExtra(MUTUAL_FUND_SALEAMOUNT,((EditText) findViewById(R.id.salePriceEditText)).getText().toString());
                intent.putExtra(MUTUAL_FUND_PURCHASEAMOUNT, ((EditText) findViewById(R.id.purchaseAmountEditText)).getText().toString());
                intent.putExtra(MUTUAL_FUNDS_PURCHASEEXPENSE, ((EditText) findViewById(R.id.purchaseExpenseEditText)).getText().toString());
                intent.putExtra(MUTUAL_FUNDS_SALEEXPENSE, ((EditText) findViewById(R.id.saleExpenseEditText)).getText().toString());
                intent.putExtra(MUTUAL_FUNDS_NUMBEROFUNITS, ((EditText) findViewById(R.id.numberOfUnitEditText)).getText().toString());
                startActivity(intent);
           }
       });
       calculateButton.setEnabled(false);
   }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       if(parent.getId() == R.id.purchaseYearSpinner)
           intent.putExtra(MUTUAL_FUND_PURCHASEYEAR,(String)parent.getItemAtPosition(position));
       if(parent.getId() == R.id.saleYearSpinner)
           intent.putExtra(MUTUAL_FUND_SALEYEAR, (String)parent.getItemAtPosition(position));
       checkButtonCanBeEnabled();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

       Spinner purchaseYearSpinner = (Spinner) findViewById(R.id.purchaseYearSpinner);
       Spinner saleYearSpinner = (Spinner) findViewById(R.id.saleYearSpinner);
       EditText numberOfUnitsEditText = (EditText) findViewById(R.id.numberOfUnitEditText);
       EditText purchasePriceEditText = (EditText) findViewById(R.id.purchaseAmountEditText);
       EditText purchaseExpenseEditText = (EditText) findViewById(R.id.purchaseExpenseEditText);
       EditText salePriceEditText = (EditText) findViewById(R.id.salePriceEditText);
       EditText saleExpenseEditText = (EditText) findViewById(R.id.saleExpenseEditText);

       if(!radioButtonChecked ||
               purchaseYearSpinner.getSelectedItemPosition() == 0 ||
               saleYearSpinner.getSelectedItemPosition() == 0 ||
               numberOfUnitsEditText.getText().toString().length() == 0 ||
               purchasePriceEditText.getText().toString().length() == 0 ||
               purchaseExpenseEditText.getText().toString().length() == 0 ||
               salePriceEditText.getText().toString().length() == 0 ||
               saleExpenseEditText.getText().toString().length() == 0){
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

       numberOfUnitsEditText.addTextChangedListener(watcher);
       purchasePriceEditText.addTextChangedListener(watcher);
       purchaseExpenseEditText.addTextChangedListener(watcher);
       salePriceEditText.addTextChangedListener(watcher);
       saleExpenseEditText.addTextChangedListener(watcher);
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkButtonCanBeEnabled();
        }
    };

}
