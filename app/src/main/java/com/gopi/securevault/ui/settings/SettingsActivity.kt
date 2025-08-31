package com.gopi.securevault.ui.settings

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gopi.securevault.R
import com.gopi.securevault.ui.auth.ChangePasswordActivity
import com.gopi.securevault.ui.backup.BackupRestoreActivity
import net.sqlcipher.database.SQLiteDatabase
import java.io.File

import com.gopi.securevault.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnBackupRestore.setOnClickListener {
            Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
        }

       // binding.btnChangePassword.setOnClickListener {
         //   startActivity(Intent(this, ChangePasswordActivity::class.java))
        //}
    }

}
