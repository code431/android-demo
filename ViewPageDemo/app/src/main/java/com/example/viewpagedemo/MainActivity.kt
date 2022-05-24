package com.example.viewpagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //viewPager2需要适配器
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            //告诉viewPager有几个页面
            override fun getItemCount() = 3
            //哪一个位置要创建哪一个页面给它
            override fun createFragment(position: Int) =
                when (position) {
                    0 -> ScaleFragment()
                    1 -> RotateFragment()
                    else -> TranslateFragment()
                }
        }
        //关联TabLayout和ViewPager
        //参数1:TabLayout,参数2:ViewPager2,参数3:配置策略，如何配置，即position和标题的关系
        TabLayoutMediator(tabLayout,viewPager2){tab, position ->
            when(position){
                0 -> tab.text = "缩放"
                1 -> tab.text = "旋转"
                else -> tab.text = "移动"
            }
        }.attach()
    }
}
