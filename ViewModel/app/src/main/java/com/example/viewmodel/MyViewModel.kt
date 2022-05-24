package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.*

//ViewModel、AndroidViewModel都可以
//class MyViewModel(private val savedStateHandle: SavedStateHandle) :ViewModel(){
class MyViewModel(application: Application,private val savedStateHandle: SavedStateHandle) : AndroidViewModel(
    application
){
    private val _number = MutableLiveData<Int>() .also {
        if (!savedStateHandle.contains("number")){
            savedStateHandle.set("number",0)
        }
        it.value = savedStateHandle.get("number")
    }

    val number:LiveData<Int> get() = _number

    fun addOne() {
        getApplication<Application>()
        _number.value = _number.value?.plus(1)
        savedStateHandle.set("number",_number.value)
    }
}