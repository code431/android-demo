package com.example.lifecycles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    MyChronometer chronometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.meter);
        getLifecycle().addObserver(chronometer);
        //chronometer.setBase(SystemClock.elapsedRealtime());  //手机从上一次启动到现在经历的毫秒数
        //chronometer.setBase(System.currentTimeMillis());  //UNIX时间    1970.1.1到现在为止经过的毫秒数
    }
}
