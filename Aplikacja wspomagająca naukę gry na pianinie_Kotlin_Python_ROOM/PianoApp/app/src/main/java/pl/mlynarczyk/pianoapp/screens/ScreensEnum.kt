package pl.mlynarczyk.pianoapp.screens

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import pl.mlynarczyk.pianoapp.R
import pl.mlynarczyk.pianoapp.ui.theme.AppAppearance

enum class ScreensEnum(val screen: String, val namePl: String, val icon: ImageVector) {
    HOME("HomeScreen","Strona główna", Icons.Default.Home),
    PIECE("PieceScreen","Utwór",Icons.Default.MusicNote),
    PIECES("PiecesScreen","Utwory",Icons.Default.MusicNote),
    WARMUPS("WarmUpsScreen","Rozgrzewki", Icons.AutoMirrored.Filled.QueueMusic,),
    WARMUP("WarmUpScreen","Rozgrzewka",Icons.AutoMirrored.Filled.QueueMusic),
    ACCOUNT("AccountScreen","Konto",Icons.Default.AccountBox),
    SETTINGS("SettingsScreen","Ustawienia",Icons.Default.Settings),
    FUNFACTS("FunFactsScreen","Ciekawostki",Icons.Default.Star),
    FUNFACT("FunFactScreen","Ciekawostka",Icons.Default.Star),
    NOTIFICATIONS("NotificationsScreen","Powiadomienia",Icons.Default.Notifications)
}