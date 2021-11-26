package com.sofiamarchinskaya.hw1


class MainActivityPresenterImpl(
    private var view: MainActivityView?,
    private val model: SaveModel
) : MainActivityPresenter {

    override fun onSave(title: String, text: String) {
        if (title.isBlank() || text.isBlank()) {
            view?.onFailed()
            return
        }
        model.onSave(title, text)
        view?.onSaveComplete()
    }

    override fun onDestroy() {
        view = null
    }

    override fun shareOnClick(title: String, text: String) {
        if (title.isBlank() || text.isBlank()) {
            view?.onFailed()
        } else {
            view?.shareNote(title, text)
        }
    }

    override fun aboutOnClick() {
        view?.openAboutScreen()
    }
}