package com.example.mybooks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybooks.R;
import com.example.mybooks.entity.Person;

import java.util.ArrayList;
import java.util.List;

/*
*recyclerview的自定义适配器
*/
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
    //通讯录数据
    private List<Person> pdata = new ArrayList<Person>();
    //上下文
    private Context context;

    //定义监听
    private OnItemClickListener mOnItemClickListener;



    //构造方法
    public MyRecyclerAdapter(List<Person> pdata, Context context) {
        this.pdata = pdata;
        this.context = context;
    }

    @NonNull
    @Override
    //返回一个自定义的ViewHolder
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //填充布局，获取列表项布局文件
        View itemView = LayoutInflater.from(context).inflate(R.layout.bookitem,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    //填充onCreateViewHolder返回的holder中的控件
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        //获取通讯录数据
        Person person = pdata.get(position);
        holder.myimg.setImageResource(person.getImg());
        holder.myname.setText(person.getName());
        //设置点击事件
        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    //返回通讯录数据个数
    @Override
    public int getItemCount() {
        return pdata.size();
    }

    //定义内部类ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        //定义对应的列表项
        private ImageView myimg;
        private TextView myname;


        //构造方法
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //获取对应的列表项,传入的itemView即bookitem
            myimg = itemView.findViewById(R.id.item_img);
            myname = itemView.findViewById(R.id.item_name);
        }
    }
}
