package com.sofiamarchinskaya.hw1


class MainActivityPresenterImp(
    private val view: MainActivityView,
    private val model: SaveModel
) : MainActivityPresenter {
    override fun onSave(title: String, text: String) {
        if (title.isBlank() || text.isBlank()) {
            view.onFailed()
            return
        }
        model.onSave(title, text)
        view.onSaveComplete()
    }
}