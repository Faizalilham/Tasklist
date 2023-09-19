package coding.faizal.tasklist.room.entity

import androidx.annotation.NonNull
import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("id_user")]
)
data class StatusEntity(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name= "id_status_task")
    val idStatusTask : Int,

    @ColumnInfo(name = "name_status")
    val nameStatus : String?,

    @ColumnInfo(name = "id_user")
    val idUser : Int

)
