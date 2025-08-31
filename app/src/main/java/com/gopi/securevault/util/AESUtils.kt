package com.gopi.securevault.util

import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import android.util.Base64
import java.security.SecureRandom

object AESUtils {

    private const val ALGORITHM = "AES/CBC/PKCS7Padding"
    private const val KEY_ALGORITHM = "PBKDF2WithHmacSHA1"
    private const val ITERATION_COUNT = 10000
    private const val KEY_LENGTH = 256

    fun encrypt(plainText: String, password: String): String {
        val salt = generateSalt()
        val keySpec = PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH)
        val secretKeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM)
        val secretKey = secretKeyFactory.generateSecret(keySpec)
        val secretKeySpec = SecretKeySpec(secretKey.encoded, "AES")

        val iv = generateIv()
        val ivParameterSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        val saltBase64 = Base64.encodeToString(salt, Base64.NO_WRAP)
        val ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP)
        val encryptedBase64 = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)

        return "$saltBase64:$ivBase64:$encryptedBase64"
    }

    fun decrypt(encryptedText: String, password: String): String {
        val parts = encryptedText.split(":")
        val salt = Base64.decode(parts[0], Base64.NO_WRAP)
        val iv = Base64.decode(parts[1], Base64.NO_WRAP)
        val encryptedBytes = Base64.decode(parts[2], Base64.NO_WRAP)

        val keySpec = PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH)
        val secretKeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM)
        val secretKey = secretKeyFactory.generateSecret(keySpec)
        val secretKeySpec = SecretKeySpec(secretKey.encoded, "AES")

        val ivParameterSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return String(decryptedBytes, Charsets.UTF_8)
    }

    private fun generateSalt(bytes: Int = 16): ByteArray {
        val salt = ByteArray(bytes)
        SecureRandom().nextBytes(salt)
        return salt
    }

    private fun generateIv(bytes: Int = 16): ByteArray {
        val iv = ByteArray(bytes)
        SecureRandom().nextBytes(iv)
        return iv
    }
}
