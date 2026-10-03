package com.example.islamicdigitalclock
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
class AdhanNotificationService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val name = intent?.getStringExtra("prayer_name") ?: "نماز"
        try { mediaPlayer = MediaPlayer.create(this, R.raw.adhan)?.apply { start() } } catch (_: Exception) {}
        showNotification(name)
        return START_NOT_STICKY
    }
    private fun showNotification(prayerName: String) {
        val channelId = "adhan_channel"
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(NotificationChannel(
                channelId, "اذان نوٹیفکیشن", NotificationManager.IMPORTANCE_HIGH))
        }
        val n = NotificationCompat.Builder(this, channelId)
            .setContentTitle("اذان کا وقت")
            .setContentText("$prayerName کی اذان کا وقت ہو گیا ہے")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setPriority(NotificationCompat.PRIORITY_HIGH).build()
        nm.notify(1, n)
    }
    override fun onDestroy() { mediaPlayer?.release(); super.onDestroy() }
    override fun onBind(intent: Intent?): IBinder? = null
}
