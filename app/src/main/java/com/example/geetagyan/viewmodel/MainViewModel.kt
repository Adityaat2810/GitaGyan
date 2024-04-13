package com.example.geetagyan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.geetagyan.datasource.room.AppDatabase
import com.example.geetagyan.datasource.room.savedChapters
import com.example.geetagyan.datasource.room.savedVerses
import com.example.geetagyan.datasource.room.savedVersesDao
import com.example.geetagyan.models.ChaptersItem
import com.example.geetagyan.models.VersesItem
import com.example.geetagyan.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MainViewModel(application: Application):AndroidViewModel(application) {
    val savedChaptersDao =AppDatabase.getDatabaseInstance(application)?.savedChaptersDao()
    val savedVersesDao =AppDatabase.getDatabaseInstance(application)?.savedVersesDao()

    val appRepository = ApiRepository(savedChaptersDao!!,savedVersesDao!!)
    fun getAllChapters(): Flow<List<ChaptersItem>> = appRepository.getAllChapters()

    fun getVerses(chapterNumber: Int): Flow<List<VersesItem>> =
        appRepository.getVerses(chapterNumber)


    fun getParticularVerse(chapterNumber: Int,verseNumber: Int):Flow<VersesItem> = appRepository.getParticularVerse(chapterNumber,verseNumber)

    //savedchapters
    suspend fun insertChapters(savedChapters: savedChapters) =appRepository.insertChapters(savedChapters)

    fun getSavedChapters(): LiveData<List<savedChapters>> =appRepository.getSavedChapters()

    fun getParticularChapter(chapterNumber: Int): LiveData<savedChapters> =appRepository.getParticularChapter(chapterNumber)

    //savedVerse
    suspend fun insertVerseInenglish(verseInEnglish: savedVerses) = appRepository.insertVerseInenglish(verseInEnglish)
    fun getSavedVerses():LiveData<List<savedVerses>> =appRepository.getSavedVerses()

    fun getParticularVerseFromRoom(chapterNumber: Int,verseNumber: Int):LiveData<savedVerses> = appRepository.getParticularVerseFromRoom(chapterNumber,verseNumber)

    fun deleteParticularVerse(chapterNumber: Int,verseNumber:Int) =appRepository.deleteParticularVerse(chapterNumber,verseNumber)


}