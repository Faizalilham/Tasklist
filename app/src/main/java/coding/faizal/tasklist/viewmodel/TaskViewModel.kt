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

    private val database = RoomDB.buildDatabase(context)
    var dao: TaskDao = database.dao()

    init{

        data = dao.getAllTasksWithUserAndStatus()
        task = dao.getAllTask()
        status = dao.getAllStatus()
    }

    fun addTask(task : String,resultDropDown : String,idUser : Int){
        viewModelScope.launch(Dispatchers.IO){
            val newTask = TaskEntity(
                idTaskList = 0,
                nameTask = task,
                id_user = idUser,
                id_status_task = if(resultDropDown == "Ongoing") 1 else 2
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