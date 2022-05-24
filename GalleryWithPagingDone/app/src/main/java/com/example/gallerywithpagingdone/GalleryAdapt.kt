package com.example.gallerywithpagingdone

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.gallery_cell.view.*
import kotlinx.android.synthetic.main.gallery_footer.view.*

//使用Page时Adapt需要继承PagedListAdapter
class GalleryAdapt(private val galleryViewModel: GalleryViewModel): PagedListAdapter<PhotoItem,RecyclerView.ViewHolder>(DIFFCALLBACK) {
    private var netWorkStatus : NetWorkStatus? = null
    private var hasFooter = false
    init {
        //每次进入recycleView如果retry不为空就执行刷新，即点击图片后退出
        galleryViewModel.retry()
    }
    fun updateNetWorkStatus(netWorkStatus: NetWorkStatus?){
        this.netWorkStatus = netWorkStatus
        if (netWorkStatus == NetWorkStatus.INITIAL_LOADING) hideFooter() else showFooter()
    }

    private fun hideFooter(){
        if (hasFooter){
            notifyItemRemoved(itemCount - 1)
        }
        hasFooter = false
    }

    private fun showFooter(){
        if (hasFooter){
            notifyItemChanged(itemCount - 1)
        }else{
            hasFooter = true
            notifyItemInserted(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasFooter && position == itemCount - 1) R.layout.gallery_footer else R.layout.gallery_footer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            R.layout.gallery_cell -> PhotoViewHolder.newInstance(parent).also { holder->
                holder.itemView.setOnClickListener {
                    Bundle().apply {
                        //将整个ArrayList传递过去，currentList是当前在用的List
                        //此处警告currentList可能为空，但如果能点击currentList就不可能为空
                        //putParcelableArrayList("PHOTO_LIST", ArrayList(currentList!!)) //如果数据过多这种方不适合，直接全使用ViewModel提供的数据
                        putInt("PHOTO_POSITION",holder.adapterPosition)
                        holder.itemView.findNavController().navigate(R.id.action_galleryFragment_to_pagerPhotoFragment,this)
                    }
                }
            }
            else -> FooterViewHolder.newInstance(parent).also {
                it.itemView.setOnClickListener {
                    galleryViewModel.retry()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            R.layout.gallery_footer->(holder as FooterViewHolder).bindWithNetworkStatus(netWorkStatus)
            else -> {
                //换为PagedListAdapter后getItem有可能为空，使用要进行判空处理
                val photoItem = getItem(position) ?: return
                (holder as PhotoViewHolder).bindWithPhotoItem(photoItem)
            }
        }
    }

    object DIFFCALLBACK:DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoId == newItem.photoId
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }
    }

}
//进行封装，将有关ViewHolder的写到这个类中
class PhotoViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    companion object{
        fun newInstance(parent: ViewGroup):PhotoViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_cell,parent,false)
            return PhotoViewHolder(view)
        }
    }
    fun bindWithPhotoItem(photoItem: PhotoItem){
        with(itemView){
            textViewUser.text = photoItem.photoUser
            textViewLikes.text = photoItem.photoLikes.toString()
            textViewCollections.text = photoItem.photoCollections.toString()
            imageView.layoutParams.height = photoItem!!.photoHeight
        }
        Glide.with(itemView)
            //此处getItem也有可能为空，需要判空处理
            .load(photoItem?.previewUrl)
            .placeholder(R.drawable.photo_placeholder)
            .listener(object :RequestListener<Drawable>{
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
                    return false.also { itemView.shimmerLayoutCell?.stopShimmerAnimation() }
                }

            })
            .into(itemView.imageView)
    }
}
class FooterViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    companion object{
        fun newInstance(parent: ViewGroup):PhotoViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_footer,parent,false)
            (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
            return PhotoViewHolder(view)
        }
    }
    fun bindWithNetworkStatus(networkStatus: NetWorkStatus?){
        with(itemView){
            when(networkStatus){
                NetWorkStatus.FAILED -> {
                    textView.text = "点击重试"
                    progressBar.visibility = View.GONE
                    isClickable = true
                }
                NetWorkStatus.COMPLETED -> {
                    textView.text = "加载完毕"
                    progressBar.visibility = View.GONE
                    isClickable = false
                }
                else -> {
                    textView.text = "正在加载"
                    progressBar.visibility = View.VISIBLE
                    isClickable = false
                }
            }
        }
    }
}
