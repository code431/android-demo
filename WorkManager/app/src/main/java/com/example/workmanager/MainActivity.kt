package com.example.workmanager

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

const val SHP_PREFERENCES_KEY = "shp_key"
const val INPUT_DATA_KEY = "input_data_key"
const val OUTPUT_DATA_KEY = "output_data_key"
const val WORK_A_NAME = "Work A"
const val WORK_B_NAME = "Work B"

//对SharedPreferences添加观察需要SharedPreferences.OnSharedPreferenceChangeListener接口，重写onSharedPreferenceChanged
class MainActivity : AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener {
    private val workManager = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //添加SharedPreferences观察
        val sp = getSharedPreferences(SHP_PREFERENCES_KEY,Context.MODE_PRIVATE)
        sp.registerOnSharedPreferenceChangeListener(this)
        updataView()
        button.setOnClickListener {
            val workRequestA = createWork(WORK_A_NAME)
            val workRequestB = createWork(WORK_B_NAME)

            workManager.beginWith(workRequestA)
                .then(workRequestB)
                .enqueue()
        }
    }
    private fun updataView(){
        val sp = getSharedPreferences(SHP_PREFERENCES_KEY,Context.MODE_PRIVATE)
        textViewA.text =sp.getInt(WORK_A_NAME,0).toString()
        textViewB.text =sp.getInt(WORK_B_NAME,0).toString()
    }
    private fun createWork(name:String): OneTimeWorkRequest {
        //设置约束条件
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        return OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf(INPUT_DATA_KEY to name))
            .build()
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        updataView()
    }
}
