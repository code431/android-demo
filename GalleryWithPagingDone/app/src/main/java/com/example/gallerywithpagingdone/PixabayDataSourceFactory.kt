package com.example.gallerywithpagingdone

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

//有了工厂类和DataSource就可以在ViewModel中创建PagedList
//工厂类
//实例化时将context进一步传递给PixabayDataSource
class PixabayDataSourceFactory(private val context: Context):DataSource.Factory<Int,PhotoItem>(){
    //此处必须为MutableLiveData形式，否则会为空，因为create执行有延迟性，在ViewModel赋值后才执行，
    //不能直接为PixabayDataSource类型，需要LiveData包裹
    private val _pixabayDataSource = MutableLiveData<PixabayDataSource>()
    val pixabayDataSource:LiveData<PixabayDataSource> = _pixabayDataSource
    //需要返回一个DataSource类型对象
    override fun create(): DataSource<Int, PhotoItem> {
        return PixabayDataSource(context).also { _pixabayDataSource.postValue(it) }
    }
}