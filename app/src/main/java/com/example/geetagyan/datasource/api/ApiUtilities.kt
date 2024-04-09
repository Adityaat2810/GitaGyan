package com.example.geetagyan.datasource.api

import com.example.geetagyan.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {

    /*
    * use interceptor(we can make ome modification in our request just before
    * doing it ) to reduce code lines as we don not want to write same Headers
    * above every request
    */

    val headers = mapOf<String,String>(
        "Accept" to "application/json",
        "X-RapidAPI-Key" to BuildConfig.API_KEY,
        "X-RapidAPI-Host" to "bhagavad-gita3.p.rapidapi.com"
    )

    val client = OkHttpClient.Builder().apply {
        addInterceptor{chain ->
            val newRequest = chain.request().newBuilder().apply {
                headers.forEach { key, value -> addHeader(key,value)  }
            }.build()

            chain.proceed(newRequest)
        }
    }.build()

    val api: ApiInterface by lazy{
        Retrofit.Builder()
            .baseUrl("https://bhagavad-gita3.p.rapidapi.com")
            .client(client)  // modified  req with header
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}