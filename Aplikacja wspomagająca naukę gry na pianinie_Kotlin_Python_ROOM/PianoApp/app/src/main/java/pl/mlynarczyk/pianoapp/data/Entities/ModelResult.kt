package pl.mlynarczyk.pianoapp.data.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Collections.max

@Entity(
    tableName = "modelResultsTable"//,
//    foreignKeys = [
//        ForeignKey(
//            entity = Piece::class,
//            parentColumns = ["id"],
//            childColumns = ["id"],
//            onDelete = ForeignKey.NO_ACTION
//        ),
//        ForeignKey(
//            entity = WarmUp::class,
//            parentColumns = ["id"],
//            childColumns = ["id"],
//            onDelete = ForeignKey.NO_ACTION
//        )
//    ]
    )

data class ModelResult(
    @PrimaryKey
    val id: Int? = null,
    val id_FK: Int? = null,
    val date: String = "",
    val table: Boolean = true, //true = utwory, false = rozgrzewki
    val Q1 : Float = -1.0f,
    val Q2 : Float = -1.0f,
    val Q3 : Float = -1.0f,
    val Q4 : Float = -1.0f

){
    fun getEmotionality(): Int{
        return when (listOf(Q1,Q2,Q3,Q4).max()){
            Q1 -> 1
            Q2 -> 2
            Q3 -> 3
            Q4 -> 4
            else -> 0
        }
    }
}