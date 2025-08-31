package com.gopi.securevault.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banks")
data class BankEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String?,
    val accountNo: String,
    val bankName: String?,
    val ifsc: String?,
    val cifNo: String?,
    val username: String?,
    val profilePrivy: String?,
    val mPin: String?,
    val tPin: String?,
    val notes: String?,
    val privy: String?
)
