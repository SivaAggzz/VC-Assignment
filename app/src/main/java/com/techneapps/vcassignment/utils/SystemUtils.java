package com.techneapps.vcassignment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class SystemUtils {
    public static boolean isInternetConnected(Context context){
        ConnectivityManager ConnectionManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        if (ConnectionManager != null) {
            networkInfo = ConnectionManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }else {
            return false;
        }
    }
}
