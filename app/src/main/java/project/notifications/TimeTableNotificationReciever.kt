package project.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimeTableNotificationReciever: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = TimeTableNotifications(context)
        service.showNotification( "message", "time")
    }
}