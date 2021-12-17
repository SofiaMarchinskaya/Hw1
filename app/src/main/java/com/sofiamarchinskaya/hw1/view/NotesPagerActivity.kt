package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.viewpager2.widget.ViewPager2
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.models.entity.Note
import com.sofiamarchinskaya.hw1.presenters.NotesPagerPresenterImpl
import com.sofiamarchinskaya.hw1.presenters.framework.NotesPagerPresenter
import com.sofiamarchinskaya.hw1.view.framework.NotesPagerActivityView

class NotesPagerActivity : AppCompatActivity(),NotesPagerActivityView {
    private lateinit var viewPager:ViewPager2
    private lateinit var presenter: NotesPagerPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_pager)
        setSupportActionBar(findViewById(R.id.toolBar))
        presenter = NotesPagerPresenterImpl(this)
        presenter.init(intent.extras)
    }

    override fun init(list: List<Note>, index: Long) {
        viewPager = findViewById<ViewPager2>(R.id.note_view_pager).apply {
            adapter = NotesPagerAdapter(this@NotesPagerActivity, list)
            setCurrentItem(index.toInt(), false)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }
}