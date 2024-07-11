package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.database.dao.DaoProduct
import com.example.database.dao.DaoProductsCashed
import com.example.database.models.ProductToBasketDB
import com.example.database.models.ProductDB
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Database(
    entities = [ProductDB::class, ProductToBasketDB::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class DataBaseProducts : RoomDatabase() {
    abstract val daoProductsCashed: DaoProductsCashed
    abstract val daoProduct: DaoProduct
}

internal class Converters {
    @TypeConverter
    fun fromList(value: List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)
}

fun dataBaseProductsBuilder(applicationContext: Context): DataBaseProducts {
   return Room.databaseBuilder(applicationContext, DataBaseProducts::class.java, "Products").build()
}