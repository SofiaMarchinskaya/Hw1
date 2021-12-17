package com.sofiamarchinskaya.hw1

import android.app.Application
import com.sofiamarchinskaya.hw1.models.database.AppDatabase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.createDataBase(applicationContext)
    }
}