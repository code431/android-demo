package com.example.myexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView text_status;
    private TextView text_id;
    private TextView text_email;
    private static final int REQUEST_LOGIN_CODE=1;
    private static final int REQUEST_REG_CODE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_status = (TextView)findViewById(R.id.text_status);
        text_id = (TextView)findViewById(R.id.text_id);
        text_email = (TextView)findViewById(R.id.text_email);

    }

    public void btn_start_login(View view) {
        Intent intent = new Intent(this, loginActivity.class);
        startActivityForResult(intent,REQUEST_LOGIN_CODE);
    }

    public void btn_start_reg(View view) {
        Intent intent = new Intent(this, regActivity.class);
        startActivityForResult(intent,REQUEST_REG_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent==null){
            return;
        }
        switch (requestCode){
            case REQUEST_REG_CODE:
                text_status.setText("登录成功");
                text_id.setText("您好："+intent.getStringExtra("reg_id"));
                text_email.setText("Email："+intent.getStringExtra("reg_email"));
                break;
            case REQUEST_LOGIN_CODE:
                text_status.setText("登录成功");
                text_id.setText("您好："+intent.getStringExtra("login_id"));
                break;
        }
    }
}
