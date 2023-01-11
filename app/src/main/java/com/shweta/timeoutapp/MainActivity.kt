package com.shweta.timeoutapp

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.shweta.timeoutapp.worker.WorkerClass
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setConstraints()
    }

    companion object {
        const val MESSAGE_STATUS = "message_status"
    }

    private fun setConstraints(){
        val builder= Constraints.Builder()
        builder.setRequiredNetworkType(NetworkType.CONNECTED)
        builder.setRequiresBatteryNotLow(false)
        builder.setRequiresCharging(false)
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            builder.setRequiresDeviceIdle(false)
        }
        val constraints= builder.build()
        val periodicWorkBuilder = PeriodicWorkRequest.Builder(WorkerClass::class.java,1, TimeUnit.MINUTES)
        periodicWorkBuilder.setConstraints(constraints)

        val request = periodicWorkBuilder.build()
        WorkManager.getInstance(this).enqueue(request)
    }
}