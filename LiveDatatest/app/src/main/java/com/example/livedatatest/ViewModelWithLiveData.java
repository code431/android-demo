package com.example.livedatatest;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelWithLiveData extends ViewModel {
    private MutableLiveData<Integer> LinkedNumber;

    public MutableLiveData<Integer> getLinkedNumber() {
        if (LinkedNumber == null) {
            LinkedNumber = new MutableLiveData<>();
            LinkedNumber.setValue(0);
        }
        return LinkedNumber;
    }
    public void addLikedNumber(int n){
        LinkedNumber.setValue(LinkedNumber.getValue() + n);
    }
}
