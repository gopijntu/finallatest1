package com.gopi.securevault.data.dao

import androidx.room.*
import com.gopi.securevault.data.entities.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM cards ORDER BY id DESC")
    fun observeAll(): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards")
    suspend fun getAll(): List<CardEntity>

    @Insert
    suspend fun insert(entity: CardEntity)

    @Update
    suspend fun update(entity: CardEntity)

    @Delete
    suspend fun delete(entity: CardEntity)
}
