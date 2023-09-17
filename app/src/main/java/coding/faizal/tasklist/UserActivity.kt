package coding.faizal.tasklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coding.faizal.tasklist.adapter.UserAdapter
import coding.faizal.tasklist.databinding.ActivityUserBinding
import coding.faizal.tasklist.room.entity.UserEntity
import coding.faizal.tasklist.viewmodel.TaskViewModelFactory
import coding.faizal.tasklist.viewmodel.UserViewModel
import coding.faizal.tasklist.viewmodel.UserViewModelFactory

class UserActivity : AppCompatActivity() {

    private var _binding : ActivityUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter : UserAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserBinding.inflate(layoutInflater)
        val viewModelFactory = UserViewModelFactory(this)
        userViewModel = ViewModelProvider(this,viewModelFactory)[UserViewModel::class.java]
        setContentView(binding.root)
        getAllUser()
    }

    private fun getAllUser(){
        userViewModel.user.observe(this){
            setupRecycler(it)
        }
    }

    private fun setupRecycler(data : MutableList<UserEntity>){

        userAdapter = UserAdapter(data,object : UserAdapter.OnClick{
            override fun onClick(data: UserEntity) {
                startActivity(Intent(this@UserActivity,AddActivity::class.java).also{
                    it.putExtra("data",data)
                })
            }

        })
        binding.rvUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@UserActivity)
        }

    }
}