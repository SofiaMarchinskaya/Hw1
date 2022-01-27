package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.states.MainMenuStates
import com.sofiamarchinskaya.hw1.states.MenuState

class MainActivityViewModel : ViewModel() {
    val menuState = MutableLiveData<MenuState>()

    fun onInfoIconClick(){
        menuState.value = MenuState(MainMenuStates.ABOUT)
    }
}