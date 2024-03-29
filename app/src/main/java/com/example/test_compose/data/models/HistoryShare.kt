package com.example.test_compose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_share")
data class HistoryShare(

    val secid : String,
    val count : Int,
    val curPricePerOne : Double,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
)
