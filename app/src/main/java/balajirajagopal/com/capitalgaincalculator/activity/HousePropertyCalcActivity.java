package balajirajagopal.com.capitalgaincalculator.activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import balajirajagopal.com.capitalgaincalculator.utils.CapitalGainCalculatorUtils;
import balajirajagopal.com.capitalgaincalculator.custom.CustomSpinnerAdapter;
import balajirajagopal.com.capitalgaincalculator.R;

public class HousePropertyCalcActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private static Intent intent;
    public static final String HOUSE_PROPERTY_SALEYEAR = "HOUSE_PROPERTY_SALEYEAR";
    private static final int HIDDEN_ITEM_INDEX = 0;
    private static CapitalGainCalculatorUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_property_calc);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        utils = new CapitalGainCalculatorUtils(false);

        intent = getIntent();

        Spinner spinner = (Spinner) findViewById(R.id.saleYearSpinner);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(HousePropertyCalcActivity.this, R.layout.support_simple_spinner_dropdown_item,utils.calculateSaleYear(),HIDDEN_ITEM_INDEX);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        intent.putExtra(HOUSE_PROPERTY_SALEYEAR,(String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_house_property_calc, menu);
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
    }
*/
}
