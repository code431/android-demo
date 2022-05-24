package com.example.transferdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void transferdata(View view) {
        //Intent传数据方法一，传一组数据
//        String data = "hi,secondActivity";
//        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//        intent.putExtra("extra_data",data);
//        startActivity(intent);
        //Intent传数据方法二，传多组数据
        Bundle bundle = new Bundle();
        bundle.putString("name", "张三");
        bundle.putInt("age", 20);
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
