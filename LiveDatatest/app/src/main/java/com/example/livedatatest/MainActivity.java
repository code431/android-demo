package com.example.livedatatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ViewModelWithLiveData viewModelWithLiveData;
    TextView textView;
    ImageButton imageButtonLike,imageButtonDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        imageButtonLike = findViewById(R.id.imageButton);
        imageButtonDislike = findViewById(R.id.imageButton2);
        viewModelWithLiveData = ViewModelProviders.of(this).get(ViewModelWithLiveData.class);
        viewModelWithLiveData.getLinkedNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textView.setText(String.valueOf(integer));
            }
        });
        imageButtonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModelWithLiveData.addLikedNumber(1);
            }
        });
        imageButtonDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModelWithLiveData.addLikedNumber(-1);
            }
        });
    }
}
