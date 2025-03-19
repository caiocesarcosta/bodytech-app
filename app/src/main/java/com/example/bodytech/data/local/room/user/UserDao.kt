package com.example.bodytech.data.local.room

import androidx.room.*
import com.example.bodytech.model.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getById(userId: String): Flow<User?>

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User): Int
}