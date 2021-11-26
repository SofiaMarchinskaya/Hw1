package com.sofiamarchinskaya.hw1

interface MainActivityPresenter {

    fun onSave(title: String, text: String)

    fun onDestroy()

    fun shareOnClick(title: String, text: String)

    fun aboutOnClick()
}