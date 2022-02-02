package com.sofiamarchinskaya.hw1.view

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.databinding.ActivityAboutBinding

/**
 * Активити с информацией о создателе
 */
class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (binding.aboutActivity.background as AnimationDrawable).apply {
            setEnterFadeDuration(ENTER_FADE_DURATION)
            setExitFadeDuration(EXIT_FADE_DURATION)
            start()
        }
        AnimationUtils.loadAnimation(this, R.anim.moving_text).apply {
            duration = TEXT_DURATION
            repeatCount = Animation.INFINITE
            binding.htmlText.startAnimation(this)
        }
    }

    companion object {
        const val ENTER_FADE_DURATION = 3000
        const val EXIT_FADE_DURATION = 3000
        const val TEXT_DURATION = 3000L
    }
}