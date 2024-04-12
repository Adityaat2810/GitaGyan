package com.example.geetagyan.datasource.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertors {
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
}