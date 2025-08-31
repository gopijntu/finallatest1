package com.gopi.securevault.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "license")
data class LicenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    val licenseNumber: String?,
    val notes: String?,
    val documentPath: String?
)
