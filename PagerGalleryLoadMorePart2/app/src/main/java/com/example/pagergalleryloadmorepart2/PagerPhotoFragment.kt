package com.example.pagergalleryloadmorepart2


import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_pager_photo.*
import kotlinx.android.synthetic.main.pager_photo_view.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 */
const val REQUEST_WRITE_EXTERNAL_STORAGE = 1

class PagerPhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager_photo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val photoList = arguments?.getParcelableArrayList<PhotoItem>("PHOTO_LIST")
        PagerPhotoListAdapter().apply {
            viewPager2.adapter = this
            submitList(photoList)
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                photoTag.text = getString(R.string.photo_tag, position + 1, photoList?.size)
            }
        })
        //第二个参数是是否有从第一个滚动到点击的页面的动画
        viewPager2.setCurrentItem(arguments?.getInt("PHOTO_POSITION") ?: 0, false)
        //可以实现ViewPager2的竖直滚动
        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        saveImageButton.setOnClickListener {
            //共享区间写入权限在API 29(Q)之前是需要申请的，
            //API 29之后不需要申请，默认可以写入，但读取需要请求权限，即请求外部存储
            //if(如果API小于29且权限没有打开(Manifest是括号为android的))
            if (Build.VERSION.SDK_INT < 29 && ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {//GRANTED：允许
                //requestPermissions有两个参数，第一个为Array，允许同时请求多个权限，只请求一个也需要放到Array里
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL_STORAGE
                )
            }//这样就可以弹出一个对话框
            else {
                viewLifecycleOwner.lifecycleScope.launch {
                    savePhoto()
                }
            }
        }
    }

    //对用户点击对话框选项进行处理
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            //因为requestPermissions传入的requestCode参数为REQUEST_WRITE_EXTERNAL_STORAGE
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                //grantResults不为空且因为只请求了一个权限所以只看grantResults[0]是否为PERMISSION_GRANTED(允许)即可
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //不能直接呼叫，需要放到携程处理
                    //让副线程在lifecycleScope范围里执行，好处是它跟随fragment的生命周期，
                    //如果生命周期被摧毁了，副线程也自动终止
                    viewLifecycleOwner.lifecycleScope.launch {
                        savePhoto()
                    }//权限通过，存储
                } else {
                    Toast.makeText(requireContext(), "权限未允许，存储失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //关于savePhoto详细注释请见PagerGallerySave
    //suspend表示可以挂起，在另一个线程运行
    private suspend fun savePhoto() {
        withContext(Dispatchers.IO) {//本质上ViewPager2里封装了一个RecycleView，要找到RecycleView，
            val holder =
                (viewPager2[0] as RecyclerView).findViewHolderForAdapterPosition(viewPager2.currentItem)
                        as PagerPhotoViewHolder
            val bitmap = holder.itemView.pagerPhoto.drawable.toBitmap()
            val saveUri = requireContext().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()//ContentValues对文件具体的描述，如标题可留空
            ) ?: kotlin.run {
                MainScope().launch {
                    Toast.makeText(requireContext(), "存储失败", Toast.LENGTH_SHORT).show()
                }
                return@withContext          //saveUri可能为空，需要判空操作
            }
            requireContext().contentResolver.openOutputStream(saveUri).use {
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)) {
                    MainScope().launch {
                        Toast.makeText(
                            requireContext(),
                            "存储成功",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    MainScope().launch {
                        Toast.makeText(
                            requireContext(),
                            "存储失败",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
