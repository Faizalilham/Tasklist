package coding.faizal.tasklist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.tasklist.databinding.TaskItemBinding
import coding.faizal.tasklist.room.entity.Task

class TaskAdapter(
    private val data : MutableList<Task>,
    private val listener : OnClick
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder (val binding : TaskItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
       return TaskViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.apply {
            taskName.text = data[position].nameTask
             if(data[position].status_name != null && data[position].user_name != null ){
                 status.visibility = View.VISIBLE
                 user.visibility = View.VISIBLE
                 status.text = data[position].status_name
                 user.text = data[position].user_name
             }

            card.setOnClickListener {
                listener.onClickItem(data[position])
            }

        }
    }

    override fun getItemCount(): Int = data.size

    fun refreshData(datas : List<Task>){
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }


    interface OnClick{
         fun onClickItem(data : Task)
    }
}