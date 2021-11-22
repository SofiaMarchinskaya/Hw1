package com.sofiamarchinskaya.hw1

interface MainActivityPresenter {
    fun onSave(title : String, text : String)
    fun onDestroy()
}