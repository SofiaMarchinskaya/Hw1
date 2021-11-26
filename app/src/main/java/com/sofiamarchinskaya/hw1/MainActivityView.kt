package com.sofiamarchinskaya.hw1

interface MainActivityView {

    fun onSaveComplete()

    fun onFailed()

    fun openAboutScreen()

    fun shareNote(title: String, text: String)
}