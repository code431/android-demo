package com.example.kotlindemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel:ViewModel(){
    //MutableLiveData应该是对内的，需要给一个对外的LiveData，带下划线的变量一般表示内部的
    private val _number:MutableLiveData<Int> by lazy { MutableLiveData<Int>().also { it.value = 0 } }//第二种lazy，当调用number在初始化
    //创建一个对外的开放的
    val number:LiveData<Int>
    get() = _number
    //第一种，呼叫完构造函数立即初始化
//    init {
//        number = MutableLiveData()
//        number.value = 0
//    }

    fun modifyNumber(aNumber:Int){
        //直接加可能为空
        //number.value = number.value + aNumber
        //需要先判断不为空在加
        _number.value = _number.value?.plus(aNumber)
        //强制执行，不考虑空
        //number.value = number.value!! + aNumber
    }
}