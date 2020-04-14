package com.techneapps.vcassignment.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techneapps.vcassignment.models.ResponseItem;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter(value = "imageName")
    public static void setItemImageName(TextView textView, String imageURL) {
        textView.setText(imageURL.substring(imageURL.lastIndexOf("/") + 1, imageURL.indexOf(".png")));
    }

    @androidx.databinding.BindingAdapter(value = "imageURL")
    public static void setItemImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext()).load(imageURL).into(imageView);
    }

    @androidx.databinding.BindingAdapter(value = "itemExpiry")
    public static void setItemExpiry(TextView textView, ResponseItem responseItem) {
        if (responseItem.getExpiryDate() == null) {
            textView.setVisibility(View.INVISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
            int countDays=responseItem.getCalculatedRemainingDays();
            if (countDays == 1) {
                textView.setText("Ending in " + countDays + " day");
            } else {
                textView.setText("Ending in " + countDays + " days");
            }
        }
    }

}
