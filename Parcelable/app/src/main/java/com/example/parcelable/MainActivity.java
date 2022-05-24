package com.example.parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parcelable.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();
                int age = Integer.valueOf(binding.editTextAge.getText().toString());
                int math = Integer.valueOf(binding.editTextMath.getText().toString());
                int english = Integer.valueOf(binding.editTextEnglish.getText().toString());
                Student student = new Student(name,age,new Score(math,english));
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);//创建Intent
                Bundle bundle = new Bundle();//创建Bundle
                bundle.putParcelable("student",student);//将student通过Parcelable序列化后传入bundle
                intent.putExtra("data",bundle);//将bundle放到intent中
                startActivity(intent);
            }
        });
    }
}
