package com.example.damian.countries.rest

import com.example.damian.countries.App
import com.example.damian.countries.utils.hasNetwork
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://restcountries.eu"
const val cacheSize = (5 * 1024 * 1024).toLong()

val myCache = Cache(App.applicationContext().cacheDir, cacheSize)

val gson: Gson by lazy {
    GsonBuilder()
            .setPrettyPrinting()
            .create()
}

val rest: RestInterface by lazy {
    Retrofit.Builder()
            .apply {
                addConverterFactory(GsonConverterFactory.create(gson))
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                baseUrl(BASE_URL)
                client(okHttpClient)
            }.build()
            .create(RestInterface::class.java)
}

val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(App.applicationContext())) {
                request.newBuilder().header("Cache-Control", "public, max-age=" + 60 * 60 * 16).build()
            } else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 15).build()

            chain.proceed(request)
        }
        .build()