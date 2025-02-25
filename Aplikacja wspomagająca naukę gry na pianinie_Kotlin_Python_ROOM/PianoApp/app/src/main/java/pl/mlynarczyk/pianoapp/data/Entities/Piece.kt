package pl.mlynarczyk.pianoapp.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "piecesTable")
data class Piece(
    @PrimaryKey
    val id: Int? = null,

    val version: Int = 0,
    val compositor: String = "",
    val description: String = "",
    val name: String = "",
    val notesFile: String = "",
    val soundFile: String = "",
    var favourite : Boolean = false,
    var completed: Boolean = false
    )
