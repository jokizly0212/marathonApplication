package fi.metropolia.marathon.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM User WHERE userName = :userName")
    suspend fun getUser(userName: String): User?

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()
}