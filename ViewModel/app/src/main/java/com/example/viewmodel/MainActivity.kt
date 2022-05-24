package com.example.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import androidx.activity.viewModels as viewModels1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val myViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MyViewModel::class.java)
        //需要在gradle中指定JVM为1.8
        val myViewModel by viewModels1<MyViewModel>()
        myViewModel.number.observe(this, Observer {
            textView.text = it.toString()
        })
        button.setOnClickListener {
            myViewModel.addOne()
        }
    }
}

