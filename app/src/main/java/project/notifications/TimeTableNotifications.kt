package project.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.vamz_semestralka.R

class TimeTableNotifications ( private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showNotification(message: String, time: String) {
        val notification = NotificationCompat.Builder(context, "timetable_notification_channel")
            .setSmallIcon(R.drawable.tim_t_notification)
            .setContentTitle(message)
            .setContentText("Time: $time")
            .build()

        notificationManager.notify(1, notification)
    }
    // NOT WORKING
    /*fun scheduleNotification(context: Context, message: String, time: String, dayOfWeek: Int) {
        val parsedTime = parseTimeString(time)
        if (parsedTime == null) {
            return
        }

        val (hours, minutes) = parsedTime

        val notificationIntent = Intent(context, TimeTableNotificationReciever::class.java)
        notificationIntent.putExtra("message", message)
        notificationIntent.putExtra("time", time)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7, // Repeat weekly
            pendingIntent
        )
    }

    private fun parseTimeString(timeString: String): Pair<Int, Int>? {
        val timeParts = timeString.split(":")
        if (timeParts.size != 2) {
            return null
        }

        val hours = timeParts[0].toIntOrNull()
        val minutes = timeParts[1].toIntOrNull()

        if (hours == null || minutes == null || hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            return null
        }

        return Pair(hours, minutes)
    }*/
}