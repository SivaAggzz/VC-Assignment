package com.techneapps.vcassignment.utils;

import java.util.ArrayList;

public class FilterHelper {
    public static ArrayList<String> getFilterOptions() {
        ArrayList<String> filterOptions=new ArrayList<>();
        filterOptions.add("Trending");
        filterOptions.add("Headline");
        return filterOptions;
    }
}
