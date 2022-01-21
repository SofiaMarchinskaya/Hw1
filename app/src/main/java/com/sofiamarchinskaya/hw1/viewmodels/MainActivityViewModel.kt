package com.sofiamarchinskaya.hw1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sofiamarchinskaya.hw1.R
import com.sofiamarchinskaya.hw1.states.MenuState
import com.sofiamarchinskaya.hw1.states.MenuStates

class MainActivityViewModel : ViewModel() {
    val menuState = MutableLiveData<MenuState>()

    fun selectMenuItem(itemId:Int) {
        when (itemId) {
            R.id.about -> {
                menuState.value = MenuState(MenuStates.ABOUT)
            }
        }
    }
}