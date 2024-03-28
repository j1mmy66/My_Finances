package com.example.test_compose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    val firstName : String,
    val lastName : String,

    @PrimaryKey
    val id : Int = 0,
)