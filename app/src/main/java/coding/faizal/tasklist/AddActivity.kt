package coding.faizal.tasklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coding.faizal.tasklist.databinding.ActivityAddBinding
import coding.faizal.tasklist.room.entity.StatusEntity
import coding.faizal.tasklist.room.entity.Task
import coding.faizal.tasklist.room.entity.TaskEntity
import coding.faizal.tasklist.room.entity.UserEntity
import coding.faizal.tasklist.viewmodel.TaskViewModel
import coding.faizal.tasklist.viewmodel.TaskViewModelFactory
import coding.faizal.tasklist.viewmodel.UserViewModelFactory

class AddActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var _binding : ActivityAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskViewModel: TaskViewModel
    private var resultDropDown = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddBinding.inflate(layoutInflater)
        val viewModelFactory = TaskViewModelFactory(this)
        taskViewModel = ViewModelProvider(this,viewModelFactory)[TaskViewModel::class.java]
        setContentView(binding.root)

        dropDownMenu()


        val dataIntent = intent.getParcelableExtra<UserEntity>("data")
        Toast.makeText(this, "${dataIntent?.id_user}", Toast.LENGTH_SHORT).show()
        if(dataIntent != null){ submitData(dataIntent.id_user) }

    }



    // Set DropDownMenu
    private fun dropDownMenu(){
        val data = resources.getStringArray(R.array.status)
        val adapter = ArrayAdapter(this,R.layout.dropdown_item,data)
        with(binding.tvStatusTask){
            setAdapter(adapter)
            onItemClickListener = this@AddActivity
        }
    }

    // Set DropDown Menu OnClick
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        resultDropDown = item


    }


    private fun submitData(idUser : Int?){
        binding.btnSubmit.setOnClickListener {
            if (idUser != null) {

                try {
                    taskViewModel.addTask(
                        binding.etNameTask.text.toString(),
                        resultDropDown,
                        idUser
                    )

                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java).also { finish() })
                }catch (e : Exception){
                    Toast.makeText(this, "Error ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}