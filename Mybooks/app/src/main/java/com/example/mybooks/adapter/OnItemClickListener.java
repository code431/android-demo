package com.example.mybooks.adapter;


import android.view.View;

//RecyclerView的单击监听接口
public interface OnItemClickListener {
    //点击监听
    public void onItemClick(View view,int postion);
}
