package coding.faizal.tasklist.room.utils

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import coding.faizal.tasklist.room.RoomDB
import coding.faizal.tasklist.room.entity.StatusEntity
import coding.faizal.tasklist.room.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomHelper(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val database = Room.databaseBuilder(context, RoomDB::class.java, "Tasked").build()
            val userDao = database.dao()

            val user1 = UserEntity(0, "Faizal")
            val user2 = UserEntity(0, "Falakh")


            val status1 = StatusEntity(0, "Ongoing",1)
            val status2 = StatusEntity(0, "Finish",2)
            val status3 = StatusEntity(0, "Ongoing",1)
            val status4 = StatusEntity(0, "Finish",2)

            userDao.insertDataUser(user1)
            userDao.insertDataUser(user2)

            userDao.insertDataStatus(status1)
            userDao.insertDataStatus(status2)
            userDao.insertDataStatus(status3)
            userDao.insertDataStatus(status4)

            database.close()
        }
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        // Tindakan yang akan diambil saat database terbuka
        // Ini dapat digunakan untuk tugas lain yang perlu dilakukan saat database terbuka
    }
}