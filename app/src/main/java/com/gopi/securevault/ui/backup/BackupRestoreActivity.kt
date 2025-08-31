package com.gopi.securevault.ui.backup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gopi.securevault.backup.BackupManager
import com.gopi.securevault.databinding.ActivityBackupRestoreBinding
import com.gopi.securevault.R
import com.gopi.securevault.util.CryptoPrefs
import com.gopi.securevault.util.PasswordUtils
import kotlinx.coroutines.launch

class BackupRestoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBackupRestoreBinding
    private lateinit var backupManager: BackupManager
    private lateinit var prefs: CryptoPrefs

    private val backupDbLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.also { uri ->
                showPasswordDialog { password ->
                    lifecycleScope.launch {
                        backupManager.backupDatabase(password, uri)
                    }
                }
            }
        }
    }

    private val restoreDbLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.also { uri ->
                showPasswordDialog { password ->
                    lifecycleScope.launch {
                        backupManager.restoreDatabase(password, uri)
                    }
                }
            }
        }
    }

    private val backupJsonLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.also { uri ->
                showPasswordDialog { password ->
                    lifecycleScope.launch {
                        backupManager.backupToJson(password, uri)
                    }
                }
            }
        }
    }

    private val restoreJsonLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.also { uri ->
                showPasswordDialog { password ->
                    lifecycleScope.launch {
                        backupManager.restoreFromJson(password, uri)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBackupRestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        backupManager = BackupManager(this)
        prefs = CryptoPrefs(this)

        binding.btnBackup.setOnClickListener {
            showBackupOptions()
        }

        binding.btnRestore.setOnClickListener {
            showRestoreOptions()
        }
    }

    private fun showBackupOptions() {
        val options = arrayOf("Backup as .db file", "Backup as .vaultbackup file")
        AlertDialog.Builder(this)
            .setTitle("Choose Backup Format")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openFilePickerForDbBackup()
                    1 -> openFilePickerForJsonBackup()
                }
            }
            .show()
    }

    private fun showRestoreOptions() {
        val options = arrayOf("Restore from .db file", "Restore from .vaultbackup file")
        AlertDialog.Builder(this)
            .setTitle("Choose Restore Source")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openFilePickerForDbRestore()
                    1 -> openFilePickerForJsonRestore()
                }
            }
            .show()
    }

    private fun showPasswordDialog(onPasswordEntered: (String) -> Unit) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_password_prompt, null)
        val etPassword = dialogView.findViewById<android.widget.EditText>(R.id.etPassword)

        AlertDialog.Builder(this)
            .setTitle("Enter Master Password")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val password = etPassword.text.toString()
                if (password.isNotEmpty()) {
                    val salt = prefs.getString("salt", null)
                    val hash = prefs.getString("master_hash", null)
                    if (salt != null && hash != null && PasswordUtils.hashWithSalt(password, salt) == hash) {
                        onPasswordEntered(password)
                    } else {
                        Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Password required!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun openFilePickerForDbBackup() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/octet-stream"
            putExtra(Intent.EXTRA_TITLE, backupManager.getBackupFileName(false))
        }
        backupDbLauncher.launch(intent)
    }

    private fun openFilePickerForDbRestore() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        restoreDbLauncher.launch(intent)
    }

    private fun openFilePickerForJsonBackup() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/octet-stream"
            putExtra(Intent.EXTRA_TITLE, backupManager.getBackupFileName(true))
        }
        backupJsonLauncher.launch(intent)
    }

    private fun openFilePickerForJsonRestore() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        restoreJsonLauncher.launch(intent)
    }
}
