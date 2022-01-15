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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

/**
 * Активити, в которой реализована возможность листать детальные отображения заметок
 */
class NotesPagerActivity : AppCompatActivity(), NotesPagerActivityView {
    private lateinit var viewPager: ViewPager2
    private lateinit var presenter: NotesPagerPresenter
    private lateinit var job: Job
    private lateinit var pagerAdapter: NotesPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_pager)
        setSupportActionBar(findViewById(R.id.toolBar))
        pagerAdapter = NotesPagerAdapter(this,presenter::onNoteSaved)
        presenter = NotesPagerPresenterImpl(NoteModelImpl(), this)
        presenter.init()
    }

    override fun init(listFlow: Flow<List<Note>>) {
        job = lifecycleScope.launch {
            listFlow.cancellable().collect {
                viewPager = findViewById<ViewPager2>(R.id.note_view_pager).apply {
                    adapter = pagerAdapter
                    setCurrentItem(
                        presenter.listCollected(
                            it,
                            intent.extras?.getLong(Constants.ID) ?: Constants.INVALID_ID
                        ), false
                    )
                }
                job.cancel()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNoteSaved(listFlow: Flow<List<Note>>, index: Long) {
        job.cancel()
        job = lifecycleScope.launch {
            listFlow.cancellable().collect {
                viewPager.apply {
                    (adapter as NotesPagerAdapter).update(it)
                    setCurrentItem(presenter.listCollected(it, index), false)
                }
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
