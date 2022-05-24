package com.example.parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.parcelable.databinding.ActivityMain2Binding;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain2Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main2);
        Intent intent = getIntent();//获取Intent
        Bundle bundle = intent.getBundleExtra("data");//从Intent中获取bundle
        Student student = bundle.getParcelable("student");//从bundle中获取student
        binding.textViewName.setText(String.valueOf(student.getName()));
        binding.textViewAge.setText(String.valueOf(student.getAge()));
        binding.textViewMath.setText(String.valueOf(student.getScore().getMath()));
        binding.textViewEnglish.setText(String.valueOf(student.getScore().getEnglish()));
    }
}
