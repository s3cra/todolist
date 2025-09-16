package com.todolist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.todolist.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        var viewModel = ViewModelProvider(this)[TaskViewModel::class]
        viewModel.allTasks.observe(viewLifecycleOwner){
            var donePercentage = when{
                it.isNotEmpty() -> (it.count { task -> task.isDone }).toFloat() / (it.size).toFloat() * 100f
                else -> 0f
            }
            binding.progress.setProgressWithAnimation(donePercentage , 500)
            var donePercentageText = "You Have Done $donePercentage% Of Your Tasks!"
            binding.text.text = donePercentageText
        }
        val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        binding.username.setText(sharedPref.getString("username", ""))

        binding.save.setOnClickListener {
            editor.putString("username", binding.username.text.toString())
            editor.apply()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment()
    }
}