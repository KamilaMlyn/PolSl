package pl.mlynarczyk.pianoapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.data.PianoDB
import pl.mlynarczyk.pianoapp.data.Repo
import pl.mlynarczyk.pianoapp.data.Repository
import pl.mlynarczyk.pianoapp.data.checkSettingsTable
import pl.mlynarczyk.pianoapp.data.updateFunFactsTable
import pl.mlynarczyk.pianoapp.data.updatePiecesTable
import pl.mlynarczyk.pianoapp.data.updateWarmUpsTable
import pl.mlynarczyk.pianoapp.menu.NotificationsClass
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.python.PythonRepo
import pl.mlynarczyk.pianoapp.screens.deleteFromTmp

//import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen() //splash screen
        enableEdgeToEdge()
        setContent {

            //lokalna baza danych ROOM
            val repository = Repository()
            val dao = PianoDB.getDB(this).getDao()
            repository.setDao(dao)
            Repo.set(repository)

            val coroutineScope = rememberCoroutineScope()

            //RealtimeDatabase FireBase
            val firebaseDB = Firebase.database
            updateFirebaseDB(this,firebaseDB, coroutineScope)

            //pobranie ustawien z Room
            coroutineScope.launch {

                SettingsObject.checkBasicSettings()

            }

            //nawigacja
            val navController = rememberNavController()
            Navigation.SetNavController(navController)

            //powiadomienia
            createNotificationsChannel()

            //Python
            PythonRepo.start(this)


        }

    }

    private fun createNotificationsChannel() {
        val channel = NotificationChannel(
            NotificationsClass.channelId,
            "Arpeggia Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Do powiadomień przypominających o ćwiczeniach na pianinie"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }


    private fun updateFirebaseDB(context: Context, firebaseDB: FirebaseDatabase, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            updateFunFactsTable(context,firebaseDB)
        }
        coroutineScope.launch {
            updatePiecesTable(context,firebaseDB)
        }
        coroutineScope.launch {
            updateWarmUpsTable(context,firebaseDB)
        }
        coroutineScope.launch {
            checkSettingsTable(context,firebaseDB)
        }
    }


    override fun onStop(){
        super.onStop()
        lifecycleScope.launch {
            SettingsObject.saveSettings()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        deleteFromTmp(this)

    }

}