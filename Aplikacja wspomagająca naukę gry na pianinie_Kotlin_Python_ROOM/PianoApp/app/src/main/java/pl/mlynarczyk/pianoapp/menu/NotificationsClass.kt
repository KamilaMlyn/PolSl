package pl.mlynarczyk.pianoapp.menu

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import pl.mlynarczyk.pianoapp.MainActivity
import pl.mlynarczyk.pianoapp.R
import java.time.LocalTime
import java.util.Calendar

interface AlarmScheduler{
    fun scheduleOne(weekDay: Int,time: LocalTime,title: String, text: String, requestCode: Int)
    fun schedule(days:List<Int>,time: LocalTime, title: String, text: String, requestCode: Int)
    fun cancel (requestCode: Int)
}
class NotificationsClass(private val context: Context ){
    companion object{
        const val channelId = "notificationChannel"
    }
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @SuppressLint("LaunchActivityFromNotification")
    fun showNotification(title: String, text: String){
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            2,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.logo_v2)//drowable.logo_v1)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(2,notification)
    }

}

class Alarms(private val context: Context) : AlarmScheduler{
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleOne(weekDay: Int,time: LocalTime,title: String, text: String, requestCode: Int) {

        val intent = Intent(context, AlarmReceiver::class.java).apply {
              putExtra("text", text)
              putExtra("title", title)
              putExtra("time", "${time.hour}:${time.minute}")
        }
        val pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, weekDay)
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent)
    }

    override fun schedule(days:List<Int>,time: LocalTime, title: String, text: String, requestCode: Int) {

        days.forEach{ weekDay ->
            scheduleOne(
                weekDay,
                time,
                title,
                text,
                requestCode)
        }
    }

    override fun cancel(requestCode: Int) {

        alarmManager.cancel(PendingIntent.getBroadcast(context,requestCode,Intent(context,AlarmReceiver::class.java),PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))
    }
}

class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val text = intent?.getStringExtra("text") ?: return
        val title = intent.getStringExtra("title") ?: return
        val time = intent.getStringExtra("time") ?: return
        val notificationsClass = context?.let { NotificationsClass(it) }
        notificationsClass?.showNotification(title,text)
    }
}
