package pl.mlynarczyk.pianoapp.menu

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SoundPlayer(soundFile: String, table : String, context: Context) {
    val coroutineScope = rememberCoroutineScope()
    var soundLoading by remember { mutableStateOf(true) }
    var sound: Pair<Int, String?> by remember { mutableStateOf(Pair(0, null)) }
    var enableButton: Boolean by remember { mutableStateOf(false) }
    var displaySoundPlayer: Boolean by remember { mutableStateOf(false) }
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            enabled = enableButton,
            onClick = {
                displaySoundPlayer = displaySoundPlayer.not()
            },
            modifier = Modifier.animateContentSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!displaySoundPlayer) {
                    if (soundLoading) {
                        LoadingIndicator(Modifier.height(10.dp), Modifier.size(15.dp))
                        Text("Ładowanie dźwięku")
                        coroutineScope.launch {
                            sound = getIdOrUrl(table, soundFile, "mp3", context)
                            soundLoading = false
                        }
                    } else {
                        if (sound.first == 0 && sound.second == null) {
                            Icon(imageVector = Icons.Default.Warning, null)
                            Text(text = "Błąd ładowania")
                        } else {
                            Icon(imageVector = Icons.Default.PlayArrow, null)
                            enableButton = true;
                        }
                    }

                } else {

                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                    DisplaySoundPlayer(sound, context)
                }
            }
        }

    }
}

@Composable
fun DisplaySoundPlayer(sound : Pair<Int, String?>, context: Context) {

    val coroutineScope = rememberCoroutineScope()
    var isPlaying : Boolean by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0) }
    val mediaPlayer = remember {
        if (sound.first != 0) {
            MediaPlayer.create(context, sound.first).apply {
                MusicPlayer.setMP(this)
            }
        } else if (sound.second != null) {
            MediaPlayer().apply {
                setDataSource(sound.second)
                prepare()
                MusicPlayer.setMP(this)

            }
        } else {
            null
        }
    }
    mediaPlayer ?: return
    DisposableEffect(Unit) {
        onDispose { mediaPlayer.release() }
    }
    Column(){
        Slider(
            value = currentPosition.toFloat(),
            onValueChange = {
                mediaPlayer.seekTo(it.toInt())
                currentPosition = it.toInt()
            },
            valueRange = 0f..mediaPlayer.duration.toFloat()//,
            // modifier = Modifier.fillMaxWidth()
        )
        Row(){
            IconButton(
                onClick = {
                    if (isPlaying) {
                        MusicPlayer.getMP()!!.pause()
                        isPlaying = false
                    } else {
                        MusicPlayer.getMP()!!.start()
                        isPlaying = true
                        coroutineScope.launch {
                            while (mediaPlayer.isPlaying) {
                                currentPosition = mediaPlayer.currentPosition
                                delay(500)
                            }
                        }
                    }
                }
            ) {
                Icon(
                    imageVector =
                    if(isPlaying)
                        Icons.Default.Pause
                    else
                        Icons.Default.PlayArrow,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                MusicPlayer.getMP()!!.stop()
                MusicPlayer.getMP()?.prepare()
                currentPosition = 0
                isPlaying = false
            },

                ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
        }
    }
}

object MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null
    fun setMP(mediaPlayer: MediaPlayer?)
    {
        this.mediaPlayer?.release()
        this.mediaPlayer = mediaPlayer
        this.mediaPlayer?.setOnCompletionListener {

        }

    }
    fun getMP() : MediaPlayer?
    {
        return mediaPlayer
    }
}
