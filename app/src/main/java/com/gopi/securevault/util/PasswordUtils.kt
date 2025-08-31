package com.gopi.securevault.util

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordUtils {

    private const val ITERATIONS = 10000   // How many times to hash
    private const val KEY_LENGTH = 256     // Output hash length in bits

    fun generateSalt(bytes: Int = 16): String {
        val salt = ByteArray(bytes)
        SecureRandom().nextBytes(salt)
        return Base64.encodeToString(salt, Base64.NO_WRAP)
    }

    fun hashWithSalt(password: String, salt: String): String {
        val spec = PBEKeySpec(
            password.toCharArray(),
            Base64.decode(salt, Base64.NO_WRAP),
            ITERATIONS,
            KEY_LENGTH
        )
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded
        return Base64.encodeToString(hash, Base64.NO_WRAP)
    }

    fun isPasswordValid(password: String): Boolean {
        if (password.length < 8) return false
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecial = password.any { !it.isLetterOrDigit() }
        return hasLetter && hasDigit && hasSpecial
    }
}
