package online.mohmedbakr.todo.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import online.mohmedbakr.todo.R
import online.mohmedbakr.todo.database.Task
const val CHANNEL_ID = "0000"
const val NOTIFICATION_ID = 1001

fun createNotification(context:Context, title:String){


    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(CHANNEL_ID, "TODO",
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.enableLights(true)
        channel.lightColor = Color.BLUE
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)
    }

    val pattern = longArrayOf(0,10,200,500,700,1000,300,200,50,10)
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_thumb_up_24)
        .setContentTitle(title)
        .setVibrate(pattern)
        .setStyle(NotificationCompat.BigTextStyle())
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        .build()


    notificationManager.notify(NOTIFICATION_ID,builder)


}