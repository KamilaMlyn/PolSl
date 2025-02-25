package pl.mlynarczyk.pianoapp.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.mlynarczyk.pianoapp.menu.WeekDays
import pl.mlynarczyk.pianoapp.menu.getDayFromNumber

@Entity(tableName = "notificationsTable")
data class Notification(
    @PrimaryKey
    val id: Int? = null,
    val hour: Int,
    val minute: Int,
    val title: String,
    val text: String,
    val days: String) {

    fun getWeekDays(): List<WeekDays> {
        val daysList = mutableListOf<WeekDays>()
        days.forEach { num ->
            val day = getDayFromNumber(num.digitToInt())
            if (day != null) {
                daysList.add(day)
            }
        }
        return daysList
    }
}