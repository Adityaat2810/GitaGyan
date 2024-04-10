package com.example.geetagyan.datasource.api

import com.example.geetagyan.BuildConfig
import com.example.geetagyan.models.ChaptersItem
import com.example.geetagyan.models.VersesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {
    // first point where we fetch data from api

    // getting headers from interceptor in ApiUtilities
    @GET("/v2/chapters/")
    fun getAllChapters(): Call<List<ChaptersItem>>

    @GET("/v2/chapters/{chapterNumber}/verses/")
    fun getVerses(@Path("chapterNumber") chapterNumber:Int):Call<List<VersesItem>>

    @GET("v2/chapters/{chapterNumber}/verses/{verseNumber}/")
    fun getParticularVerse(
        @Path("chapterNumber") chapterNumber: Int,
        @Path("verseNumber") verseNumber:Int
    ):Call<VersesItem>


}