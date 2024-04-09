package com.example.geetagyan.repository

import com.example.geetagyan.datasource.api.ApiUtilities
import com.example.geetagyan.models.ChaptersItem
import com.example.geetagyan.models.VersesItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiRepository {
    /*
    REPO(get ,fetch ,save logic)

    M(actual data provider)   v(xml)    LiveData/flows{used to show data to views from view models}      VM(data from repo to vm )

     */

    /*
    data dlow
    m -> repo -> view models(like services )
     */

    fun getAllChapters(): Flow<List<ChaptersItem>> = callbackFlow {
        val callback = object :Callback<List<ChaptersItem>>{
            override fun onResponse(
                call: Call<List<ChaptersItem>>,
                response: Response<List<ChaptersItem>>
            ) {
                if(response.isSuccessful && response.body() != null){
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<List<ChaptersItem>>, t: Throwable) {
                close(t)
            }

        }

        ApiUtilities.api.getAllChapters().enqueue(callback)
        awaitClose{}  // app will crash without this
    }

    fun getVerses(chapterNumber:Int):Flow<List<VersesItem>> = callbackFlow {
        val  callback =object :Callback<List<VersesItem>>{
            override fun onResponse(
                call: Call<List<VersesItem>>,
                response: Response<List<VersesItem>>
            ) {
                //TODO("Not yet implemented")

                if(response.isSuccessful && response.body() != null){
                    trySend(response.body()!!)
                    close()
                }

            }

            override fun onFailure(call: Call<List<VersesItem>>, t: Throwable) {
                //
                close(t)
            }

        }
        ApiUtilities.api.getVerses(chapterNumber).enqueue(callback)
        awaitClose{}  // app will crash without this
    }
}