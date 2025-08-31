# Room
-keep class androidx.room.** { *; }

# App classes
-keep class com.gopi.securevault.** { *; }

# SQLCipher
-keep class net.sqlcipher.** { *; }
-keep class org.sqlite.** { *; }
-dontwarn net.sqlcipher.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.concurrent.GuardedBy
