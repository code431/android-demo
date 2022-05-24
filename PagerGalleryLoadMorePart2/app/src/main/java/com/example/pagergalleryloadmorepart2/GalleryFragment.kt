package com.example.pagergalleryloadmorepart2


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : Fragment() {
    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.swipeIndicator -> {
                //点击后刷新图标转动
                swipeRefreshLayout.isRefreshing = true
                //延迟1s后进行，让刷新图标出现，否则网速太快刷新图标一闪而过
                Handler().postDelayed({galleryViewModel.resetQuery()},1000)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        galleryViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(GalleryViewModel::class.java)
        val galleryAdapt = GalleryAdapt(galleryViewModel)
        recyclerView.apply {
            adapter = galleryAdapt
            //spanCount两列
            //StaggeredGridLayoutManager交错布局
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        galleryViewModel.photoListLive.observe(this, Observer {
            if (galleryViewModel.needToScrollToTop){
                recyclerView.scrollToPosition(0)
                galleryViewModel.needToScrollToTop = false
            }
            //当数据发生变化时，调用adapt的Diff修改数据
            galleryAdapt.submitList(it)
            //此时已经修改完毕，刷新图标取消
            swipeRefreshLayout.isRefreshing = false
        })
        //viewModel为空就填充数据
        //galleryViewModel.photoListLive.value ?: galleryViewModel.resetQuery()

        galleryViewModel.dataStatusLive.observe(this, Observer {
            //将数据加载情况传递给galleryAdapt
            galleryAdapt.footerViewStatus = it
            //当dataStatusLive通知galleryAdapt在哪个地方需要刷新
            galleryAdapt.notifyItemChanged(galleryAdapt.itemCount - 1)
            //如果网络错误停止转动动画
            if (it == DATA_STATUS_NETWORK_ERROR) swipeRefreshLayout.isRefreshing = false
        })
        swipeRefreshLayout.setOnRefreshListener {
            galleryViewModel.resetQuery()
        }

        //滑动监听
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                //只要滑动就有变化，dx水平变化，dx竖直变化
                //dy为正为向下滚动，dy为负为向上滚动
                super.onScrolled(recyclerView, dx, dy)
                Log.d("hello","onScrolled $dy")
                if (dy < 0) return
                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                val intArray = IntArray(2)
                //将最后两个元素放到intArray
                layoutManager.findLastVisibleItemPositions(intArray)
                //当intArray里的元素为最后一个时刷新数据
                if ( intArray[0] == galleryAdapt.itemCount - 1){
                    galleryViewModel.fetchData()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                //开始滚动newState为1，滑动结束newState为0，快速滑动一下为2
                super.onScrollStateChanged(recyclerView, newState)
                Log.d("hello","onScrollStateChanged $newState")
            }
        })
    }
}
