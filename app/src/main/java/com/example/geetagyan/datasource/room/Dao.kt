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
    fun getParticularChapter(chapterNumber: Int): LiveData<savedChapters>
}

@Dao
interface savedVersesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerseInenglish(verseInEnglish:savedVerses)

    @Query("SELECT * from savedVerses")
    fun getSavedVerses():LiveData<List<savedVerses>>

    @Query("SELECT *  FROM savedVerses WHERE chapter_number= :chapterNumber AND verse_number=:verseNumber")
    fun getParticularVerseFromRoom(chapterNumber: Int,verseNumber: Int):LiveData<savedVerses>

    @Query("DELETE from savedVerses WHERE chapter_number=:chapterNumber AND verse_number=:verseNumber")
    fun deleteParticularVerse(chapterNumber: Int,verseNumber:Int)
}