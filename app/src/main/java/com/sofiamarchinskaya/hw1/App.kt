package com.sofiamarchinskaya.hw1

import android.app.Application
import com.sofiamarchinskaya.hw1.models.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
        AppDatabase.createDataBase(applicationContext)
    }
}