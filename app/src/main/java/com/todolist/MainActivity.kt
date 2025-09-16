package com.todolist

import android.app.UiModeManager.MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.todolist.databinding.ActivityMainBinding
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.todolist.entity.Task

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tasksFragment = TasksFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment(tasksFragment)
        binding.bottomMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.tasks_item -> changeFragment(tasksFragment)
                else -> changeFragment(profileFragment)
            }
            true
        }

    }

    override fun onStart() {
        super.onStart()

    }

    fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragment)
            addToBackStack(null)
            commit()
        }
    }
}