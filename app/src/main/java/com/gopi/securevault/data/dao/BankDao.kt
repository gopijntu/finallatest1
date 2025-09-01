package com.gopi.securevault.data.dao

import androidx.room.*
import com.gopi.securevault.data.entities.BankEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BankDao {
    @Query("SELECT * FROM banks ORDER BY id DESC")
    fun observeAll(): Flow<List<BankEntity>>

    @Query("SELECT * FROM banks")
    suspend fun getAll(): List<BankEntity>

    @Insert
    suspend fun insert(entity: BankEntity)

    @Update
    suspend fun update(entity: BankEntity)

    @Delete
    suspend fun delete(entity: BankEntity)
}
