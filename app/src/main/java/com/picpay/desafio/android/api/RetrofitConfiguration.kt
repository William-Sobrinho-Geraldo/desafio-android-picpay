package com.picpay.desafio.android.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.api.service.PicPayService
import com.picpay.desafio.android.utilidades.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitConfiguration {

    companion object {
        private const val baseUrl = Constants.BASE_URL
        private const val CONNECTION_TIMEOUT = 12 * 1000
        private lateinit var retrofit: Retrofit


        private fun getRetrofitInstance(): Retrofit {

            val client: OkHttpClient by lazy {
                OkHttpClient.Builder().addInterceptor { chain ->
                    var newRequest = chain.request().newBuilder().build()
                    chain.proceed(newRequest)
                }.connectTimeout(
                    CONNECTION_TIMEOUT.toLong(),
                    TimeUnit.MINUTES
                ).readTimeout(1, TimeUnit.MINUTES).build()
            }


            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit

        }


        fun <S> createService (serviceClass : Class<S>) : S {
            return  getRetrofitInstance().create(serviceClass)
        }

    }
}

