package com.example.geetagyan.datasource.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface savedChaptersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(savedChapters:savedChapters)

    @Query("SELECT * from savedChapters")
    fun getSavedChapters():LiveData<List<savedChapters>>

    @Query("DELETE FROM savedChapters WHERE id=:id")
    fun deleteParticularChapter(id:Int)

    @Query("SELECT * from savedChapters WHERE chapter_number=:chapterNumber")
    fun getParticularChapter(chapterNumber:Int)
}