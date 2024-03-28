package com.example.test_compose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_compose.data.models.User
@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase(){
    abstract val dao : UserDao
}