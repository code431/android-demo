package com.example.button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*方法一：自带的setOnClickListener实例化后定义函数*/
        Button btnbmicalc = (Button)findViewById(R.id.button);
        btnbmicalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editheight = (EditText) findViewById(R.id.editheight);
                EditText editweight = (EditText) findViewById(R.id.editweight);
                TextView textresult = (TextView) findViewById(R.id.textresult);
                Double height = Double.parseDouble(editheight.getText().toString());
                Double weight = Double.parseDouble(editweight.getText().toString());
                /*计算判断*/
                Double BMI = weight / (height * height);
                if (BMI < 18.5) {
                    textresult.setText("BMI:" + BMI.toString() + "，体型：过轻");
                } else if (BMI <= 23.9) {
                    textresult.setText("BMI:" + BMI.toString() + "，体型：正常");
                } else if (BMI <= 27) {
                    textresult.setText("BMI:" + BMI.toString() + "，体型：过重");
                } else if (BMI <= 32) {
                    textresult.setText("BMI:" + BMI.toString() + "，体型：肥胖");
                } else {
                    textresult.setText("BMI:" + BMI.toString() + "，体型：非常肥胖");
                }
            }
        });
    }
/*方法二：button的onClick属性绑定定义的函数*/
//    public void BtnBmiCalc_Clicked(View v){
//        EditText editheight = (EditText)findViewById(R.id.editheight);
//        EditText editweight = (EditText)findViewById(R.id.editweight);
//        TextView textresult = (TextView)findViewById(R.id.textresult);
//        Double height = Double.parseDouble(editheight.getText().toString());
//        Double weight = Double.parseDouble(editweight.getText().toString());
//        /*计算判断*/
//        Double BMI = weight / (height * height);
//        if (BMI<18.5){
//            textresult.setText("BMI:" + BMI.toString() + "，体型：过轻");
//        }
//        else if (BMI<=23.9){
//            textresult.setText("BMI:" + BMI.toString() + "，体型：正常");
//        }
//        else if (BMI<=27){
//            textresult.setText("BMI:" + BMI.toString() + "，体型：过重");
//        }
//        else if (BMI<=32){
//            textresult.setText("BMI:" + BMI.toString() + "，体型：肥胖");
//        }
//        else{
//            textresult.setText("BMI:" + BMI.toString() + "，体型：非常肥胖");
//        }
//
//    }
}
