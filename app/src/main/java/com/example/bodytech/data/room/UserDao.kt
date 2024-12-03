package com.example.bodytech.data.room

import androidx.room.*
import com.example.bodytech.model.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getById(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<UserEntity>>

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)
}