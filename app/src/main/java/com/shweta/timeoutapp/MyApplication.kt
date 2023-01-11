package com.shweta.timeoutapp

import android.app.Application
import android.os.Build
import androidx.work.*
import com.shweta.timeoutapp.worker.WorkerClass
import java.util.concurrent.TimeUnit

class MyApplication :Application() {

    override fun onCreate() {
        super.onCreate()
    }


}