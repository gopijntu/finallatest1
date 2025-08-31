package com.gopi.securevault.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bankName: String?,
    val cardType: String?,
    val cardNumber: String?,
    val cvv: String?,
    val validTill: String?,
    val customerId: String?,
    val pin: String?,
    val notes: String?
)
