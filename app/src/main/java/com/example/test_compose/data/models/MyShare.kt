package com.example.test_compose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_shares")
data class MyShare(
    @PrimaryKey
    val secid : String,
    val count : Int,
    val moneySpend : Double,
    val curPricePerOne : Int
)
