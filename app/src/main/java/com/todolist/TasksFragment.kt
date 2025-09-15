package com.todolist

import android.graphics.Matrix
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Visibility
import com.todolist.databinding.FragmentTasksBinding
import com.todolist.entity.Task


class TasksFragment : Fragment() {
    lateinit var binding: FragmentTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        binding.currentTasks.setOnClickListener {
            binding.currentTasksList.visibility = when(binding.currentTasksList.visibility){
                View.VISIBLE -> View.GONE
                else -> View.VISIBLE
            }
            binding.currentTasksArrow.rotation = when(binding.currentTasksArrow.rotation){
                0F -> 180F
                else -> 0F
            }
            println(binding.currentTasksArrow.rotation)
        }
//        var viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
//        println(viewModel.allTasks.value)
//        viewModel.insert(Task(
//            title = "title1",
//            description = "asdasdsa",
//            dueDateTime = 123123131,
//        ))
//        viewModel.allTasks.observeForever {
//            println(it)
//        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TasksFragment().apply {
                arguments = Bundle()
            }
    }
}