package com.gopi.securevault.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pan")
data class PanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    val notes: String?,
    val documentPath: String?
)
