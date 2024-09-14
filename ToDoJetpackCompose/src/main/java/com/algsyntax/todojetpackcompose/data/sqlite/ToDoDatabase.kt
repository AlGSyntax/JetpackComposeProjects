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
                // Generieren oder Abrufen des sicheren Passworts
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
         * Funktion zum Abrufen des Verschlüsselungspassworts.
         * Hier verwenden wir EncryptedSharedPreferences, um das Passwort sicher zu speichern.
         */
        private fun getPassphrase(context: Context): ByteArray {
            // MasterKey für die Verschlüsselung erstellen
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            // EncryptedSharedPreferences erstellen oder öffnen
            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                "secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            // Überprüfen, ob das Passwort bereits gespeichert ist
            var passphraseString = sharedPreferences.getString("db_passphrase", null)

            if (passphraseString == null) {
                // Passwort generieren (z. B. zufällig)
                passphraseString = generateRandomPassphrase()
                // Passwort speichern
                sharedPreferences.edit().putString("db_passphrase", passphraseString).apply()
            }

            // Konvertiert das Passwort in ByteArray
            return SQLiteDatabase.getBytes(passphraseString.toCharArray())
        }

        /**
         * Funktion zum Generieren eines sicheren, zufälligen Passworts.
         */
        private fun generateRandomPassphrase(): String {
            // Äthiopisches Alphabet hinzufügen
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

            // Lateinisches Alphabet und andere Zeichen
            val latinAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            val numbers = "0123456789"
            val specialChars = "!@#\$%^&*()-_=+<>?"

            // Kombinieren aller erlaubten Zeichen
            val allowedChars = ethiopicAlphabet + latinAlphabet + numbers + specialChars

            // Erhöhen der Passphrase-Länge
            val passphraseLength = 64  // Zum Beispiel auf 64 erhöhen

            return (1..passphraseLength)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
}