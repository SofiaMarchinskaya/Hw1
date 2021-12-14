package com.sofiamarchinskaya.hw1.presenters

import com.sofiamarchinskaya.hw1.view.framework.MainActivityView
import com.sofiamarchinskaya.hw1.presenters.framework.MainActivityPresenter

class MainActivityPresenterImpl(
    private var view: MainActivityView?
) : MainActivityPresenter {

    override fun aboutOnClick() {
        view?.openAboutScreen()
    }
}