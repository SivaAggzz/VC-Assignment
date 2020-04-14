package com.techneapps.vcassignment.models;


import com.techneapps.vcassignment.utils.DateTimeUtils;

public class ResponseItem {
    private String expiryDate;
    private String img;
    private boolean isTrending;
    private String itemHeadline;
    private String itemText;

    private int calculatedRemainingDays;

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setIsTrending(boolean isTrending) {
        this.isTrending = isTrending;
    }

    public boolean isIsTrending() {
        return isTrending;
    }

    public void setItemHeadline(String itemHeadline) {
        this.itemHeadline = itemHeadline;
    }

    public String getItemHeadline() {
        return itemHeadline;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getItemText() {
        return itemText;
    }

    public int getCalculatedRemainingDays() {
        return calculatedRemainingDays;
    }

    private void setCalculatedRemainingDays(int calculatedRemainingDays) {
        this.calculatedRemainingDays = calculatedRemainingDays;
    }

    public void calculateRemainingDays() {
    	if (getExpiryDate()==null){
			setCalculatedRemainingDays(999999);
		}else {
			int countDays = DateTimeUtils.calculateRemainingDays(getExpiryDate());
			setCalculatedRemainingDays(countDays);
		}
    }

    @androidx.annotation.NonNull
    @Override
    public String toString() {
        return
                "Response{" +
                        "expiryDate = '" + expiryDate + '\'' +
                        ",img = '" + img + '\'' +
                        ",isTrending = '" + isTrending + '\'' +
                        ",itemHeadline = '" + itemHeadline + '\'' +
                        ",itemText = '" + itemText + '\'' +
                        "}";
    }
}
