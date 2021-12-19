package com.sofiamarchinskaya.hw1.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sofiamarchinskaya.hw1.presenters.MainActivityPresenterImpl
import com.sofiamarchinskaya.hw1.view.framework.MainActivityView
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.presenters.framework.MainActivityPresenter

/**
 * Активити, в которой находится основной интерфейс пирложения: фрагмент со списком,
 * фрагмент с основной информацией о заметке, меню
 */
class MainActivity : AppCompatActivity(), MainActivityView {

    private var presenter: MainActivityPresenter = MainActivityPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolBar))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                presenter.aboutOnClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openAboutScreen() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

}