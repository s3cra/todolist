package com.todolist

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.todolist.databinding.CreateTaskDialogLayoutBinding
import com.todolist.databinding.FragmentTasksBinding
import com.todolist.databinding.TaskBottomSheetLayoutBinding
import com.todolist.databinding.TaskFullDialogLayoutBinding
import com.todolist.entity.Task
import java.time.Instant


class TasksFragment : Fragment() {
    lateinit var binding: FragmentTasksBinding
    lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun openList(view: RecyclerView, arrow: ImageView){
        view.visibility = when(view.visibility){
            View.VISIBLE -> View.GONE
            else -> View.VISIBLE
        }
        arrow.rotation = when(arrow.rotation){
            0F -> 180F
            else -> 0F
        }
    }

    private fun showBottomSheet(task: Task) {
        val bottomSheet = BottomSheetDialog(requireContext())
        val sheetBinding = TaskBottomSheetLayoutBinding.inflate(layoutInflater)
        bottomSheet.setContentView(sheetBinding.root)
        sheetBinding.markAsDoneBtn.setOnClickListener {
            task.isDone = true
            viewModel.update(task)
            bottomSheet.dismiss()
        }
        sheetBinding.deleteBtn.setOnClickListener {
            viewModel.delete(task)
            bottomSheet.dismiss()
        }
        bottomSheet.show()
    }

    private fun showPopUp(){
        val popupBinding = CreateTaskDialogLayoutBinding.inflate(layoutInflater)
        val popUp = CreateTaskDialogFragment()

        popUp.show(parentFragmentManager, "create_dialog")
    }

    private fun showTaskInfo(task: Task){
        val taskBinding = TaskFullDialogLayoutBinding.inflate(layoutInflater)
        val info = TaskFullDialogFragment(task)

        info.show(parentFragmentManager, "task_info_dialog")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding.createTaskBtn.setOnClickListener {
            showPopUp()
        }

        binding.currentTasks.setOnClickListener { openList(binding.currentTasksList, binding.currentTasksArrow) }
        binding.previousTasks.setOnClickListener { openList(binding.previousTasksList, binding.previousTasksArrow) }
        binding.completedTasks.setOnClickListener { openList(binding.completedTasksList, binding.completedTasksArrow) }
        var greeting = "Hello ${sharedPref.getString("username", "")}"
        binding.topcardGreeting.text = greeting

        val adapterCurr = TaskAdapter({ task ->
            showBottomSheet(task)
        }, { task ->
            showTaskInfo(task)
        })
        val adapterPrev = TaskAdapter({ task ->
            showBottomSheet(task)
        }, {task ->
            showTaskInfo(task)
        })
        val adapterComp = TaskAdapter({ task ->
            showBottomSheet(task)
        }, {task ->
            showTaskInfo(task)
        })
        binding.currentTasksList.adapter = adapterCurr
        binding.previousTasksList.adapter = adapterPrev
        binding.completedTasksList.adapter = adapterComp
        viewModel = ViewModelProvider(this)[TaskViewModel::class]
        viewModel.allTasks.observe(viewLifecycleOwner) {
            adapterCurr.submitList(it.filter { task ->
                task.dueDateTime > Instant.now().epochSecond && !task.isDone
            })
            adapterPrev.submitList(it.filter { task -> task.dueDateTime <= Instant.now().epochSecond })
            adapterComp.submitList(it.filter { task -> task.isDone
                    && task.dueDateTime > Instant.now().epochSecond})
        }
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