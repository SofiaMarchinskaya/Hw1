package com.sofiamarchinskaya.hw1.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofiamarchinskaya.hw1.databinding.ActivityAboutBinding

/**
 * Активити с информацией о создателе
 */
class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityAboutBinding.inflate(layoutInflater).root)
    }
}