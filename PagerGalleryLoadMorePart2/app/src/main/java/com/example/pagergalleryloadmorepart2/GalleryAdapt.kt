package com.example.pagergalleryloadmorepart2

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.gallery_cell.view.*
import kotlinx.android.synthetic.main.gallery_footer.view.*

class GalleryAdapt(val galleryViewModel: GalleryViewModel) : ListAdapter<PhotoItem, MyViewHolder>(DIFFCALLBACK) {
    companion object {
        const val NORMAL_VIEW_TYPE = 0
        const val FOOTER_VIEW_TYPE = 1
    }

    var footerViewStatus = DATA_STATUS_CAN_LOAD_MORE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val holder: MyViewHolder
        //没到最后一行就显示图片，到最后一行就显示foot
        if (viewType == NORMAL_VIEW_TYPE) {
            holder = MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.gallery_cell, parent, false)
            )
            holder.itemView.setOnClickListener {
                Bundle().apply {
                    //将整个ArrayList传递过去，currentList是当前在用的List
                    putParcelableArrayList("PHOTO_LIST", ArrayList(currentList))
                    putInt("PHOTO_POSITION", holder.adapterPosition)
                    holder.itemView.findNavController()
                        .navigate(R.id.action_galleryFragment_to_pagerPhotoFragment, this)
                }
            }
        } else {
            //3、创建ViewHolder
            holder = MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.gallery_footer, parent, false)
                    .also {
                        //因为StaggeredGridLayoutManager显示两列，foot会在左边一列的中间，需要改为让他占据整一列的空间
                        (it.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
                        it.setOnClickListener {itemView ->
                            itemView.progressBar.visibility = View.VISIBLE
                            itemView.textView.text = "正在加载"
                            galleryViewModel.fetchData()
                        }
                    }
            )
        }
        return holder
    }

    //当一个RecycleView要请求视图时，有以下几步：1、请求视图总数 2、view的类型 3、创建ViewHolder 4、绑定到对应位置
    //1、在原视图总数+1，即footer视图
    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    //2、view的类型
    override fun getItemViewType(position: Int): Int {//这里返回的ViewType作为onCreateViewHolder的参数viewType
        return if (position == itemCount - 1) FOOTER_VIEW_TYPE else NORMAL_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //4、绑定到对应位置
        //如果是最后一个就不绑定数据跳过
        if (position == itemCount - 1) {
            with(holder.itemView){
                when(footerViewStatus){
                    DATA_STATUS_CAN_LOAD_MORE -> {
                        progressBar.visibility = View.VISIBLE
                        textView.text = "正在加载"
                        isClickable = false
                    }
                    DATA_STATUS_NO_MORE -> {
                        progressBar.visibility = View.GONE
                        textView.text = "全部加载完毕"
                        isClickable = false
                    }
                    DATA_STATUS_NETWORK_ERROR -> {
                        progressBar.visibility = View.GONE
                        textView.text = "网络错误，点击重试"
                        isClickable = true
                    }
                }
            }
            return
        }
        val photoItem = getItem(position)
        with(holder.itemView) {
            textViewUser.text = photoItem.photoUser
            textViewLikes.text = photoItem.photoLikes.toString()
            textViewCollections.text = photoItem.photoCollections.toString()
            shimmerLayoutCell.apply {
                setShimmerColor(0X55FFFFFF)
                setShimmerAngle(0)
                startShimmerAnimation()
            }
            imageView.layoutParams.height = getItem(position).photoHeight
        }
        Glide.with(holder.itemView)
            .load(getItem(position).previewUrl)
            .placeholder(R.drawable.photo_placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //？判空，如果在图片加载完成之前切换页面，依然会呼叫，导致空指针错误
                    return false.also { holder.itemView.shimmerLayoutCell?.stopShimmerAnimation() }
                }
            })
            .into(holder.itemView.imageView)
    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoId == newItem.photoId
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)