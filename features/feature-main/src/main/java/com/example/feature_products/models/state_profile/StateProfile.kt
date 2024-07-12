package com.example.feature_products.models.state_profile

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class StateProfile(
    val photo: Uri = Uri.EMPTY,
    val name: String = "Unknown",
    val phoneNumber: String = String(),
    val email: String = String(),
    val direction: String = String()
)