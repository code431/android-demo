package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyData myData = new MyData(getApplicationContext()); //不能传递this，会导致内存泄漏
        myData.number = 1000;
        myData.save();
        int x = myData.load();
        String TAG = "mylog";
        Log.d(TAG,"onCreate" + x);
//        SharedPreferences sharedPreferences = getSharedPreferences("MY DATA",Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit= sharedPreferences.edit();
//        edit.putInt("NUMBER",100);
//        edit.apply();
//        int x = sharedPreferences.getInt("NUMBER",0);
//        String TAG = "mylog";
//        Log.d(TAG,"onCreate" + x);

    }
}
