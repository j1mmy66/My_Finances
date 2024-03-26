package com.example.test_compose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_compose.data.models.MyShare


@Database(entities = [MyShare::class], version = 1)
abstract class MyShareDatabase : RoomDatabase(){
    abstract val dao: MyShareDao
}
