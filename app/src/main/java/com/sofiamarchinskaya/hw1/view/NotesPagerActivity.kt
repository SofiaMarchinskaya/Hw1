package com.sofiamarchinskaya.hw1.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sofiamarchinskaya.hw1.types.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.ActivityNotesPagerBinding
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.viewmodels.NotesPagerViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesPagerBinding
    private val viewModel: NotesPagerViewModel by viewModel()
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
                init(intent.extras?.getInt(Constants.ID))
            }
            listWithIndex.observe(this@NotesPagerActivity) {
                observeListWithIndex(it.list, it.index)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun observeListWithIndex(list: List<Note>, index: Int) {
        pagerAdapter.update(list)
        with(binding) {
            if (isCurrentItem) {
                noteViewPager.setCurrentItem(index, false)
                isCurrentItem = false
            } else {
                noteViewPager.setCurrentItem(
                    noteViewPager.currentItem,
                    false
                )
            }
        }
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
