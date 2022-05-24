package com.example.getbackdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
    }

    public void btn_back(View view) {
        //步骤二
        EditText editText = (EditText)findViewById(R.id.editText);
        Intent intent = new Intent();
        intent.putExtra("result",editText.getText().toString());
        this.setResult(0,intent);
        this.finish();
    }
}
