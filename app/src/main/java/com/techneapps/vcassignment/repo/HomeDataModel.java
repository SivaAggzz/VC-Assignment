package com.techneapps.vcassignment.repo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techneapps.vcassignment.models.ResponseItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeDataModel {
    private ArrayList<ResponseItem> responseItems;

    public HomeDataModel() {
    }


    public ArrayList<ResponseItem> getDataFromServer() {
        ArrayList<ResponseItem> arrayList = new ArrayList<>();
        HttpURLConnection urlConnection = null;

        try {
            String API_ADDRESS = "https://us-central1-webpush-151905.cloudfunctions.net/getInterviewData";
            URL url = new URL(API_ADDRESS);
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new IOException("Invalid response from server: " + code);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
               // Log.e("data", line);
                JSONObject jsonObject = new JSONObject(line);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
               // Log.e("jsonArray", jsonArray.toString());


                Gson gson = new Gson();
                Type userListType = new TypeToken<ArrayList<ResponseItem>>() {
                }.getType();
                arrayList = gson.fromJson(jsonArray.toString(), userListType);
                //Log.e("arrayList size", arrayList.size()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return arrayList;
    }
}
