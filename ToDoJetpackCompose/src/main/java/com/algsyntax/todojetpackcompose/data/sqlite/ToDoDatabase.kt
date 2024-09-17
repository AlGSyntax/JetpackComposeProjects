package com.algsyntax.todojetpackcompose.data.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.algsyntax.jetpackcomposetodo.data.model.Task
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

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
            // Create a MasterKey for encryption using AES256_GCM specification
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            // Create or open EncryptedSharedPreferences with the specified encryption schemes
            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                "secret_shared_prefs", // Name of the SharedPreferences file
                masterKey, // MasterKey for encrypting the data
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // Encryption scheme for keys
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // Encryption scheme for values
            )

            // Attempt to retrieve the existing passphrase from EncryptedSharedPreferences
            var passphraseString = sharedPreferences.getString("db_passphrase", null)

            if (passphraseString == null) {
                // Generate a new passphrase if one does not already exist
                passphraseString = generateRandomPassphrase()
                // Save the new passphrase securely in EncryptedSharedPreferences
                sharedPreferences.edit().putString("db_passphrase", passphraseString).apply()
            }

            // Convert the passphrase to a ByteArray for use with SQLCipher
            return SQLiteDatabase.getBytes(passphraseString.toCharArray())
        }

        /**
         * Generates a secure, random passphrase for database encryption.
         *
         * This function creates a passphrase with high entropy by including a wide range of characters,
         * such as the Ethiopian alphabet, Latin letters, numbers, and special symbols. The increased
         * length and character variety enhance security against brute-force attacks.
         *
         * @return A randomly generated passphrase as a String.
         */
        private fun generateRandomPassphrase(): String {
            // Ethiopian alphabet characters
            val ethiopicAlphabet = "ሀሁሂሃሄህሆሇለሉሊላሌልሎሏ" +
                    "መሙሚማሜምሞሟሠሡሢሣሤሥሦሧ" +
                    "ረሩሪራሬርሮሯሰሱሲሳሴስሶሷ" +
                    "ሸሹሺሻሼሽሾሿቀቁቂቃቄቅቆቇ" +
                    "በቡቢባቤብቦቧቨቩቪቫቬቭቮቯ" +
                    "ተቱቲታቴትቶቷቸቹቺቻቼችቾቿ" +
                    "ኀኁኂኃኄኅኆኇነኑኒናኔንኖኗ" +
                    "ኘኙኚኛኜኝኞኟአኡኢኣኤእኦኧ" +
                    "ከኩኪካኬክኮኯኸኹኺኻኼኽኾ኿" +
                    "ወዉዊዋዌውዎዏዐዑዒዓዔዕዖ዗" +
                    "ዘዙዚዛዜዝዞዟዠዡዢዣዤዥዦዧ" +
                    "የዩዪያዬይዮዯደዱዲዳዴድዶዷ" +
                    "ጀጁጂጃጄጅጆጇገጉጊጋጌግጎጏ" +
                    "ጠጡጢጣጤጥጦጧጨጩጪጫጬጭጮጯ" +
                    "ጰጱጲጳጴጵጶጷጸጹጺጻጼጽጾጿ" +
                    "ፀፁፂፃፄፅፆፇፈፉፊፋፌፍፎፏ" +
                    "ፐፑፒፓፔፕፖፗ"

            // Latin alphabet characters and other symbols
            val latinAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            val numbers = "0123456789"
            val specialChars = "!@#\$%^&*()-_=+<>?"

            // Combine all allowed characters into a single string
            val allowedChars = ethiopicAlphabet + latinAlphabet + numbers + specialChars

            // Set the passphrase length to 64 characters for enhanced security
            val passphraseLength = 64

            // Generate the random passphrase using the allowed characters
            return (1..passphraseLength)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
}