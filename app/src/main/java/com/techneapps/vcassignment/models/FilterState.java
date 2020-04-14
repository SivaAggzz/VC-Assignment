package com.techneapps.vcassignment.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FilterState implements Parcelable {
    private String filterValue;
    private boolean filterON;

    public FilterState(String filterValue, boolean filterON) {
        this.filterValue = filterValue;
        this.filterON = filterON;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public boolean isFilterON() {
        return filterON;
    }

    public void setFilterON(boolean filterON) {
        this.filterON = filterON;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filterValue);
        dest.writeByte(this.filterON ? (byte) 1 : (byte) 0);
    }

    private FilterState(Parcel in) {
        this.filterValue = in.readString();
        this.filterON = in.readByte() != 0;
    }

    public static final Parcelable.Creator<FilterState> CREATOR = new Parcelable.Creator<FilterState>() {
        @Override
        public FilterState createFromParcel(Parcel source) {
            return new FilterState(source);
        }

        @Override
        public FilterState[] newArray(int size) {
            return new FilterState[size];
        }
    };
}
