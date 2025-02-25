package pl.mlynarczyk.pianoapp.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "funFactsTable")
data class FunFact(
    @PrimaryKey
    val id: Int? = null,

    val version: Int = 0,
    val title: String = "",
    val text: String = "",
    val source: String = "",
    var favourite: Boolean = false
)