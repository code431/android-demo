package com.example.transferdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //方法一的接收数据
//        Intent intent = getIntent();
//        String data = intent.getStringExtra("extra_data");
//        Toast toast = Toast.makeText(SecondActivity.this, data,Toast.LENGTH_LONG);
//        toast.show();
        //方法二
        Bundle bundle = getIntent().getExtras();
        String string = bundle.getString("name");
        int age = bundle.getInt("age");
        Toast.makeText(SecondActivity.this, string+age, Toast.LENGTH_LONG).show();
    }
}
