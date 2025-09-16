package com.todolist

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.todolist.databinding.CreateTaskDialogLayoutBinding
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import com.todolist.entity.Task
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class CreateTaskDialogFragment : DialogFragment() {

    lateinit var binding: CreateTaskDialogLayoutBinding

    private fun dateTimeSwap(){
        when(binding.datePicker.visibility){
            View.VISIBLE -> {
                binding.datePicker.visibility = View.GONE
                binding.timePicker.visibility = View.VISIBLE
            }
            View.GONE -> {
                binding.datePicker.visibility = View.VISIBLE
                binding.timePicker.visibility = View.GONE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateTaskDialogLayoutBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[TaskViewModel::class]
        var newTask = Task(
            title = "",
            description = "",
            dueDateTime = Instant.now().epochSecond
        )
        binding.confirmButton.setOnClickListener {
            newTask.title = binding.title.text.toString()
            newTask.description = binding.description.text.toString()
            viewModel.insert(newTask)
            this.dismiss()
        }
        binding.dateTimeBtn.setOnClickListener {
            binding.mainLayout.visibility = View.GONE
            binding.dateTimeLayout.visibility = View.VISIBLE
        }
        binding.dateTimeSwap.setOnClickListener {
            dateTimeSwap()
        }
        binding.dateTimeConfirm.setOnClickListener {
            val date = binding.datePicker.dayOfMonth
            val month = binding.datePicker.month + 1
            val year = binding.datePicker.year
            val hour = binding.timePicker.hour
            val minute = binding.timePicker.minute
            val dateTime = LocalDateTime.of(year, month, date, hour, minute)
            newTask.dueDateTime = dateTime.toEpochSecond(ZoneOffset.UTC)
            binding.mainLayout.visibility = View.VISIBLE
            binding.dateTimeLayout.visibility = View.GONE
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