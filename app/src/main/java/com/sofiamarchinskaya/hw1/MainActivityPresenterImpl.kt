package com.sofiamarchinskaya.hw1

import com.sofiamarchinskaya.hw1.presenters.MainActivityPresenter

class MainActivityPresenterImpl(
    private var view: MainActivityView?
) : MainActivityPresenter {

    override fun aboutOnClick() {
        view?.openAboutScreen()
    }
}