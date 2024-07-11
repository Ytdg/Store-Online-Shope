package com.example.autorizationapi.models

class UserDAP(
    val id: Long,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String,
    val verifiedAccount: Boolean = false
)