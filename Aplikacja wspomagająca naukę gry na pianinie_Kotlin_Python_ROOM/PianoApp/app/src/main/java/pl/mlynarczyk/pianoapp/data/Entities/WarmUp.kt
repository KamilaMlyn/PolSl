package pl.mlynarczyk.pianoapp.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "warmUpsTable")
data class WarmUp(
    @PrimaryKey
    val id: Int? = null,

    val version: Int = 0,
    val name: String = "",
    val notesFile: String = "",
    val description: String = "",
    val soundFile: String = ""
)
