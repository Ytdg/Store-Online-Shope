package com.example.feature_authorization.ui.navigate

import androidx.compose.runtime.Immutable

@Immutable
sealed class Destination(val route: String) {

    object ScreenSign : Destination(route = "sign/")

    object ScreenLogin : Destination(route = "login/")

}
