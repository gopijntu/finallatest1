package com.gopi.securevault.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "policies")
data class PolicyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    val amount: String?,
    val company: String?,
    val nextPremiumDate: String?,
    val premiumValue: String?,
    val maturityValue: String?,
    val notes: String?
)
