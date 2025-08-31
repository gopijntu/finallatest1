package com.gopi.securevault.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "voter_id")
data class VoterIdEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    val voterIdNumber: String?,
    val notes: String?,
    val documentPath: String?
)
