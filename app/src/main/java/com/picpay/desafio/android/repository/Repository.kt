package com.picpay.desafio.android.repository

import com.picpay.desafio.android.api.RetrofitConfiguration
import com.picpay.desafio.android.api.service.PicPayService

class Repository (private val service: PicPayService) {

//    private val service = RetrofitConfiguration.createService(PicPayService::class.java)

    fun getUsers() = service.getUsers()

}