package com.example.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductToBasketDB(
    @PrimaryKey(autoGenerate = false)
    val idProduct:Int,
    val countProducts:Int
)