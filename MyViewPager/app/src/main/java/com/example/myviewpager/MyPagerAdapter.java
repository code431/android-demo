package com.example.myviewpager;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager的数据，提供视图数据
 */
public class MyPagerAdapter extends PagerAdapter {

    //视图列表
    private List<View> viewlist = new ArrayList<View>();

    //构造方法

    public MyPagerAdapter(){
    }

    public MyPagerAdapter(List<View> viewlist) {
        this.viewlist = viewlist;
    }

    //重写父类继承的方法
    //初始化指定位置的页面
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //将页面加入到容器中
        container.addView(viewlist.get(position));
        return viewlist.get(position);
    }

    //销毁指定位置的页面
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //删除指定位置的视图
        container.removeView(viewlist.get(position));
    }

    //显示多少个页面
    @Override
    public int getCount() {
        return viewlist.size();
    }

    //判断返回的View是否是Object,返回布尔型
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
