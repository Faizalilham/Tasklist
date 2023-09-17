package coding.faizal.tasklist.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import coding.faizal.tasklist.room.entity.StatusEntity
import coding.faizal.tasklist.room.entity.Task
import coding.faizal.tasklist.room.entity.TaskEntity
import coding.faizal.tasklist.room.entity.UserEntity


@Dao
interface TaskDao {

    @Query("SELECT TaskEntity.*,nameTask, StatusEntity.name_status AS status_name,UserEntity.name_user AS user_name FROM TaskEntity " +
            "INNER JOIN UserEntity ON TaskEntity.id_user = UserEntity.id_user " +
            "INNER JOIN StatusEntity ON TaskEntity.id_status_task = StatusEntity.id_status_task")
    fun getAllTasksWithUserAndStatus(): LiveData<List<Task>>


    @Query("SELECT * FROM TaskEntity")
    fun getAllTask():LiveData<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data : TaskEntity)

    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): LiveData<MutableList<UserEntity>>

    @Query("SELECT * FROM StatusEntity")
    fun getAllStatus(): LiveData<List<StatusEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataUser(data : UserEntity)

    @Insert
    suspend fun insertDataStatus(data : StatusEntity)





}