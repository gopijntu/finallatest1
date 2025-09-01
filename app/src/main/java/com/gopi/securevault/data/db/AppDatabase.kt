package com.gopi.securevault.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gopi.securevault.data.dao.*
import com.gopi.securevault.data.entities.*
import net.sqlcipher.database.SupportFactory
import net.sqlcipher.database.SQLiteDatabase
import com.gopi.securevault.util.CryptoPrefs

@Database(
    entities = [BankEntity::class, CardEntity::class, PolicyEntity::class, AadharEntity::class, PanEntity::class, VoterIdEntity::class, LicenseEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bankDao(): BankDao
    abstract fun cardDao(): CardDao
    abstract fun policyDao(): PolicyDao
    abstract fun aadharDao(): AadharDao
    abstract fun panDao(): PanDao
    abstract fun voterIdDao(): VoterIdDao
    abstract fun licenseDao(): LicenseDao

    suspend fun clearAllTablesManually() {
        // This is not a safe operation, so we will clear it.
    }

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val prefs = CryptoPrefs(context)

                // Derive an encryption key from stored hashed master password
                val passphrase: CharArray = (prefs.getString("master_hash", null) ?: "fallback-key").toCharArray()

                val factory = SupportFactory(SQLiteDatabase.getBytes(passphrase))

                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "securevault.db"
                )
                    .openHelperFactory(factory)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = inst
                inst
            }
        }
    }
}
