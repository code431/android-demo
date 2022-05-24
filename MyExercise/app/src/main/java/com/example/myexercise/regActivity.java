package com.example.myexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class regActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }

    public void btn_submit_reg(View view) {
        EditText editText_email = (EditText)findViewById(R.id.editText_email);
        EditText editText_id = (EditText)findViewById(R.id.editText_id);
        EditText editText_password = (EditText)findViewById(R.id.editText_password);
        Intent intent = new Intent();
        intent.putExtra("reg_id",editText_id.getText().toString());
        intent.putExtra("reg_email",editText_id.getText().toString());
        this.setResult(0,intent);
        this.finish();
    }
}
