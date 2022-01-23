package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sofiamarchinskaya.hw1.databinding.ActivityMainBinding
import com.sofiamarchinskaya.hw1.states.MainMenuStates
import com.sofiamarchinskaya.hw1.viewmodels.MainActivityViewModel
import com.sofiamarchinskaya.hw1.viewmodels.NotesPagerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Активити, в которой находится основной интерфейс пирложения: фрагмент со списком,
 * фрагмент с основной информацией о заметке, меню
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        viewModel.menuState.observe(this) {
            when (it.state) {
                MainMenuStates.ABOUT -> openAboutScreen()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.selectMenuItem(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun openAboutScreen() {
        startActivity(Intent(this, AboutActivity::class.java))
    }
}