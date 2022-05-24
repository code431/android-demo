package com.example.myexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void btn_submit_login(View view) {
        EditText editText_id = (EditText)findViewById(R.id.editText_login_id);
        EditText editText_password = (EditText)findViewById(R.id.editText_login_password);
        Intent intent = new Intent();
        intent.putExtra("login_id",editText_id.getText().toString());
        this.setResult(0,intent);
        this.finish();
    }
}
