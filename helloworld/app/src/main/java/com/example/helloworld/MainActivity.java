package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.editText);
            }
        });
        Log.d(TAG, "onCreate: is called");
        Button a = new Button(this);//ctrl+alt+space重新显示代码提示,ctrl+p提示函数的参数
        if (a == null) {//ctrl+j 快捷代码 代码缩写提示

        }
        try {
            TextView textView = new TextView(this); //ctrl+alt+t 为选择代码添加if、for、try/catch等语句
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        a1();
    }
    private void a1() {

    }
}

