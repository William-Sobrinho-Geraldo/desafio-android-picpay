package com.picpay.desafio.android.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationDesafioAndroid : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
        startKoin {
            androidContext(this@ApplicationDesafioAndroid)
            modules(appModule)
        }

    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
            private set
    }
}