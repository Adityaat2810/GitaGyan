package com.example.geetagyan.datasource.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [savedChapters::class], version = 1, exportSchema = false)
@TypeConverters(AppTypeConvertors::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun savedChaptersDao():savedChaptersDao

    companion object{
        @Volatile
        var INSTANCE:AppDatabase? =null

        fun getDatabaseInstance(context: Context):AppDatabase?{
            val tempInstance  = INSTANCE
            if(INSTANCE != null){
                return tempInstance
            }

            synchronized(this) {
                val roomDb = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "AppDatabase"
                ).fallbackToDestructiveMigration().build()
                     //fallbackToDestructiveMigration()  => when room db migrate it delete prev data
                INSTANCE=roomDb
                return roomDb
            }

        }

        //
    }
}
