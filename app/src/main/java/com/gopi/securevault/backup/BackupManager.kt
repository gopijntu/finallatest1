package com.gopi.securevault.backup

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.gopi.securevault.data.db.AppDatabase
import com.gopi.securevault.util.AESUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BackupManager(private val context: Context) {

    private val db = AppDatabase.get(context)

    suspend fun backupDatabase(password: String, destinationUri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                val dbFile = context.getDatabasePath("securevault.db")
                context.contentResolver.openOutputStream(destinationUri)?.use { outputStream ->
                    dbFile.inputStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Backup successful!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Backup failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    suspend fun restoreDatabase(password: String, sourceUri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                val dbFile = context.getDatabasePath("securevault.db")
                context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
                    FileOutputStream(dbFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Restore successful! Please restart the app.", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Restore failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    suspend fun backupToJson(password: String, destinationUri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                val backupData = BackupData(
                    aadhar = db.aadharDao().getAll(),
                    banks = db.bankDao().getAll(),
                    cards = db.cardDao().getAll(),
                    policies = db.policyDao().getAll()
                )
                val json = Gson().toJson(backupData)
                val encryptedJson = AESUtils.encrypt(json, password)

                context.contentResolver.openOutputStream(destinationUri)?.use { outputStream ->
                    outputStream.write(encryptedJson.toByteArray())
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Backup successful!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Backup failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    suspend fun restoreFromJson(password: String, sourceUri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                val encryptedJson = context.contentResolver.openInputStream(sourceUri)?.use {
                    it.bufferedReader().readText()
                } ?: throw Exception("Could not read from file")

                val json = AESUtils.decrypt(encryptedJson, password)
                val backupData = Gson().fromJson(json, BackupData::class.java)

                db.clearAllTablesManually()

                backupData.aadhar.forEach { db.aadharDao().insert(it) }
                backupData.banks.forEach { db.bankDao().insert(it) }
                backupData.cards.forEach { db.cardDao().insert(it) }
                backupData.policies.forEach { db.policyDao().insert(it) }

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Restore successful!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Restore failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getBackupFileName(isJson: Boolean): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return if (isJson) "securevault_backup_$timestamp.vaultbackup" else "securevault_backup_$timestamp.db"
    }
}
