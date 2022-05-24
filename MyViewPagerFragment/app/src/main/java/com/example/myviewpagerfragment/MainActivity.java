package com.example.myviewpagerfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //定义属性
    private TextView tab_1,tab_2,tab_3;//底部标签
    private ViewPager myViewPager;//切换区
    private List<Fragment> fragmentList;//Fragment列表
    private MyFragmentPagerAdapter fragmentPagerAdapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        initUI();
        initTab();
    }

    /**
     * 初始化UI
     */
    private void initUI(){
        //初始化底部标签
        tab_1 = (TextView)findViewById(R.id.tv_tab1);
        tab_2 = (TextView)findViewById(R.id.tv_tab2);
        tab_3 = (TextView)findViewById(R.id.tv_tab3);
        //为底部标签添加点击事件
        tab_1.setOnClickListener(this);
        tab_2.setOnClickListener(this);
        tab_3.setOnClickListener(this);
        //初始化切换区
        myViewPager = (ViewPager)findViewById(R.id.myviewpager);
    }

    /**
     * 初始化
     */
    private void initTab(){
        //新建Fragment
        Fragment_1 fragment_1 = new Fragment_1();
        Fragment_2 fragment_2 = new Fragment_2();
        Fragment_3 fragment_3 = new Fragment_3();
        //创建列表
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(fragment_1);
        fragmentList.add(fragment_2);
        fragmentList.add(fragment_3);

        //新建适配器
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        //设置适配器
        myViewPager.setAdapter(fragmentPagerAdapter);
        //设置滑动监听
        myViewPager.addOnPageChangeListener(new MyPageChangeListener());

        //显示第一个页面
        showFragment(0);
    }

    /**
     * 显示Fragment
     */
    private void showFragment(int num){
        //按索引显示Fragment
        myViewPager.setCurrentItem(num);
        //改变底部标签
        if (num==0){
            tab_1.setBackgroundColor(Color.RED);
            tab_2.setBackgroundColor(Color.WHITE);
            tab_3.setBackgroundColor(Color.WHITE);
        }
        else if (num==1){
            tab_1.setBackgroundColor(Color.WHITE);
            tab_2.setBackgroundColor(Color.RED);
            tab_3.setBackgroundColor(Color.WHITE);
        }
        else if (num==2){
            tab_1.setBackgroundColor(Color.WHITE);
            tab_2.setBackgroundColor(Color.WHITE);
            tab_3.setBackgroundColor(Color.RED);
        }
    }

    /**
     * 底部标签点击事件
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_tab1){
            //第一个标签被点击
            showFragment(0);
        }
        else if (view.getId() == R.id.tv_tab2){
            //第二个标签被点击
            showFragment(1);
        }
        else if (view.getId() == R.id.tv_tab3){
            //第三个标签被点击
            showFragment(2);
        }
    }

    /**
     *定义页面滑动的监听类，用于页面滑动时，底部导航跟着变化
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //页面选中时调用
        @Override
        public void onPageSelected(int position) {
            //改变底部标签
            if (position==0){
                tab_1.setBackgroundColor(Color.RED);
                tab_2.setBackgroundColor(Color.WHITE);
                tab_3.setBackgroundColor(Color.WHITE);
            }
            else if (position==1){
                tab_1.setBackgroundColor(Color.WHITE);
                tab_2.setBackgroundColor(Color.RED);
                tab_3.setBackgroundColor(Color.WHITE);
            }
            else if (position==2){
                tab_1.setBackgroundColor(Color.WHITE);
                tab_2.setBackgroundColor(Color.WHITE);
                tab_3.setBackgroundColor(Color.RED);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
