package com.example.geetagyan.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geetagyan.models.ChaptersItem
import com.example.geetagyan.repository.ApiRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel:ViewModel() {
    val appRepository =ApiRepository()
    fun getAllChapters(): Flow<List<ChaptersItem>> = appRepository.getAllChapters()
}