package com.example.pagergalleryloadmorepart2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import kotlin.math.ceil

const val DATA_STATUS_CAN_LOAD_MORE = 0
const val DATA_STATUS_NO_MORE = 1
const val DATA_STATUS_NETWORK_ERROR = 2

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    //_dataStatusLive用来判断是否全部加载完毕
    private val _dataStatusLive = MutableLiveData<Int>()
    val dataStatusLive: LiveData<Int> get() = _dataStatusLive
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()

    //对外开发一个不可变得LiveData封装
    val photoListLive: LiveData<List<PhotoItem>>
        get() = _photoListLive

    //第一次加载完后滚到到最上面
    var needToScrollToTop = true
    private val perPage = 50
    private val keyWords =
        listOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal")
    private var currentPage = 1
    private var totalPage = 1
    private var currentKey = "cat"
    private var isNewQuery = true
    //防止在一次调用完成之前就再次调用
    private var isLoading = false

    init {
        resetQuery()
    }

    fun resetQuery() {
        currentPage = 1
        totalPage = 1
        currentKey = keyWords.random()
        isNewQuery = true
        needToScrollToTop = true
        fetchData()
    }

    fun fetchData() {
        //如果正在加载就return
        if (isLoading) return
        //即全部加载完毕没有更多了
        if (currentPage > totalPage) {
            _dataStatusLive.value = 1
            return
        }
        isLoading = true
        val stringRequest = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                with(Gson().fromJson(it, Pixabay::class.java)) {
                    //Int无法整除会将小数省略，ceil会将小数+1
                    totalPage = ceil(totalHits.toDouble() / perPage).toInt()
                    //是否是新页面
                    if (isNewQuery) {
                        _photoListLive.value = hits.toList()
                    } else {
                        //追加到原来的_photoListLive中
                        _photoListLive.value =
                            arrayListOf(_photoListLive.value!!, hits.toList()).flatten()
                    }
                }
                _dataStatusLive.value = 0
                isLoading = false
                isNewQuery = false
                currentPage++
            },
            Response.ErrorListener {
                _dataStatusLive.value = 2
                isLoading = false
            }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)
    }


    private fun getUrl(): String {
        return "https://pixabay.com/api/?key=25915374-97fbc1d497918b0fe1140f7bb&q=${currentKey}&per_page=${perPage}&page=${currentPage}"
    }

}