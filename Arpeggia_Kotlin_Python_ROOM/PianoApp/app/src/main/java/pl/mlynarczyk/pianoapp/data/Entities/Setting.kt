package pl.mlynarczyk.pianoapp.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settingsTable")
data class Setting(

    //val id: Int? = null,
    @PrimaryKey
    val id: Int? = null,

    val setting: String = "",
    val value: String = ""
    )