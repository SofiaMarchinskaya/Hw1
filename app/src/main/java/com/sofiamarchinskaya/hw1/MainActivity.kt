package com.sofiamarchinskaya.hw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity(), MainActivityView {
    private var presenter : MainActivityPresenter = MainActivityPresenterImpl(this, SaveModelImpl())
    private lateinit var text : EditText
    private lateinit var title : EditText
    private lateinit var save : Button

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById<EditText>(R.id.text)
        title = findViewById<EditText>(R.id.title)
        save = findViewById<Button>(R.id.save)
        save.setOnClickListener { presenter.onSave(text.text.toString(), title.text.toString()) }

    }

    override fun onSaveComplete() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_LONG).show()
    }

    override fun onFailed() {
        Toast.makeText(this, getString(R.string.no_content), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}