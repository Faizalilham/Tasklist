package coding.faizal.tasklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.tasklist.databinding.UserItemBinding
import coding.faizal.tasklist.room.entity.UserEntity

class UserAdapter(
    val data : MutableList<UserEntity>,
    private val listener : OnClick
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding : UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       return UserViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.binding.apply {
           userName.text = data[position].name_user

           card.setOnClickListener {
               listener.onClick(data[position])
           }
       }
    }

    override fun getItemCount(): Int = data.size

    interface OnClick{
        fun onClick(data : UserEntity)
    }
}