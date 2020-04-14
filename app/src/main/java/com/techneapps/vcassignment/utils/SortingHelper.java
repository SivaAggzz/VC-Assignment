package com.techneapps.vcassignment.utils;

import com.techneapps.vcassignment.models.ResponseItem;

import java.util.ArrayList;
import java.util.Collections;

public class SortingHelper {
    public static void sortItemsByExpiryDateAscending(ArrayList<ResponseItem> responseItems) {

        Collections.sort(responseItems, (responseItem1, responseItem2) -> {
            if (responseItem1.getCalculatedRemainingDays() == 999999) {
                return (responseItem2.getCalculatedRemainingDays() == 999999) ? 0 : 1;
            }
            if (responseItem2.getCalculatedRemainingDays() == 999999) {
                return -1;
            }

            return Long.compare(responseItem1.getCalculatedRemainingDays(), responseItem2.getCalculatedRemainingDays());
        });




    }

    public static void sortItemsByExpiryDateDescending(ArrayList<ResponseItem> responseItems) {
        Collections.sort(responseItems, (responseItem1, responseItem2) -> {
            if (responseItem1.getCalculatedRemainingDays() == 999999) {
                return (responseItem2.getCalculatedRemainingDays() == 999999) ? 0 : 1;
            }
            if (responseItem2.getCalculatedRemainingDays() == 999999) {
                return -1;
            }
            return Long.compare(responseItem2.getCalculatedRemainingDays(), responseItem1.getCalculatedRemainingDays());
        });


    }
}
