package com.picpay.desafio.android.di

import com.picpay.desafio.android.api.RetrofitConfiguration
import com.picpay.desafio.android.api.service.PicPayService
import com.picpay.desafio.android.cache.AppDatabase
import com.picpay.desafio.android.repository.Repository
import com.picpay.desafio.android.ui.viewModels.MainActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainActivityViewModel(get()) }

    single { Repository(get(),get()) }

    single { RetrofitConfiguration.createService(PicPayService::class.java) }

    single { AppDatabase.getDatabase(androidContext()).userDao() }
}