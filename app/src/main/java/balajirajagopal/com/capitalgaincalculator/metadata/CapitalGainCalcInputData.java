package balajirajagopal.com.capitalgaincalculator.metadata;

import android.os.Parcel;
import android.os.Parcelable;

import balajirajagopal.com.capitalgaincalculator.enums.ASSETTYPE;
import balajirajagopal.com.capitalgaincalculator.enums.ASSET_SUBTYPE;

public class CapitalGainCalcInputData implements Parcelable {

    private String saleDate;
    private String purchaseDate;
    private String saleAmount;
    private String purchaseAmount;
    private String saleExpense;
    private String purchaseExpense;
    private String fairMarketPrice;
    private String numberOfUnits;
    private String headerMessage;
    private ASSETTYPE assettype;
    private ASSET_SUBTYPE assetSubtype;

    public CapitalGainCalcInputData(Parcel in) {
        saleDate = in.readString();
        purchaseDate = in.readString();
        saleAmount = in.readString();
        purchaseAmount = in.readString();
        saleExpense = in.readString();
        purchaseExpense = in.readString();
        fairMarketPrice = in.readString();
        numberOfUnits = in.readString();
        headerMessage = in.readString();
        assettype = ASSETTYPE.valueOf(in.readString());
        assetSubtype = ASSET_SUBTYPE.valueOf(in.readString());
    }

    public CapitalGainCalcInputData(){

    }

    public static final Creator<CapitalGainCalcInputData> CREATOR = new Creator<CapitalGainCalcInputData>() {
        @Override
        public CapitalGainCalcInputData createFromParcel(Parcel in) {
            return new CapitalGainCalcInputData(in);
        }

        @Override
        public CapitalGainCalcInputData[] newArray(int size) {
            return new CapitalGainCalcInputData[size];
        }
    };

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getSaleExpense() {
        return saleExpense;
    }

    public void setSaleExpense(String saleExpense) {
        this.saleExpense = saleExpense;
    }

    public String getPurchaseExpense() {
        return purchaseExpense;
    }

    public void setPurchaseExpense(String purchaseExpense) {
        this.purchaseExpense = purchaseExpense;
    }

    public String getFairMarketPrice() {
        return fairMarketPrice;
    }

    public void setFairMarketPrice(String fairMarketPrice) {
        this.fairMarketPrice = fairMarketPrice;
    }

    public String getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(String numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public String getHeaderMessage() {
        return headerMessage;
    }

    public void setHeaderMessage(String headerMessage) {
        this.headerMessage = headerMessage;
    }

    public ASSETTYPE getAssettype() {
        return assettype;
    }

    public void setAssettype(ASSETTYPE assettype) {
        this.assettype = assettype;
    }

    public ASSET_SUBTYPE getAssetSubtype() {
        return assetSubtype;
    }

    public void setAssetSubtype(ASSET_SUBTYPE assetSubtype) {
        this.assetSubtype = assetSubtype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(saleDate);
        dest.writeString(purchaseDate);
        dest.writeString(saleAmount);
        dest.writeString(purchaseAmount);
        dest.writeString(saleExpense);
        dest.writeString(purchaseExpense);
        dest.writeString(fairMarketPrice);
        dest.writeString(numberOfUnits);
        dest.writeString(headerMessage);
        dest.writeString(assettype.name());
        dest.writeString(assetSubtype.name());
    }
}
