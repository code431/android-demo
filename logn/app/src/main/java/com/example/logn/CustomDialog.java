package com.example.logn;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context, OnCustomDialogListener customDialogListener) {
        super(context);
        this.customDialogListener = customDialogListener;
    }

    public interface OnCustomDialogListener{
        public void btnConfirmLicenseClicked(Boolean isConfirm);
    }
    private OnCustomDialogListener customDialogListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(lp);


        Button btnconfirmlicense = (Button)findViewById(R.id.btnconfirmlicense);
        btnconfirmlicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cbconfirmlicense = (CheckBox)findViewById(R.id.cbconfirmlicense);
                customDialogListener.btnConfirmLicenseClicked(cbconfirmlicense.isChecked());
            }
        });
    }
}
