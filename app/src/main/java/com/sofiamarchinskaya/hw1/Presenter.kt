package com.sofiamarchinskaya.hw1


class Presenter(private val view: View) {
    private val model = Model()

    interface View {
        fun onSaveComplete()
        fun onFailed()
    }

    fun onSave(title: String, text: String) {
        if (title.isBlank() || text.isBlank()) {
            view.onFailed()
            return
        }
        model.onSave(title, text)
        view.onSaveComplete()
    }
}