package com.sofiamarchinskaya.hw1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sofiamarchinskaya.hw1.presenters.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityView {

    private var presenter: MainActivityPresenter = MainActivityPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
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