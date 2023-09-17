package coding.faizal.tasklist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import coding.faizal.tasklist.room.dao.TaskDao
import coding.faizal.tasklist.room.entity.StatusEntity
import coding.faizal.tasklist.room.entity.TaskEntity
import coding.faizal.tasklist.room.entity.UserEntity
import coding.faizal.tasklist.room.utils.RoomHelper

@Database(
    entities = [TaskEntity::class,UserEntity::class,StatusEntity::class],
    version = 1
)
abstract class RoomDB  : RoomDatabase() {
    abstract fun dao(): TaskDao

    companion object {

        @Volatile
        private var instance: RoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "Taskk"
        ).addCallback(RoomHelper(context)).fallbackToDestructiveMigration().build()

    }
}
