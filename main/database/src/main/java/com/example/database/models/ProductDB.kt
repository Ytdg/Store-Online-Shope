package com.example.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductDB(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val images: List<String>,
)
