package com.example.myviewpagerfragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Fragment的适配器
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    //定义属性：Fragment列表
    private List<Fragment> fragmentList;
    //构造方法
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    //显示那个页面
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    //页面个数
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
