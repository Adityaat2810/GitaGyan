package com.example.geetagyan.datasource.api

import com.example.geetagyan.BuildConfig
import com.example.geetagyan.models.ChaptersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {
    @Headers(
        "Accept:application/json",
        "X-RapidAPI-Key:${BuildConfig.API_KEY}",
        "X-RapidAPI-Host:bhagavad-gita3.p.rapidapi.com"
    )
    @GET("/v2/chapters/")
    fun getAllChapters(): Call<List<ChaptersItem>>
}