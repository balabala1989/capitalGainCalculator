package balajirajagopal.com.capitalgaincalculator.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import balajirajagopal.com.capitalgaincalculator.R;
import balajirajagopal.com.capitalgaincalculator.common.ReportGen;
import balajirajagopal.com.capitalgaincalculator.enums.GAINTYPE;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcDisplayData;
import balajirajagopal.com.capitalgaincalculator.metadata.CapitalGainCalcInputData;
import balajirajagopal.com.capitalgaincalculator.utils.CapitalGainCalculatorUtils;

public class GoldCalcReportActivity extends AppCompatActivity {

    private static Intent intent;
    private GAINTYPE gaintype;
    private static CapitalGainCalculatorUtils utils;
    private CapitalGainCalcInputData capitalGainCalcInputData;
    private CapitalGainCalcDisplayData capitalGainCalcDisplayData;
    private ReportGen reportGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_calc_report);

        utils = new CapitalGainCalculatorUtils(true);

        intent = getIntent();

        capitalGainCalcInputData = (CapitalGainCalcInputData) intent.getParcelableExtra(GoldCalcActivity.GOLD_INPUT_DATA);
        capitalGainCalcDisplayData = new CapitalGainCalcDisplayData();
        reportGen = new ReportGen(this,capitalGainCalcInputData, capitalGainCalcDisplayData);
        reportGen.generateReport();
    }

    public CapitalGainCalcDisplayData calculateGainDetails(CapitalGainCalcInputData capitalGainCalcInputData, CapitalGainCalcDisplayData capitalGainCalcDisplayData){

        double taxPercent;

        utils.calculateDifferenceInPurchaseAndSaleDate(capitalGainCalcInputData.getPurchaseDate(),capitalGainCalcInputData.getSaleDate());
        //Short term computation
        if(utils.yearDiff < Integer.parseInt(getString(R.string.gold_shortterm_year))){
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
            Double indexedPurchaseAmount = capitalGainCalcDisplayData.getTotalPurchaseAmount() * ((double)utils.getIndexValue(saleDate,this) / (double)utils.getIndexValue(purchaseDate, this));
            capitalGainCalcDisplayData.setCapitalGainAmount(capitalGainCalcDisplayData.getTotalSaleAmount() - indexedPurchaseAmount);
            capitalGainCalcDisplayData.setGaintype(capitalGainCalcDisplayData.getCapitalGainAmount() < 0 ? GAINTYPE.LONG_TERM_LOSS : GAINTYPE.LONG_TERM_GAIN);
            if(GAINTYPE.LONG_TERM_GAIN.equals(capitalGainCalcDisplayData.getGaintype())){
                taxPercent = (double)Double.parseDouble(getString(R.string.gold_long_term_percent))/(double)100;
                capitalGainCalcDisplayData.setCapitalGainTaxAmount(capitalGainCalcDisplayData.getCapitalGainAmount() * taxPercent);
                capitalGainCalcDisplayData.setCapitalGainAmountTextView(getString(R.string.capitalgain_gain_amount_textview));
                capitalGainCalcDisplayData.setGainTypeDisplayValue(getString(R.string.capitalgain_long_term_gain_textview));
                capitalGainCalcDisplayData.setNetProfit(capitalGainCalcDisplayData.getCapitalGainAmount() - capitalGainCalcDisplayData.getCapitalGainTaxAmount());
                capitalGainCalcDisplayData.setCapitalGainTaxPercentDisplayValue(getString(R.string.gold_long_term_percent) + "%");
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
