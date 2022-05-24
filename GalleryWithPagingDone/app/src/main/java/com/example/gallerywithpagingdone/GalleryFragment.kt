package com.example.gallerywithpagingdone


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : Fragment() {
    //使用新方法创建ViewModel
    //private val galleryViewModel by viewModels<GalleryViewModel>()
    //viewModels只在fragment里生效，将viewModels扩充为activityViewModels，将在整个activity生效
    private val galleryViewModel by activityViewModels<GalleryViewModel>()

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
//            R.id.menuRetry -> {
//                galleryViewModel.retry()
//            }
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
        val galleryAdapt = GalleryAdapt(galleryViewModel)
        recyclerView.apply {
            adapter = galleryAdapt
            //两列
            //交错布局
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        //galleryViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(GalleryViewModel::class.java)
        //此处参数Owner为viewLifecycleOwner一个跟随view生命周期的Owner
        galleryViewModel.pagedListLiveData.observe(viewLifecycleOwner, Observer {
            galleryAdapt.submitList(it)
        })
        swipeRefreshLayout.setOnRefreshListener {
            galleryViewModel.resetQuery()
        }
        galleryViewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            Log.d("hello","onActivityCreated:$it")
            galleryAdapt.updateNetWorkStatus(it)
            //如果是第一次加载刷新图标就出现
            swipeRefreshLayout.isRefreshing = it == NetWorkStatus.LOADING
        })
    }
}
