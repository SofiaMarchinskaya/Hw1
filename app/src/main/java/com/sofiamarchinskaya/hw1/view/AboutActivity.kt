package com.sofiamarchinskaya.hw1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sofiamarchinskaya.hw1.R
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