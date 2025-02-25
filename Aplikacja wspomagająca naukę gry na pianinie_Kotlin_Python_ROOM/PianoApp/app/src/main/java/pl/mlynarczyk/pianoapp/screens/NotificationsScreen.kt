package pl.mlynarczyk.pianoapp.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.data.Entities.Notification
import pl.mlynarczyk.pianoapp.data.Repo
import pl.mlynarczyk.pianoapp.menu.Alarms
import pl.mlynarczyk.pianoapp.menu.LoadingIndicator
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.menu.TopBar
import pl.mlynarczyk.pianoapp.menu.getWeekDays
import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme
import pl.mlynarczyk.pianoapp.ui.theme.RowInCard
import java.time.LocalTime
import java.util.Calendar


@Composable
fun NotificationsScreen(){
    var adding = remember{ mutableStateOf(false) }
    PianoAppTheme(darkTheme = SettingsObject.darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    TopBar(ScreensEnum.NOTIFICATIONS.namePl)
                    GetNotifications()
                }
                Surface(modifier = Modifier.align(Alignment.CenterEnd)) {
                    IconButton(onClick = { adding.value = true },Modifier.background(color = MaterialTheme.colorScheme.inversePrimary)) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }
            if (adding.value) {
                NewNotificationDialog(adding)
            }
        }
    }
}

@Composable
fun NewNotificationDialog(adding : MutableState<Boolean>) {
    Dialog(
        onDismissRequest = { adding.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        AddNewNotificationPanel(adding)
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GetNotifications() {
    var isLoading by remember{ mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var notificationsList by remember {  mutableStateOf<List<Notification>>(emptyList())}
    coroutineScope.launch {
        Repo.get().getNotifications().collect{
            newNotifications -> notificationsList = newNotifications
            isLoading = false
        }
    }
    if(isLoading){
        LoadingIndicator()
    }
    else {
        Column {
            if(notificationsList.isEmpty()){
                Text("Brak powiadomień")
            }
            else {
                notificationsList.forEach {
                    NotificationsCard(it)
                }
            }
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewNotificationPanel(
    addingNotification: MutableState<Boolean>,
    id: Int? = null,
    hour : Int? = null,
    min : Int? = null,
    text: String? = null,
    daysList : MutableList<Boolean>? = null
    ) {
    var notificationText by remember { mutableStateOf(text ?: "Pamiętaj o dzisiejszych ćwiczeniach na pianinie! :)") }
    val selected = remember { daysList ?: mutableListOf(false, false, false, false, false, false, false) }
    var delete by remember { mutableStateOf(false) }
    var add by remember { mutableStateOf(false) }
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = hour ?: currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = min ?: currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    val context : Context = LocalContext.current
    Surface(modifier = Modifier.padding(15.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                IconButton(onClick = {
                    addingNotification.value = false
                }) { Icon(imageVector = Icons.Default.Clear, contentDescription = null) }
                Spacer(modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                TimePicker(state = timePickerState)
                Spacer(modifier = Modifier.padding(30.dp))
                SelectDays(selected)
            }
            TextField(
                value = notificationText,
                onValueChange = { notificationText = it }
            )
            Button(onClick = {
                if(id != null){
                    delete = true

                }
                add = true

            }
            ) {
                Text(
                    if (id != null) {
                        "Aktualizuj powiadomienie"
                    } else {
                        "Dodaj przypomnienie o ćwiczeniu"
                    }
                )
            }
        }
    }
    if(add){
        add(context, timePickerState, selected, notificationText)
        add = false
    }
    if(delete){
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch{
            Repo.get().deleteFromNotificationsTable(id!!)
        }
        addingNotification.value = false
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun add(context: Context, time: TimePickerState, selected: List<Boolean>, notificationText: String) {

    val daysList = mutableListOf<Int>()
    var daysString = ""
    var iter = 1
    selected.forEach { select ->
        if (select) {
            daysList.add(iter)
            daysString += iter.toString()
        }
        iter++
    }

    var notificationsTime = LocalTime.now().withHour(time.hour).withMinute(time.minute)
    if (daysList.isEmpty()) {
        Toast.makeText(context, "Wybierz minimum jeden dzień tygodnia", Toast.LENGTH_LONG).show()
    } else {
        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch {
            val notification = Notification(hour = time.hour, minute = time.minute, title = "Przypominajka Arpeggiowa", days = daysString, text = notificationText)

            val id = Repo.get().insertNotifications(notification)

            val alarms = Alarms(context)
            alarms.schedule(
                daysList,
                notificationsTime,
                "Przypominajka Arpeggiowa",
                notificationText,
                id.toInt()

                )

        }
    }
}

@Composable
fun SelectDays( selected: MutableList<Boolean>) {
   Column(
      horizontalAlignment = Alignment.CenterHorizontally
   ){
        getWeekDays().forEach { day ->
            var isChecked by remember{ mutableStateOf( selected[day.number-1])}
            Checkbox(
                checked = isChecked,
                onCheckedChange = { newChecked ->
                    selected[day.number-1] = newChecked
                    isChecked = newChecked
                }
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(day.namePl)
            Spacer(modifier = Modifier.width(7.dp))
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NotificationsCard(notification: Notification) {
    var update = remember{ mutableStateOf(false) }
    var delete by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .padding(16.dp)
           // .height(60.dp)
            .wrapContentWidth()
    ) {

        RowInCard{

            Column {


                Row {
                    Icon(imageVector = ScreensEnum.NOTIFICATIONS.icon, contentDescription = null)
                    Spacer(Modifier.padding(5.dp))
                    Text("${notification.hour}:${notification.minute}")
                    Spacer(modifier = Modifier.padding(5.dp))
                    var notificationsDays: String = ""
                    notification.getWeekDays().forEach { day ->
                        notificationsDays += day.abbrevPl + " "
                    }

                    Text(notificationsDays)
                }
                Text(
                    if(notification.text.length > 30)
                        notification.text.take(30) + "..."
                    else
                        notification.text
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row() {
                IconButton(onClick = { delete = true }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    update.value = true
                }) { Icon(imageVector = Icons.Default.Create, contentDescription = null) }
            }
        }
    }


    if(update.value){

        val notifDays = MutableList(7){false}
        notification.getWeekDays().forEach{ day ->
            notifDays[day.number-1] = true
        }
        AddNewNotificationPanel(update,notification.id,notification.hour,notification.minute,notification.text,notifDays)

    }
    if(delete) {
        coroutineScope.launch {
            Repo.get().deleteFromNotificationsTable(notification.id!!)
            delete = false
        }

    }

}
