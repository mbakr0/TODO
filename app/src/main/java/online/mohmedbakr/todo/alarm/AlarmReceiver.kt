package online.mohmedbakr.todo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import online.mohmedbakr.todo.util.createNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        title?.let {
            createNotification(context,it)
        }
    }
}