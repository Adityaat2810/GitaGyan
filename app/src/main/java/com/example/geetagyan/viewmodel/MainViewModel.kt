package com.example.geetagyan.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geetagyan.models.ChaptersItem
import com.example.geetagyan.models.VersesItem
import com.example.geetagyan.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MainViewModel:ViewModel() {
    val appRepository = ApiRepository()
    fun getAllChapters(): Flow<List<ChaptersItem>> = appRepository.getAllChapters()

    fun getVerses(chapterNumber: Int): Flow<List<VersesItem>> =
        appRepository.getVerses(chapterNumber)


    fun getParticularVerse(chapterNumber: Int,verseNumber: Int):Flow<VersesItem> = appRepository.getParticularVerse(chapterNumber,verseNumber)
}