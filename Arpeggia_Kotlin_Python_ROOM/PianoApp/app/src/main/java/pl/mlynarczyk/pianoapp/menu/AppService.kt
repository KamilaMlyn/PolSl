package pl.mlynarczyk.pianoapp.menu

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AppService: Service() {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var toDoFunc: ()->List<Int> = {listOf(-1)}
    private var toEndFunc: (List<Int>)->Unit = {}

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    @Suppress("DEPRECATION")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

     //   toDoFunc = intent?.getSerializableExtra("toDoFunc") as? () -> Int ?: { -1 }
     //   toEndFunc = intent?.getSerializableExtra("toEndFunc") as? () -> Unit ?: {  }

        //startForeground(1, createNotification())

        // Uruchom analizę w tle
        scope.launch {
            val result = toDoFunc()
            toEndFunc(result)
            stopSelf() // Zatrzymaj serwis po zakończeniu
        }

        return START_NOT_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel() // Anuluj wszystkie zadania w tle, jeśli serwis zostanie zniszczony
    }
}