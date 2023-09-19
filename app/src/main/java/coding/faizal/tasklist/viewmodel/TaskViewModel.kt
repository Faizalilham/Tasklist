package coding.faizal.tasklist.viewmodel


import android.content.Context
import androidx.lifecycle.*
import coding.faizal.tasklist.room.RoomDB
import coding.faizal.tasklist.room.dao.TaskDao
import coding.faizal.tasklist.room.entity.StatusEntity
import coding.faizal.tasklist.room.entity.Task
import coding.faizal.tasklist.room.entity.TaskEntity
import coding.faizal.tasklist.room.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TaskViewModel(context : Context) : ViewModel() {

    var data : LiveData<List<Task>>
    lateinit var task : LiveData<List<TaskEntity>>
    lateinit var status : LiveData<List<StatusEntity>>
    lateinit var user : LiveData<MutableList<UserEntity>>

    private var _userById : MutableLiveData<UserEntity> = MutableLiveData()
    val userById : LiveData<UserEntity> = _userById

    private var _statusByIdUser : MutableLiveData<List<StatusEntity>> = MutableLiveData()
    var statusByIdUser : MutableLiveData<List<StatusEntity>> = _statusByIdUser

    private val database = RoomDB.buildDatabase(context)
    var dao: TaskDao = database.dao()

    init{

        data = dao.getAllTasksWithUserAndStatus()
        task = dao.getAllTask()
        status = dao.getAllStatus()
        user = dao.getAllUser()

    }


    fun getUserById(id : Int){
       viewModelScope.launch(Dispatchers.IO){
           val data = dao.getUserById(id)
           _userById.postValue(data)
       }
    }

    fun getStatusByIdUser(id : Int){
        viewModelScope.launch(Dispatchers.IO){
            val data = dao.getStatusByIdUser(id)
            _statusByIdUser.postValue(data)
        }
    }

    fun addTask(task : String,resultDropDown : Int,idUser : Int){
        viewModelScope.launch(Dispatchers.IO){
            val newTask = TaskEntity(
                idTaskList = 0,
                nameTask = task,
                id_user = idUser,
                id_status_task = resultDropDown
            )
            dao.insertData(newTask)
//            val status1 = StatusEntity(0, resultDropDown)
//
//            database.runInTransaction {
//               viewModelScope.launch {
//                   val statusId = dao.insertDataStatus(status1)
//                   newTask.id_status_task = statusId.toInt()
//                   dao.insertData(newTask)
//               }
//            }
        }
    }

//    fun addUser(){
//        viewModelScope.launch(Dispatchers.IO){
//            dao.insertDataUser(UserEntity(0,"Faizal"))
//        }
//    }

}

class TaskViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}