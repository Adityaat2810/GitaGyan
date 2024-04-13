package com.example.geetagyan.datasource.room

import androidx.room.TypeConverter
import com.example.geetagyan.models.Commentary
import com.example.geetagyan.models.Translation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppTypeConvertors {
    // we do need to call these function anywhere {room use them whenever non recognizable datatype is found }
    // as mysql store only primitive data types not List
    @TypeConverter
    fun fromListToString(list: List<String> ):String{
        return Gson().toJson(list)
    }

    //string to list to fetch and show
    @TypeConverter
    fun fromStringToList(string: String):List<String>{
        return Gson().fromJson(string,object :TypeToken<List<String>>(){}.type)
    }

    @TypeConverter
    fun fromTransToString(list: List<Translation> ):String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToTrans(string: String):List<Translation>{
        return Gson().fromJson(string,object :TypeToken<List<Translation>>(){}.type)
    }

    @TypeConverter
    fun fromCommentaryToString(list: List<Commentary> ):String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToCommentary(string: String):List<Commentary>{
        return Gson().fromJson(string,object :TypeToken<List<Commentary>>(){}.type)
    }


}