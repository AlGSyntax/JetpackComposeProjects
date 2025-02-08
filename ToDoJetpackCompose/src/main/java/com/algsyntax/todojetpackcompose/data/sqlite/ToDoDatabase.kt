package com.algsyntax.todojetpackcompose.data.sqlite

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKey.Builder
import androidx.security.crypto.MasterKey.KeyScheme
import com.algsyntax.jetpackcomposetodo.data.model.Task
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.security.SecureRandom

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    // Abstract method that provides the ToDoDao for accessing the database.
    abstract val toDoDao: ToDoDao

    companion object {
        // Instance of ToDoDatabase for Singleton purposes.
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        /**
         * Returns the Singleton instance of ToDoDatabase.
         *
         * @param context: The application context, needed for database creation.
         * @return: The Singleton instance of ToDoDatabase.
         */
        fun getDatabase(context: Context): ToDoDatabase {
            return INSTANCE ?: synchronized(this) {
                //Generate or retrieve the secure password
                val passphrase: ByteArray = getPassphrase(context)
                val factory = SupportFactory(passphrase)

                // Building the Room database with SQLCipher SupportFactory
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "encrypted_todo.db"
                )
                    .openHelperFactory(factory)
                    .build()
                INSTANCE = instance
                instance
            }
        }



        /**
         * Retrieves or generates the encryption passphrase for the encrypted SQLite database.
         *
         * This function ensures that the encryption passphrase is securely generated,
         * stored, and retrieved using Android's security libraries. It uses `MasterKey`
         * and `EncryptedSharedPreferences` to prevent unauthorized access to the passphrase.
         *
         * @param context The application context, required for accessing Android-specific features.
         * @return The passphrase as a ByteArray, necessary for initializing the encrypted database.
         */
        private fun getPassphrase(context: Context): ByteArray {
            // Erzeuge den MasterKey
            val masterKey = generateMasterKey(context)

            // Erstelle oder öffne EncryptedSharedPreferences mit dem MasterKey
            val sharedPreferences = createEncryptedSharedPreferences(context, masterKey)

            // Versuche, die bestehende Passphrase abzurufen
            var passphraseString = sharedPreferences.getString("db_passphrase", null)

            if (passphraseString == null) {
                // Generiere eine neue Passphrase, wenn keine vorhanden ist
                passphraseString = generateRandomPassphrase()
                // Speichere die neue Passphrase sicher in EncryptedSharedPreferences
                sharedPreferences.edit().putString("db_passphrase", passphraseString).apply()
            }

            // Konvertiere die Passphrase in ein ByteArray für SQLCipher
            return SQLiteDatabase.getBytes(passphraseString.toCharArray())
        }





        /**
         * Generates a secure, random passphrase for database encryption.
         *
         * This function creates a passphrase with high entropy by including a wide range of characters,
         * such as the Latin letters, numbers, and special symbols. The increased
         * length and character variety enhance security against brute-force attacks.
         *
         * @return A randomly generated passphrase as a String.
         */
        private fun generateRandomPassphrase(): String {




            // Latin alphabet characters and other symbols
            val latinAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            val numbers = "0123456789"
            val specialChars = "!@#\$%^&*()-_=+<>?"
            val random = SecureRandom()




            // Combine all allowed characters into a single string
            val allowedChars = latinAlphabet + numbers + specialChars

            // Set the passphrase length to 4096 characters for enhanced security
            val passphraseLength = 4096

            // Generate the random passphrase using the allowed characters
            return (1..passphraseLength)
                .map { allowedChars[random.nextInt(allowedChars.length)] }
                .joinToString("")
        }



        /**
         * Generates the MasterKey with the hardware-protected Keystore.
         *
         * @param context The application context.
         * @return The generated MasterKey.
         */
        private fun generateMasterKey(context: Context): MasterKey {
            return Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(KeyScheme.AES256_GCM)
                .build()

        }


        /**
         * Creates EncryptedSharedPreferences with the given MasterKey.
         *
         * @param context The application context.
         * @param masterKey The MasterKey for encrypting the SharedPreferences.
         * @return The instance of EncryptedSharedPreferences.
         */
        private fun createEncryptedSharedPreferences(context: Context, masterKey: MasterKey): SharedPreferences {
            return EncryptedSharedPreferences.create(
                context,
                "secret_shared_prefs", // Name of the SharedPreferences file
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // Encryption scheme for keys
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // Encryption scheme for values
            )
        }

    }

}