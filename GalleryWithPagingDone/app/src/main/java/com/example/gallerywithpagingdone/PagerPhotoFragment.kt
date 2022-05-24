package com.example.gallerywithpagingdone


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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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
    //viewModels只在fragment里生效，将viewModels扩充为activityViewModels，将在整个activity生效
    private val galleryViewModel by activityViewModels<GalleryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager_photo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = PagerPhotoListAdapter()
        viewPager2.adapter = adapter
        galleryViewModel.pagedListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            //第二个参数是是否有从第一个滚动到点击的页面的动画
            viewPager2.setCurrentItem(arguments?.getInt("PHOTO_POSITION") ?: 0, false)
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                photoTag.text = getString(R.string.photo_tag, position + 1, galleryViewModel.pagedListLiveData.value?.size)
            }
        })

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

    //suspend表示可以挂起，在另一个线程运行
    private suspend fun savePhoto() {
        //suspend携程中可以使用delay来模拟耗时操作，检查副线程是否生效，即主线程无卡顿
        //delay(5000)
        //withContext的作用就是挂起后执行完毕自动切换回主线程，范围是耗时的IO操作线程
        withContext(Dispatchers.IO) {//本质上ViewPager2里封装了一个RecycleView，要找到RecycleView，
            //需要通过viewPager2[0]或viewPager2.get(0)返回ViewGroup的第一个元素然后转为RecyclerView
            //findViewHolderForAdapterPosition在RecyclerView找到对应位置的ViewHolder，
            //再转为PagerPhotoViewHolder
            val holder =
                (viewPager2[0] as RecyclerView).findViewHolderForAdapterPosition(viewPager2.currentItem)
                        as PagerPhotoViewHolder
            //获取到位图
            val bitmap = holder.itemView.pagerPhoto.drawable.toBitmap()
            //MediaStore管理共享区域,他返回一个字符串，null代表失败，API29及以上弃用insertImage
//        if(MediaStore.Images.Media.insertImage(requireContext().contentResolver,bitmap,"","") == null){
//            Toast.makeText(requireContext(),"存储失败",Toast.LENGTH_SHORT).show()
//        }else{
//            Toast.makeText(requireContext(),"存储成功",Toast.LENGTH_SHORT).show()
//        }
            //API29及以上方法
            //insertImage其实是将一些操作封装了，现在需要手动来做
            //1、创建保存路径，先占一个位置，再通过有个流将图片写入
            val saveUri = requireContext().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()//ContentValues对文件具体的描述，如标题可留空
            ) ?: kotlin.run {
                //Toast属于UI操作，要放到主线程
                MainScope().launch {
                    Toast.makeText(requireContext(), "存储失败", Toast.LENGTH_SHORT).show()
                }
                //此时不知道返回哪一个，需要指明返回到withContext
                return@withContext          //saveUri可能为空，需要判空操作
            }
            //2、写入，openOutputStream为打开一个输出流，use可以在用完之后自动将流关闭
            requireContext().contentResolver.openOutputStream(saveUri).use {
                //JPEG可压缩PNG是无损，90表示压缩率为90，他返回一个boolean值
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
