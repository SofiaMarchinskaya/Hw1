package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.SingleLiveEvent

class MainActivityViewModel : ViewModel() {
    val onAboutClickEvent = SingleLiveEvent<Unit>()
    val onWebViewClickEvent = SingleLiveEvent<Unit>()
    val onLocationClickEvent = SingleLiveEvent<Unit>()

    fun onInfoIconClick() {
        onAboutClickEvent.call()
    }

    fun onWebViewIconClick() {
        onWebViewClickEvent.call()
    }

    fun onLocationClick(){
        onLocationClickEvent.call()
    }
}