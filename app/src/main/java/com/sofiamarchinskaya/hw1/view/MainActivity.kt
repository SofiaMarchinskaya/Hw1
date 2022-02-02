package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.ActivityMainBinding
import com.sofiamarchinskaya.hw1.viewmodels.MainActivityViewModel
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

        viewModel.onAboutClickEvent.observe(this) {
            openAboutScreen()
        }
        viewModel.onWebViewClickEvent.observe(this) {
            openWebViewScreen()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                viewModel.onInfoIconClick()
                return true
            }
            R.id.web_view -> {
                viewModel.onWebViewIconClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAboutScreen() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun openWebViewScreen() {
        startActivity(Intent(this, WebViewActivity::class.java))
    }
}