package com.sofiamarchinskaya.hw1.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.ActivityNotesPagerBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.viewmodels.NotesPagerViewModel
import kotlinx.coroutines.launch


class NotesPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesPagerBinding
    private val viewModel by lazy { ViewModelProvider(this)[NotesPagerViewModel::class.java] }
    private var isCurrentItem = true
    private lateinit var pagerAdapter: NotesPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        pagerAdapter = NotesPagerAdapter(this@NotesPagerActivity).also {
            binding.noteViewPager.adapter = it
        }
        viewModel.apply {
            lifecycleScope.launch {
                init(intent.extras?.getLong(Constants.ID))
            }
            list.observe(this@NotesPagerActivity) {
                pagerAdapter.update(it)
            }
            index.observe(this@NotesPagerActivity) {
                if (isCurrentItem) {
                    binding.noteViewPager.setCurrentItem(it.toInt(), false)
                    isCurrentItem = false
                } else {
                    binding.noteViewPager.setCurrentItem(binding.noteViewPager.currentItem, false)
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        fun getStartIntent(context: Context, note: Note): Intent =
            Intent(context, NotesPagerActivity::class.java).apply {
                putExtra(Constants.TITLE, note.title)
                putExtra(Constants.TEXT, note.body)
                putExtra(Constants.ID, note.id)
            }
    }
}
