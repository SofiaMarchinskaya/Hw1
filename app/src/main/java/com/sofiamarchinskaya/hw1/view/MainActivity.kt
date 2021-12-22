package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.ActivityMainBinding

/**
 * Активити, в которой находится основной интерфейс пирложения: фрагмент со списком,
 * фрагмент с основной информацией о заметке, меню
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                openAboutScreen()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAboutScreen() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

}