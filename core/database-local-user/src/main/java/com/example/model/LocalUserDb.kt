package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalUserDb(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val email: String,
    val password: String,
    val name: String? = null
)