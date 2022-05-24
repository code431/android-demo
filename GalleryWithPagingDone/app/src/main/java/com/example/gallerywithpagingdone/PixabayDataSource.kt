package com.example.gallerywithpagingdone

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

//PageKeyedDataSource和DataSource.Factory都是抽象类需要实现其中的方法

//枚举类
enum class NetWorkStatus {
    //第一次加载
    INITIAL_LOADING,
    LOADING,
    LOADED,
    FAILED,
    COMPLETED
}

//需要一个工厂类将PixabayDataSource实例化
class PixabayDataSource(private val context: Context) : PageKeyedDataSource<Int, PhotoItem>() {
    //当网络故障后重试时需要保留现场，即保留回调函数
    var retry : (()->Any)? = null
    //当你想换一个请求时是通过一个函数来通知工厂重新生成一个DataSource，就可以得到一个新的queryKey来请求
    private val queryKey = listOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal").random()

    private val _networkStatus = MutableLiveData<NetWorkStatus>()
    val networkStatus: LiveData<NetWorkStatus> = _networkStatus

    //加载第一个页面
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoItem>
    ) {//请求时清空retry，避免误操作
        retry = null
        //postValue比Value安全
        _networkStatus.postValue(NetWorkStatus.INITIAL_LOADING)
        //第一页page为1，per_page即每页30个
        val url = "https://pixabay.com/api/?key=25915374-97fbc1d497918b0fe1140f7bb&q=${queryKey}&per_page=30&page=1"
        StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                val dataList = Gson().fromJson(it, Pixabay::class.java).hits.toList()
                //previousPageKey是上一个页面，因为是第一页所以为null，nextPageKey是下一页为2
                callback.onResult(dataList, null, 2)
                _networkStatus.postValue(NetWorkStatus.LOADED)
            },
            Response.ErrorListener {
                //失败后，保留现场
                retry = { loadInitial(params,callback) }
                _networkStatus.postValue(NetWorkStatus.FAILED)
                Log.d("hello", "loadInitial:$it")
            }
            //这里getInstance需要context参数，通过构造函数传递，在创建工厂时带过来
        ).also { VolleySingleton.getInstance(context).requestQueue.add(it) }
    }

    //当数据不够时，加载下一页
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {
        retry = null
        _networkStatus.postValue(NetWorkStatus.LOADING)
        //params.key即为当前页面序号
        val url = "https://pixabay.com/api/?key=25915374-97fbc1d497918b0fe1140f7bb&q=${queryKey}&per_page=30&page=${params.key}"
        StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {

                val dataList = Gson().fromJson(it, Pixabay::class.java).hits.toList()
                //adjacentPageKet即下一页序号
                callback.onResult(dataList, params.key + 1)
                _networkStatus.postValue(NetWorkStatus.LOADED)
            },
            Response.ErrorListener {
                //数据加载完毕
                if (it.toString() == "com.android.volley.ClientError"){
                    _networkStatus.postValue(NetWorkStatus.COMPLETED)
                }else{
                    retry = { loadAfter(params,callback) }
                    _networkStatus.postValue(NetWorkStatus.FAILED)
                    Log.d("hello", "loadAfter:$it")
                }

            }
        ).also { VolleySingleton.getInstance(context).requestQueue.add(it) }
    }

    //往前加载，此处不需要
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {
    }

}