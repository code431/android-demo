package com.example.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class MyData {
    public int number;
    private Context context;

    public MyData(Context context) {
        this.context = context;
    }

    public void save() {
        String name = context.getResources().getString(R.string.My_Data);
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = context.getResources().getString(R.string.Key);
        editor.putInt(key,number);
        editor.apply();
    }

    public int load() {
        String name = context.getResources().getString(R.string.My_Data);
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        String key = context.getResources().getString(R.string.Key);
        int defValue = context.getResources().getInteger(R.integer.defValue);
        int x = sharedPreferences.getInt(key,defValue);
        return x;
    }
}
