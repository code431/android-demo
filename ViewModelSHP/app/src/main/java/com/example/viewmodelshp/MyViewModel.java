package com.example.viewmodelshp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

@SuppressWarnings("ConstantConditions")
public class MyViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    private String KEY = getApplication().getResources().getString(R.string.DATA_KEY);
    private String SHPNAME = getApplication().getResources().getString(R.string.SHP_NAME);

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;
        if (!handle.contains(KEY)) {
            load();
        }
    }

    public LiveData<Integer> getNumber() {
        return handle.getLiveData(KEY);
    }

    private void load() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHPNAME, Context.MODE_PRIVATE);
        int x = sharedPreferences.getInt(KEY, 0);
        handle.set(KEY, x);
    }

    void save() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, getNumber().getValue());
        editor.apply();
    }

    public void add(int x) {
        handle.set(KEY, getNumber().getValue() + x);
        //save();
    }
}
