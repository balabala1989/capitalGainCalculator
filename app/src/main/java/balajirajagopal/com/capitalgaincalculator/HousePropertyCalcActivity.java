package balajirajagopal.com.capitalgaincalculator;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HousePropertyCalcActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static String[] spinnerValue;
    private static Intent intent;
    public static final String SALEYEAR = "SALEYEAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_property_calc);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        /* Populate the Sale Year value
         * In India Financial year is from Apr to Mar.
         * So if the app is opened before March make year as Current Year -1 and Currenct Year otherwise
         * make the year as current year and current year + 1
        */
        String saleYearSpinnerValue = "";
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        intent = getIntent();

        if(month > 2)
            saleYearSpinnerValue = String.valueOf(year) + "-" + String.valueOf(year + 1);
        else
            saleYearSpinnerValue = String.valueOf(year - 1) + "-" + String.valueOf(year);

        spinnerValue = new  String[1];
        spinnerValue[0] = saleYearSpinnerValue;

        Spinner spinner = (Spinner) findViewById(R.id.saleYearSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HousePropertyCalcActivity.this, R.layout.support_simple_spinner_dropdown_item,spinnerValue);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedString = "";
        switch (position)
        {
            case 0:
                selectedString = (String) parent.getItemAtPosition(position);
                break;
            default:

        }

        intent.putExtra(SALEYEAR,selectedString);
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
