package coding.faizal.tasklist.room.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import kotlinx.parcelize.Parcelize



@Parcelize
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StatusEntity::class,
            parentColumns = ["id_status_task"],
            childColumns = ["id_status_task"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("id_user"), Index("id_status_task")]
)
data class TaskEntity(


    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="idTaskList")
    val idTaskList : Int,

    @ColumnInfo(name = "nameTask")
    val nameTask : String?,

    @ColumnInfo(name = "id_user")
    val id_user : Int?,

    @ColumnInfo(name = "id_status_task")
    var id_status_task : Int?

):Parcelable
