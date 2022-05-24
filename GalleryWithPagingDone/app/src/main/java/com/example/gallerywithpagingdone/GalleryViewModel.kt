package com.example.gallerywithpagingdone

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData


class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val factory = PixabayDataSourceFactory(application)
    //这里PageSize是每个页面的大小，但此处没用，因为已在PixabayDataSource的url中指定每一页的大小
    val pagedListLiveData = factory.toLiveData(30)
    //将pixabayDataSource转变为networkStatus并且会自动观察pixabayDataSource
    val networkStatus = Transformations.switchMap(factory.pixabayDataSource) {it.networkStatus}
    //下拉刷新
    fun resetQuery(){
        //invalidate意为无效，告知数据已经无效了，工厂会重新生成DataSource就产生了新的url
        pagedListLiveData.value?.dataSource?.invalidate()
    }
    fun retry(){
        factory.pixabayDataSource.value?.retry?.invoke()
    }
}