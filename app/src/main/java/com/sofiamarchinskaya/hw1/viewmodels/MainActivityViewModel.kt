package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.SingleLiveEvent

class MainActivityViewModel : ViewModel() {
    val onAboutClickEvent = SingleLiveEvent<Unit>()

    fun onInfoIconClick(){
        onAboutClickEvent.call()
    }
}