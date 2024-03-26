package com.example.test_compose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_share")
data class HistoryShare(
    @PrimaryKey
    val secid : String,
    val shortname : String,
    val count : Int,
    val curPricePerOne : Int
)
