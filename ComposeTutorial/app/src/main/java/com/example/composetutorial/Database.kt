package com.example.composetutorial

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import java.security.AccessControlContext

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE username LIKE :user LIMIT 1")
    fun findByName(user: String): User

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun findUserById(uid: Int) : User

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun findLiveUserById(uid: Int) : LiveData<User>

    @Upsert
    suspend fun upsertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDefaultUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}

@Database(
    entities = [User::class],
    version = 4
)

abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: UserDatabase? = null

        fun getDatabase(applicationContext: Context): UserDatabase {
            return Instance?: synchronized(this){
                Room.databaseBuilder(
                    applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance= it }
            }
        }
    }
}