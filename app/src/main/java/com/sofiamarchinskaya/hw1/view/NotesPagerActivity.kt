package com.sofiamarchinskaya.hw1.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.ActivityNotesPagerBinding
import com.sofiamarchinskaya.hw1.presenters.NotesPagerViewModel


class NotesPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesPagerBinding
    private val viewModel by lazy { ViewModelProvider(this)[NotesPagerViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        viewModel.apply {
            setCoroutineScope(lifecycleScope)
            update()
            init(intent.extras?.getLong(Constants.ID))
            list.observe(this@NotesPagerActivity) {
                binding.noteViewPager.adapter = NotesPagerAdapter(this@NotesPagerActivity, it)
            }
            index.observe(this@NotesPagerActivity) {
                binding.noteViewPager.setCurrentItem(it.toInt(), false)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
