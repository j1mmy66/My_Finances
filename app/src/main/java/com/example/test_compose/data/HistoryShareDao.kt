package com.example.test_compose.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.test_compose.data.models.HistoryShare
import com.example.test_compose.data.models.MyShare
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryShareDao {
    @Insert
    suspend fun insert(historyShare: HistoryShare)

    @Query("SELECT * FROM history_share ORDER BY id DESC")
    fun getShares() : Flow<List<HistoryShare>>
}