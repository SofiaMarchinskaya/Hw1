package com.sofiamarchinskaya.hw1


class Presenter(private val view: View) {
    private val model = Model()

    interface View {
        fun onSaveComplete()
    }

    fun onSave(title: String, text: String) {
        model.onSave(title, text)
        view.onSaveComplete()
    }
}