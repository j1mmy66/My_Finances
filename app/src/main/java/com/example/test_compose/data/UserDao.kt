package com.example.test_compose.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.test_compose.data.models.User

@Dao
interface UserDao {
   @Transaction
   suspend fun updateUser(user: User) {
      delete()
      update(user)

   }

   @Upsert
   suspend fun upsert(user: User)

   @Query("Delete from user WHERE id = 0")
   suspend fun delete() {
   }
   @Insert
   suspend fun update(user: User) {

   }



   @Query("SELECT * FROM user LIMIT 1")
   suspend fun getUser(): User?
}