package com.gopi.securevault

import android.app.Application
import net.sqlcipher.database.SQLiteDatabase

class SecureApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize SQLCipher once
        SQLiteDatabase.loadLibs(this)


        // Suppress SQLCipher verbose warnings
        System.setProperty("net.sqlcipher.database.VERBOSE", "false")
    }
}
