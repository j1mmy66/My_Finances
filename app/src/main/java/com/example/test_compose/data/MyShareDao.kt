package com.example.test_compose.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.test_compose.data.models.MyShare
import kotlinx.coroutines.flow.Flow

@Dao
interface MyShareDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(myShare: MyShare): Long

    @Query("UPDATE my_shares SET count = count + :additionalCount WHERE secid = :secid")
    suspend fun updateCount(secid: String, additionalCount: Int)

    @Transaction
    suspend fun addOrUpdateShare(myShare: MyShare) {
        val insertResult = insert(myShare)
        if (insertResult == -1L) {
            updateCount(myShare.secid, myShare.count)
        }
    }

    @Query("SELECT * FROM my_shares ORDER BY secid ASC")
    fun getShares() : Flow<List<MyShare>>


}