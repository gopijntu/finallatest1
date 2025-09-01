package com.gopi.securevault.backup

import com.gopi.securevault.data.entities.AadharEntity
import com.gopi.securevault.data.entities.BankEntity
import com.gopi.securevault.data.entities.CardEntity
import com.gopi.securevault.data.entities.PolicyEntity

data class BackupData(
    val aadhar: List<AadharEntity>,
    val banks: List<BankEntity>,
    val cards: List<CardEntity>,
    val policies: List<PolicyEntity>
)
