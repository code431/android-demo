package com.example.workmanager

import android.content.Context
import android.icu.util.Output
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

//工作者类
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val name = inputData.getString(INPUT_DATA_KEY)
        //Log.d("hello","start")
        Thread.sleep(3000)
        //Log.d("hello","finish")
        val sp = applicationContext.getSharedPreferences(SHP_PREFERENCES_KEY,Context.MODE_PRIVATE)
        var number = sp.getInt(name,0)
        sp.edit().putInt(name,++number).apply()
        return Result.success(workDataOf(OUTPUT_DATA_KEY to "$name output"))
    }
}