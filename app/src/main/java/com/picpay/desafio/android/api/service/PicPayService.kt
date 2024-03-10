package com.picpay.desafio.android.api.service

import com.picpay.desafio.android.api.RetrofitConfiguration
import com.picpay.desafio.android.api.models.User
import retrofit2.Call
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    fun getUsers(): Call<List<User>?>
}