package com.example.viewmodelrestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.viewmodelrestore.databinding.ActivityMainBinding;

import java.security.Provider;

public class MainActivity extends AppCompatActivity {

    MyViewModel myViewModel;
    ActivityMainBinding binding;
    public final static String KEY_NUMBER = "my_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        myViewModel = new ViewModelProvider(this,new SavedStateViewModelFactory(getApplication(),this)).get(MyViewModel.class);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(this);
    }
}
