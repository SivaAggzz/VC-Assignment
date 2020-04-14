package com.techneapps.vcassignment.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {


    public static int calculateRemainingDays(String unformattedDate) {
        Date originalDate;
        SimpleDateFormat originalDateFormatter;
        try {
            if (unformattedDate.indexOf("-") < 4) {
                originalDateFormatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss", Locale.getDefault());
            } else {
                originalDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            }

            originalDate = originalDateFormatter.parse(unformattedDate);
            long timeInMilliseconds = Objects.requireNonNull(originalDate).getTime();

            long msDiff = timeInMilliseconds - Calendar.getInstance().getTimeInMillis();
            long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
            return ((int) daysDiff);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}
