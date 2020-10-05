package com.example.bnettask.web

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WebRepo {

    companion object {

        private const val TOKEN = "Ri50Qeh-C0-OROVPmu"
        private const val URL = "https://bnet.i-partner.ru/testAPI/"

        private val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        private val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
                    .addHeader("token", TOKEN)
                    .build()
                return@Interceptor chain.proceed(builder)
            })
            .build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val createApi: ApiService = retrofit.create(ApiService::class.java)
    }
}