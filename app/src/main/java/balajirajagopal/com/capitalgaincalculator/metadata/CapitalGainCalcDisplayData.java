package balajirajagopal.com.capitalgaincalculator.metadata;

import android.os.Parcel;
import android.os.Parcelable;

import balajirajagopal.com.capitalgaincalculator.enums.GAINTYPE;

public class CapitalGainCalcDisplayData implements Parcelable {

    private Double totalSaleAmount;
    private Double totalPurchaseAmount;
    private String capitalGainAmountTextView;
    private Double capitalGainAmount;
    private String capitalGainAmountDisplayValue;
    private String capitalGainTaxPercentDisplayValue;
    private Double capitalGainTaxAmount;
    private String getCapitalGainTaxAmountDisplayValue;
    private Double netProfit;
    private String netProfitDisplayValue;
    private String gainTypeDisplayValue;
    private GAINTYPE gaintype;

    public CapitalGainCalcDisplayData(Parcel in) {
        if (in.readByte() == 0) {
            totalSaleAmount = null;
        } else {
            totalSaleAmount = in.readDouble();
        }
        if (in.readByte() == 0) {
            totalPurchaseAmount = null;
        } else {
            totalPurchaseAmount = in.readDouble();
        }
        capitalGainAmountTextView = in.readString();
        if (in.readByte() == 0) {
            capitalGainAmount = null;
        } else {
            capitalGainAmount = in.readDouble();
        }
        capitalGainAmountDisplayValue = in.readString();
        capitalGainTaxPercentDisplayValue = in.readString();
        if (in.readByte() == 0) {
            capitalGainTaxAmount = null;
        } else {
            capitalGainTaxAmount = in.readDouble();
        }
        getCapitalGainTaxAmountDisplayValue = in.readString();
        if (in.readByte() == 0) {
            netProfit = null;
        } else {
            netProfit = in.readDouble();
        }
        netProfitDisplayValue = in.readString();
        gainTypeDisplayValue = in.readString();
        gaintype = GAINTYPE.valueOf(in.readString());
    }

    public static final Creator<CapitalGainCalcDisplayData> CREATOR = new Creator<CapitalGainCalcDisplayData>() {
        @Override
        public CapitalGainCalcDisplayData createFromParcel(Parcel in) {
            return new CapitalGainCalcDisplayData(in);
        }

        @Override
        public CapitalGainCalcDisplayData[] newArray(int size) {
            return new CapitalGainCalcDisplayData[size];
        }
    };

    public CapitalGainCalcDisplayData() {

    }

    public Double getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(Double totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }

    public Double getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public void setTotalPurchaseAmount(Double totalPurchaseAmount) {
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    public String getCapitalGainAmountTextView() {
        return capitalGainAmountTextView;
    }

    public void setCapitalGainAmountTextView(String capitalGainAmountTextView) {
        this.capitalGainAmountTextView = capitalGainAmountTextView;
    }

    public Double getCapitalGainAmount() {
        return capitalGainAmount;
    }

    public void setCapitalGainAmount(Double capitalGainAmount) {
        this.capitalGainAmount = capitalGainAmount;
    }

    public String getCapitalGainAmountDisplayValue() {
        return capitalGainAmountDisplayValue;
    }

    public void setCapitalGainAmountDisplayValue(String capitalGainAmountDisplayValue) {
        this.capitalGainAmountDisplayValue = capitalGainAmountDisplayValue;
    }

    public String getCapitalGainTaxPercentDisplayValue() {
        return capitalGainTaxPercentDisplayValue;
    }

    public void setCapitalGainTaxPercentDisplayValue(String capitalGainTaxPercentDisplayValue) {
        this.capitalGainTaxPercentDisplayValue = capitalGainTaxPercentDisplayValue;
    }

    public Double getCapitalGainTaxAmount() {
        return capitalGainTaxAmount;
    }

    public void setCapitalGainTaxAmount(Double capitalGainTaxAmount) {
        this.capitalGainTaxAmount = capitalGainTaxAmount;
    }

    public String getGetCapitalGainTaxAmountDisplayValue() {
        return getCapitalGainTaxAmountDisplayValue;
    }

    public void setGetCapitalGainTaxAmountDisplayValue(String getCapitalGainTaxAmountDisplayValue) {
        this.getCapitalGainTaxAmountDisplayValue = getCapitalGainTaxAmountDisplayValue;
    }

    public Double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }

    public String getNetProfitDisplayValue() {
        return netProfitDisplayValue;
    }

    public void setNetProfitDisplayValue(String netProfitDisplayValue) {
        this.netProfitDisplayValue = netProfitDisplayValue;
    }

    public String getGainTypeDisplayValue() {
        return gainTypeDisplayValue;
    }

    public void setGainTypeDisplayValue(String gainTypeDisplayValue) {
        this.gainTypeDisplayValue = gainTypeDisplayValue;
    }

    public GAINTYPE getGaintype() {
        return gaintype;
    }

    public void setGaintype(GAINTYPE gaintype) {
        this.gaintype = gaintype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (totalSaleAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalSaleAmount);
        }
        if (totalPurchaseAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totalPurchaseAmount);
        }
        dest.writeString(capitalGainAmountTextView);
        if (capitalGainAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(capitalGainAmount);
        }
        dest.writeString(capitalGainAmountDisplayValue);
        dest.writeString(capitalGainTaxPercentDisplayValue);
        if (capitalGainTaxAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(capitalGainTaxAmount);
        }
        dest.writeString(getCapitalGainTaxAmountDisplayValue);
        if (netProfit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(netProfit);
        }
        dest.writeString(netProfitDisplayValue);
        dest.writeString(gainTypeDisplayValue);
        dest.writeString(gaintype.name());
    }
}
