package fi.metropolia.marathon.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class MarathonDatabase: RoomDatabase() {
    //abstract fun requestDao(): RequestDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: MarathonDatabase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MarathonDatabase::class.java,
            "Marathon.db"
        ).build()
    }
}