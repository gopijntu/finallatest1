package com.gopi.securevault.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gopi.securevault.databinding.ActivityLoginBinding
import com.gopi.securevault.ui.home.HomeActivity
import com.gopi.securevault.util.CryptoPrefs
import com.gopi.securevault.util.PasswordUtils

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: CryptoPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = CryptoPrefs(this)
        val isSetup = prefs.getBoolean("is_setup_done", false)
        if (!isSetup) {
            startActivity(Intent(this, SetupActivity::class.java))
            finish()
            return
        }

        binding.btnLogin.setOnClickListener {
            val input = binding.etPassword.text.toString()
            val salt = prefs.getString("salt", "") ?: ""
            val stored = prefs.getString("master_hash", "") ?: ""

            val hash = PasswordUtils.hashWithSalt(input, salt)
            if (hash == stored) {
                val uname = prefs.getString("user_name", "User")
                Toast.makeText(this, "Welcome $uname", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvForgot.setOnClickListener {
            // basic reset via security questions
            startActivity(Intent(this, PasswordResetActivity::class.java))
        }
    }
}
