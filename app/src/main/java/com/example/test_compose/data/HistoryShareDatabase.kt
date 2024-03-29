package com.example.test_compose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_compose.data.models.HistoryShare

@Database(entities = [HistoryShare::class], version = 1)
abstract class HistoryShareDatabase : RoomDatabase(){
    abstract val dao : HistoryShareDao
}