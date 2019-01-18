package balajirajagopal.com.capitalgaincalculator.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import balajirajagopal.com.capitalgaincalculator.R;

public class CalculatorHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_home);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.calcHomeAdView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        ImageView housePropertyImageView = (ImageView) findViewById(R.id.housePropertyImageView);
        final Intent housePropertyIntent = new Intent(this, HousePropertyCalcActivity.class);

        housePropertyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(housePropertyIntent);
            }
        });

        ImageView mutualFundsImageView = (ImageView) findViewById(R.id.mutualFundImageView);
        final Intent mutualFundsIntent = new Intent(this, MutualFundsCalcActivity.class);

        mutualFundsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mutualFundsIntent);
            }
        });

        ImageView goldImageView = (ImageView) findViewById(R.id.goldImageView);
        final Intent goldIntent = new Intent(this, GoldCalcActivity.class);

        goldImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goldIntent);
            }
        });

        ImageView sharesImageView = (ImageView) findViewById(R.id.stocksImageView);
        final Intent shareIntent = new Intent(this, SharesCalcActivity.class);

        sharesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(shareIntent);
            }
        });
    }
}
