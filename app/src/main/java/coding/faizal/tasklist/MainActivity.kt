package coding.faizal.tasklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coding.faizal.tasklist.adapter.TaskAdapter
import coding.faizal.tasklist.databinding.ActivityMainBinding
import coding.faizal.tasklist.room.RoomDB
import coding.faizal.tasklist.room.entity.Task
import coding.faizal.tasklist.room.entity.TaskEntity
import coding.faizal.tasklist.viewmodel.TaskViewModel
import coding.faizal.tasklist.viewmodel.TaskViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
//    private  var listdata : MutableList<Task> = mutableListOf()
    private lateinit var taskAdapter : TaskAdapter
    private lateinit var taskViewModel : TaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModelFactory = TaskViewModelFactory(this)
        taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        taskViewModel.data.observe(this){
            setupRecycler(it)
            CoroutineScope(Dispatchers.Main).launch {

                updateRecyclerView(it)
            }
        }

        taskViewModel.task.observe(this){
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }

        taskViewModel.status.observe(this){
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this,UserActivity::class.java))
        }

    }

    suspend fun updateRecyclerView(newData: List<Task>) {
            taskAdapter.refreshData(newData)
            taskAdapter.notifyDataSetChanged()
    }


    private fun setupRecycler(listData : List<Task>){

        taskAdapter = TaskAdapter(
            listData.toMutableList(),
            object  : TaskAdapter.OnClick{
                override fun onClickItem(data: Task) {
                   startActivity(Intent(this@MainActivity,AddActivity::class.java).also {
                       it.putExtra("data",data)
                   })
                }
            }
        )
        binding.rvTask.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}