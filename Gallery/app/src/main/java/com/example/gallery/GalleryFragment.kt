package com.example.gallery


import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
                Handler().postDelayed({galleryViewModel.fetchData()},1000)
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
        val galleryAdapt = GalleryAdapt()
        recyclerView.apply {
            adapter = galleryAdapt
            //两列
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        galleryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GalleryViewModel::class.java)
        galleryViewModel.photoListLive.observe(this, Observer {
            //当数据发生变化时，调用adapt的Diff修改数据
            galleryAdapt.submitList(it)
            //此时已经修改完毕，刷新图标取消
            swipeRefreshLayout.isRefreshing = false
        })
        //viewModel为空就填充数据
        galleryViewModel.photoListLive.value ?: galleryViewModel.fetchData()

        swipeRefreshLayout.setOnRefreshListener { galleryViewModel.fetchData() }
    }
}
