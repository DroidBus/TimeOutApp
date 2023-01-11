package com.shweta.timeoutapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Audio
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shweta.timeoutapp.MainActivity
import com.shweta.timeoutapp.R


class WorkerClass(appContext: Context, appParams: WorkerParameters) :
    Worker(appContext, appParams) {

    private val WORK_RESULT = "work_result"
    val CHANNEL_ID = "task_channel"
    val channelName = "task_name"
    val context= appContext

    override fun doWork(): Result {
        Log.i("Worker Class","loaded")
       val taskData = inputData
        val taskDataString =taskData.getString(MainActivity.MESSAGE_STATUS)
        createNotification("Time Out!", taskDataString?:"Breathe Deep and relax")
        val outputData = Data.Builder().putString(WORK_RESULT, "Jobs Finished").build()
        return Result.success(outputData)
    }




    fun createNotification(title: String, message: String){
        // 1
        createNotificationChannel()
        // 2
        val intent = Intent(applicationContext, MainActivity:: class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // 3
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        // 4
        val icon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_baseline_notifications_active_24)
        // 5

        val ringtoneId= R.raw.audio
        val sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.audio )


       // val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        var notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setLargeIcon(icon)
            .setSound(sound)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()


                NotificationManagerCompat.from(applicationContext).notify(1, notification)

    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT ).apply {
                description = "Reminder Channel Description"
            }
            val notificationManager =  applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}