package com.example.hermivaldo.projetoinicial.util

import android.os.Handler
import android.os.HandlerThread

class DbWorkThread(thread: String) : HandlerThread(thread) {


    private lateinit var mWorkHanlder: Handler


    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mWorkHanlder = Handler(looper)
    }

    fun postTask(task: Runnable){

        this.mWorkHanlder.post(task)
    }

}