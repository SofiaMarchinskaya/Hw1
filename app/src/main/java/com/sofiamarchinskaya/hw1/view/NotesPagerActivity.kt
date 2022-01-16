package com.sofiamarchinskaya.hw1.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.models.NoteModelImpl
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.presenters.NotesPagerPresenterImpl
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

/**
 * Активити, в которой реализована возможность листать детальные отображения заметок
 */
class NotesPagerActivity : AppCompatActivity(), NotesPagerActivityView {
    private lateinit var viewPager: ViewPager2
    private lateinit var presenter: NotesPagerPresenter
    private lateinit var pagerAdapter: NotesPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_pager)
        setSupportActionBar(findViewById(R.id.toolBar))
        presenter = NotesPagerPresenterImpl(NoteModelImpl(), this)
        pagerAdapter = NotesPagerAdapter(this, presenter::init)
        viewPager = findViewById<ViewPager2>(R.id.note_view_pager).apply {
            adapter = pagerAdapter
        }
        presenter.init(intent.extras?.getLong(Constants.ID) ?: Constants.INVALID_ID)
    }

    override fun init(listFlow: Flow<List<Note>>, id: Long) {
        lifecycleScope.launch {
            listFlow.collect {
                viewPager.apply {
                    pagerAdapter.update(it)
                    setCurrentItem(
                        presenter.getIndex(it, id),
                        false
                    )
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
