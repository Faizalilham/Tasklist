package coding.faizal.tasklist.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coding.faizal.tasklist.room.RoomDB
import coding.faizal.tasklist.room.dao.TaskDao
import coding.faizal.tasklist.room.entity.UserEntity

class UserViewModel(context : Context) : ViewModel() {

    lateinit var user : LiveData<MutableList<UserEntity>>
    private val database = RoomDB.buildDatabase(context)
    var dao: TaskDao = database.dao()

    init{

        user = dao.getAllUser()
    }
}

class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}