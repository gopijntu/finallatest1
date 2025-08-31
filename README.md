# SecureOfflineVault-Kotlin

A fully **offline** Android app in **Kotlin** with **Room + SQLCipher** encryption, password-based login,
two security questions for reset, and CRUD screens for Banks (with stubs for Cards/Policies/Aadhar).

## Highlights
- Kotlin + AndroidX + Material (XML + ViewBinding)
- **No INTERNET permission** (offline only)
- Room DB encrypted with **SQLCipher** (via `SupportFactory`)
- Master password **hashed + salted** (EncryptedSharedPreferences)
- First-time setup: set password + 2 security Q&As
- Login + Password reset via security Q&As
- Home screen with 4 sections; Banks fully implemented with CRUD & RecyclerView
- Backup/Restore DB locally (simple copy)

## Build (tested on Android Studio Jellyfish/Koala)
1. Open Android Studio → **Open** the folder.
2. Let Gradle sync. (Ensure JDK 21 in `File > Settings > Build, Execution, Deployment > Gradle`)
3. Build > Make Project, then Run on device.

> If build fails on `SQLCipher` ABI filters, ensure your device ABI is included in `ndk.abiFilters` in `app/build.gradle.kts`.

## SQLCipher Notes
- We use `net.sqlcipher:android-database-sqlcipher:4.5.4` and wrap Room with `SupportFactory` in `AppDatabase`.
- The encryption key is derived from the **stored master hash**; change to PBKDF2 for stronger keys if you want.

### If encryption cannot be used
- Remove the `.openHelperFactory(factory)` line in `AppDatabase` builder to use plaintext Room.
- Dependencies to add (already present):
  ```kotlin
  implementation("net.zetetic:android-database-sqlcipher:4.5.4")
  implementation("androidx.sqlite:sqlite:2.4.0")
  ```

## Backup/Restore
- **Backup** copies `securevault.db` to your app external files dir.
- **Restore** lets you pick a file and replaces the DB. Restart app afterwards.

## Extend
- Implement CRUD like `BanksActivity` for Cards/Policies/Aadhar with their DAOs.
- Add validation, better UI, and export-to-Excel if needed.

