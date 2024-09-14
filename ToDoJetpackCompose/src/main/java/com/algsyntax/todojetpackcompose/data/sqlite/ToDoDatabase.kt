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
         * Encryption password retrieval function. Here we use EncryptedSharedPreferences to securely store the password.
         */
        private fun getPassphrase(context: Context): ByteArray {
            // Create MasterKey for encryption
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            // Create or open EncryptedSharedPreferences
            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                "secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            // Check whether the password is already saved
            var passphraseString = sharedPreferences.getString("db_passphrase", null)

            if (passphraseString == null) {
                // Generate a new password if not found
                passphraseString = generateRandomPassphrase()
                // Saving password to SharedPreferences
                sharedPreferences.edit().putString("db_passphrase", passphraseString).apply()
            }

           // Converts the password to ByteArray
            return SQLiteDatabase.getBytes(passphraseString.toCharArray())
        }

        /**
         * Funktion zum Generieren eines sicheren, zufälligen Passworts.
         */
        private fun generateRandomPassphrase(): String {
            // Add Ethiopian alphabet
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

            // Latin alphabet and other characters
            val latinAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            val numbers = "0123456789"
            val specialChars = "!@#\$%^&*()-_=+<>?"

            // Combining all allowed characters
            val allowedChars = ethiopicAlphabet + latinAlphabet + numbers + specialChars

            // Increasing the passphrase length
            val passphraseLength = 64 // For example increase to 64

            return (1..passphraseLength)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
}