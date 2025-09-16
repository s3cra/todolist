package com.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todolist.databinding.TaskCardBinding
import com.todolist.entity.Task
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TaskAdapter(
    private val onItemOptionsClick: (Task) -> Unit,
    private val onItemClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback()) {

    inner class TaskViewHolder(val binding: TaskCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(task: Task) = with(binding){
            title.text = task.title
            dateBy.text = DateTimeFormatter.ofPattern("dd/MM/yy")
                .withZone(ZoneId.systemDefault())
                .format(Instant.ofEpochSecond(task.dueDateTime))
            options.setOnClickListener{this@TaskAdapter.onItemOptionsClick(task)}
            root.setOnClickListener {this@TaskAdapter.onItemClick(task)}
            if (task.isDone) binding.doneCheck.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskCardBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(o: Task, n: Task) = o.id == n.id
        override fun areContentsTheSame(o: Task, n: Task) = o == n
    }
}
