package com.todolist

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.todolist.databinding.CreateTaskDialogLayoutBinding
import com.todolist.databinding.TaskFullDialogLayoutBinding
import com.todolist.entity.Task
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class TaskFullDialogFragment(val task: Task) : DialogFragment() {
    lateinit var binding: TaskFullDialogLayoutBinding

    private fun toFormat(s: Int) : String{
        return when{
            s < 10 -> "0$s"
            else -> "$s"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TaskFullDialogLayoutBinding.inflate(inflater, container, false)
        binding.title.text = task.title
        binding.description.setText(task.description)
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(task.dueDateTime)
            , ZoneId.systemDefault())

        val date = "${toFormat(dateTime.dayOfMonth)}/${toFormat(dateTime.month.ordinal)}/${dateTime.year}"
        val time = "${toFormat(dateTime.hour)}:${toFormat(dateTime.minute)}"
        binding.date.text = date
        binding.time.text = time
        if (task.isDone){
            binding.done.visibility = View.VISIBLE
            binding.markAsDoneBtn.visibility = View.GONE
        }

        val viewModel = ViewModelProvider(this)[TaskViewModel::class]

        binding.markAsDoneBtn.setOnClickListener {
            task.isDone = true
            viewModel.update(task)
            dismiss()
        }
        binding.save.setOnClickListener {
            task.description = binding.description.text.toString()
            viewModel.update(task)
            dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog?.window?.setGravity(Gravity.CENTER)
    }
}