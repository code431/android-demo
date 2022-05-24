package com.example.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Scene;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Student student = new Student("Jack",20);
        //Student[] students = {student1,student2};
        //String jsonStudents = "[{\"age\":20,\"name\":\"jack\",\"score\":{\"Chinese\":96,\"English\":95,\"Math\":90}},{\"age\":19,\"name\":\"lisa\",\"score\":{\"Chinese\":97,\"English\":93,\"Math\":94}}]";
        Gson gson = new Gson();
        Student student = new Student("jack",20,new Score(90,95,96));
        String jsonStudent = gson.toJson(student);
    }
}
