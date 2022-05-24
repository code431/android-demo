package com.example.myviewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //定义相关属性
    private ViewPager myViewPager;
    private View view1,view2,view3; //三个视图，用于接受三个启动页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取ViewPager对象
        myViewPager = (ViewPager)findViewById(R.id.myViewPager);
        //获取三个启动页面，将布局和变量联系起来
        //LayoutInflater将布局文件.xml转换成View对象
        LayoutInflater layoutInflater = getLayoutInflater();
        view1 = layoutInflater.inflate(R.layout.layout_1,null,false);
        view2 = layoutInflater.inflate(R.layout.layout_2,null,false);
        view3 = layoutInflater.inflate(R.layout.layout_3,null,false);

        //创建视图列表
        List<View> viewlist = new ArrayList<View>();
        viewlist.add(view1);
        viewlist.add(view2);
        viewlist.add(view3);

        //创建适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewlist);
        //设置适配器
        myViewPager.setAdapter(myPagerAdapter);





    }
}
