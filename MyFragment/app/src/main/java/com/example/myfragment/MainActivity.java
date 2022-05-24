package com.example.myfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //定义Fragment对象
    private Fragment fragment_1,fragment_2,fragment_3,nowFragment;
    //定义底部标签
    private TextView tab_1,tab_2,tab_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    /**
     *初始化UI界面
     */
    private void initUI(){

        //初始化底部标签
        tab_1 = (TextView)findViewById(R.id.tv_tab1);
        tab_2 = (TextView)findViewById(R.id.tv_tab2);
        tab_3 = (TextView)findViewById(R.id.tv_tab3);

        //设置底部tab的变化，默认第一个被选中
        tab_1.setBackgroundColor(Color.RED);
        tab_2.setBackgroundColor(Color.WHITE);
        tab_3.setBackgroundColor(Color.WHITE);

        //为底部标签设置点击事件
        tab_1.setOnClickListener(this);
        tab_2.setOnClickListener(this);
        tab_3.setOnClickListener(this);

        //显示第一个Fragment
        showFragment1();
    }

    /**
     * 第一个标签被点击
     */
    private void showFragment1(){
        //开启事务，Fragment的切换是由事务控制的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //判断Fragment是否为空
        if (fragment_1==null){
            fragment_1 = new Fragment_1();
            //添加Fragment到事务中
            transaction.add(R.id.content_layout,fragment_1);
        }
        //隐藏所有的Fragment
        hideAllFragment(transaction);

        //显示Fragment
        transaction.show(fragment_1);
        //记录Fragment
        nowFragment = fragment_1;
        //提交事务
        transaction.commit();
        //设置底部标签的变化
        tab_1.setBackgroundColor(Color.RED);
        tab_2.setBackgroundColor(Color.WHITE);
        tab_3.setBackgroundColor(Color.WHITE);
    }

    /**
     * 第二个标签被点击
     */
    private void showFragment2(){
        //开启事务，Fragment的切换是由事务控制的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //判断Fragment是否为空
        if (fragment_2==null){
            fragment_2 = new Fragment_2();
            //添加Fragment到事务中
            transaction.add(R.id.content_layout,fragment_2);
        }
        //隐藏所有的Fragment
        hideAllFragment(transaction);

        //显示Fragment
        transaction.show(fragment_2);
        //记录Fragment
        nowFragment = fragment_2;
        //提交事务
        transaction.commit();
        //设置底部标签的变化
        tab_1.setBackgroundColor(Color.WHITE);
        tab_2.setBackgroundColor(Color.RED);
        tab_3.setBackgroundColor(Color.WHITE);
    }

    /**
     * 第三个标签被点击
     */
    private void showFragment3(){
        //开启事务，Fragment的切换是由事务控制的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //判断Fragment是否为空
        if (fragment_3==null){
            fragment_3 = new Fragment_3();
            //添加Fragment到事务中
            transaction.add(R.id.content_layout,fragment_3);
        }
        //隐藏所有的Fragment
        hideAllFragment(transaction);

        //显示Fragment
        transaction.show(fragment_3);
        //记录Fragment
        nowFragment = fragment_3;
        //提交事务
        transaction.commit();
        //设置底部标签的变化
        tab_1.setBackgroundColor(Color.WHITE);
        tab_2.setBackgroundColor(Color.WHITE);
        tab_3.setBackgroundColor(Color.RED);
    }

    /**
     * 隐藏所有的Fragment
     */
    private void hideAllFragment(FragmentTransaction transaction){
        if (fragment_1 != null){
            transaction.hide(fragment_1);
        }
        if (fragment_2 != null){
            transaction.hide(fragment_2);
        }
        if (fragment_3 != null){
            transaction.hide(fragment_3);
        }
    }

    //点击事件
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tv_tab1){
            //第一个标签被点击
            showFragment1();
        }
        else if (view.getId() == R.id.tv_tab2){
            //第二个标签被点击
            showFragment2();
        }
        else if (view.getId() == R.id.tv_tab3){
            //第三个标签被点击
            showFragment3();
        }
    }
}
