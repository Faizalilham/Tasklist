package coding.faizal.tasklist.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val idTaskList: Int,
    val nameTask: String?,
    val user_name: String?,
    val status_name: String?
):Parcelable
