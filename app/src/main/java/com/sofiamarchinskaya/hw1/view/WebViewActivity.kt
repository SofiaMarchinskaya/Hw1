package com.sofiamarchinskaya.hw1.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofiamarchinskaya.hw1.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.settings.apply {
            javaScriptEnabled = true
            loadsImagesAutomatically = true
        }
        binding.webView.loadUrl(WEATHER_URL)
    }

    companion object {
        private const val WEATHER_URL = "https://www.gismeteo.ru"
    }
}