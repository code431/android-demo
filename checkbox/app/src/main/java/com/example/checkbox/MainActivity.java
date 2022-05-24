package com.example.checkbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.checkbox.R;

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
                CheckBox checkBoxWHO = setcheckBoxWHO();
                CheckBox checkBoxAsia = setcheckBoxAsia();
                CheckBox checkBoxChina = setcheckBoxChina();
                String strresult = "";
                if (checkBoxWHO.isChecked()) {
                    strresult += "WHO标准下\n";
                    if (BMI < 18.5) {
                        strresult += "BMI:" + BMI.toString() + "，体型：过轻\n";
                    } else if (BMI <= 24.9) {
                        strresult += "BMI:" + BMI.toString() + "，体型：正常\n";
                    } else if (BMI <= 29.9) {
                        strresult += "BMI:" + BMI.toString() + "，体型：过重\n";
                    } else if (BMI <= 34.9) {
                        strresult += "BMI:" + BMI.toString() + "，体型：肥胖\n";
                    } else {
                        strresult += "BMI:" + BMI.toString() + "，体型：非常肥胖\n";
                    }
                }
                if (checkBoxAsia.isChecked()) {
                    strresult += "亚洲标准下\n";
                    if (BMI < 18.5) {
                        strresult += "BMI:" + BMI.toString() + "，体型：过轻\n";
                    } else if (BMI <= 22.9) {
                        strresult += "BMI:" + BMI.toString() + "，体型：正常\n";
                    } else if (BMI <= 24.9) {
                        strresult += "BMI:" + BMI.toString() + "，体型：过重\n";
                    } else if (BMI <= 29.9) {
                        strresult += "BMI:" + BMI.toString() + "，体型：肥胖\n";
                    } else {
                        strresult += "BMI:" + BMI.toString() + "，体型：非常肥胖\n";
                    }
                }
                if (checkBoxChina.isChecked()){
                    strresult += "中国标准下\n";
                    if (BMI < 18.5) {
                        strresult += "BMI:" + BMI.toString() + "，体型：过轻\n";
                    } else if (BMI <= 23.9) {
                        strresult += "BMI:" + BMI.toString() + "，体型：正常\n";
                    } else if (BMI <= 27.9) {
                        strresult += "BMI:" + BMI.toString() + "，体型：过重\n";
                    } else {
                        strresult += "BMI:" + BMI.toString() + "，体型：肥胖\n";
                    }
                }
                textresult.setText(strresult);
            }
            public CheckBox setcheckBoxWHO(){
                CheckBox checkBoxWHO = (CheckBox) findViewById(R.id.checkBoxWHO);
                return checkBoxWHO;
            }
            public CheckBox setcheckBoxAsia(){
                CheckBox checkBoxAsia = (CheckBox) findViewById(R.id.checkBoxAsia);
                return checkBoxAsia;
            }
            public CheckBox setcheckBoxChina(){
                CheckBox checkBoxChina = (CheckBox) findViewById(R.id.checkBoxChina);
                return checkBoxChina;
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
