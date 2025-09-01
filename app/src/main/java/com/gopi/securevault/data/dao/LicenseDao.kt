package com.gopi.securevault.data.dao

import androidx.room.*
import com.gopi.securevault.data.entities.LicenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LicenseDao {
    @Query("SELECT * FROM license")
    fun observeAll(): Flow<List<LicenseEntity>>

    @Insert
    suspend fun insert(entity: LicenseEntity)

    @Update
    suspend fun update(entity: LicenseEntity)

    @Delete
    suspend fun delete(entity: LicenseEntity)
}
