package com.example.authorization_data.repozitory

data class User(
    val id: Long,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String,
    val verifiedAccount: Boolean = false
)
