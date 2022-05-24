package com.example.logn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonlogin = (Button)findViewById(R.id.buttonlogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButtonusername = (RadioButton) findViewById(R.id.radioButtonusername);
                RadioButton radioButtonemail = (RadioButton) findViewById(R.id.radioButtonemail);
                String Dbuser, Dbpassword;
                if (radioButtonusername.isChecked()) {
                    Dbuser = "zhangsan";
                    Dbpassword = "123456";
                } else {
                    Dbuser = "3195218997@qq.com";
                    Dbpassword = "123456";
                }
                EditText textusername = (EditText) findViewById(R.id.textusername);
                EditText textpassword = (EditText) findViewById(R.id.textpassword);
                //TextView textresult = (TextView)findViewById(R.id.textresult);
                if (textusername.getText().toString().equals(Dbuser)) {
                    if (textpassword.getText().toString().equals(Dbpassword)) {
                        //textresult.setText("登录成功");
                        Toastshow("登录成功");
                    } else {
                        //textresult.setText("密码错误");
                        Toastshow("密码错误");
                    }
                } else {
                    //textresult.setText("用户名不存在");
                    Toastshow("用户名不存在");
                }
            }
        });
        final Button buttonreg = (Button)findViewById(R.id.buttonreg);
        buttonreg.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(MainActivity.this, new CustomDialog.OnCustomDialogListener() {
                    @Override
                    public void btnConfirmLicenseClicked(Boolean isConfirm) {
                        if (isConfirm){
                            Toastshow("感谢您成为我们的新用户");
                        }
                        else{
                            Toastshow("只有接受用户协议，才能注册新用户。");
                        }
                    }
                });
                customDialog.show();
                /*AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("用户协议确认");
                alertDialog.setIcon(R.drawable.ic_launcher_background);
                alertDialog.setMessage("注册新用户需接受用户协议的约束，请您认真查阅用户协议内容，并选择是非同意接受用户协议内容。");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        View popupwindow_content = LayoutInflater.from(MainActivity.this).inflate(R.layout.popupwindow_content,null,false);
                        PopupWindow popupWindow = new PopupWindow(popupwindow_content, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                        popupWindow.setTouchable(true);
                        //popupWindow.showAsDropDown(buttonreg, 0, 0, Gravity.BOTTOM);
                        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "不同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toastshow("只有接受用户协议，才能注册新用户。");
                    }
                });
                alertDialog.show();
                 */
            }
        });
    }
            void Toastshow(String cont){
                Toast toast = Toast.makeText(MainActivity.this, cont, Toast.LENGTH_LONG);
                //自定义布局信息提示
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.toast_layout,null);
                TextView textViewToast = (TextView)view.findViewById(R.id.textViewToast);
                textViewToast.setText(cont);
                toast.setView(view);
                toast.setGravity(Gravity.CENTER, 0, 0);
                /*带图片信息提示
                LinearLayout layout = (LinearLayout)toast.getView();
                ImageView img = new ImageView(getApplicationContext());
                img.setImageResource(R.drawable.toast);
                layout.addView(img, 0);
                 */
                toast.show();
            }

    }
