package com.example.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import com.example.model.LocalUserDb

@Database(
    entities = [LocalUserDb::class],
    version = 1
)

abstract class DataBaseLocalUser : RoomDatabase() {
    abstract val daoLocalUserReg: DaoAutorization
}

fun databaseBuilder(applicationContext: Context): DataBaseLocalUser {
    return Room.databaseBuilder(
        applicationContext,
        DataBaseLocalUser::class.java,
        "TableAuthorization"
    ).build()
}

@Dao
interface DaoAutorization {

    @Upsert
    suspend fun updateData(localUserDb: LocalUserDb)

    @Query("SELECT * FROM LocalUserDb")
    suspend fun getData(): LocalUserDb?

    @Query("DELETE FROM LocalUserDb")
    suspend fun clearData()


}
