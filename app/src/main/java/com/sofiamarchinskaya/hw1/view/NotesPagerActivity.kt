package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.models.Note
import com.sofiamarchinskaya.hw1.presenters.NotesPagerPresenterImpl
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView

class NotesPagerActivity : AppCompatActivity(),NotesPagerActivityView {
    private lateinit var viewPager:ViewPager2
    private lateinit var presenter: NotesPagerPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_pager)
        presenter = NotesPagerPresenterImpl(this)
        presenter.init()
    }

    override fun init(list: List<Note>) {
        viewPager = findViewById<ViewPager2>(R.id.note_view_pager).apply {
            adapter = NotesPagerAdapter(this@NotesPagerActivity, list)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}