package com.sofiamarchinskaya.hw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity(), MainActivityView {

    private var presenter : MainActivityPresenter = MainActivityPresenterImpl(this, SaveModelImpl())
    private lateinit var text : EditText
    private lateinit var title : EditText
    private lateinit var save : Button
    private lateinit var share : Button

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.text)
        title = findViewById(R.id.title)
        save = findViewById(R.id.save)
        share = findViewById(R.id.share)
        save.setOnClickListener { presenter.onSave(text.text.toString(), title.text.toString()) }
        share.setOnClickListener {
            presenter.shareOnClick(
                title.text.toString(),
                text.text.toString()
            )
        }
    }

    override fun onSaveComplete() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_LONG).show()
    }

    override fun onFailed() {
        Toast.makeText(this, getString(R.string.no_content), Toast.LENGTH_LONG).show()
    }

    override fun openAboutScreen() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    override fun shareNote(title : String, text : String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = TYPE
            putExtra(Intent.EXTRA_TEXT, "$title\n$text")
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            R.id.about -> {
                presenter.aboutOnClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val TYPE = "text/plain"
    }
}