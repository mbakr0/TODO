package online.mohmedbakr.todo.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import online.mohmedbakr.todo.database.Task


object Alarm {
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    fun createAlarm(context:Context, task: Task){
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra("title",task.title)
            PendingIntent.getBroadcast(context, task.date.toInt(), intent,PendingIntent.FLAG_UPDATE_CURRENT)

        }

        alarmMgr?.set(
            AlarmManager.RTC_WAKEUP,
            task.date,
            alarmIntent
        )
    }

    fun removeAlarm(context:Context, task: Task){
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, task.date.toInt(), intent,PendingIntent.FLAG_UPDATE_CURRENT)
        }
        alarmMgr?.cancel(alarmIntent)

    }
}