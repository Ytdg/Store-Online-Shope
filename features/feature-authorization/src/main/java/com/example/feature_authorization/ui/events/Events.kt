package com.example.feature_authorization.ui.events

sealed class EventsSignIn {
    data class ChangeEmail(val value: String) : EventsSignIn()
    data class ChangePassword(val value: String) : EventsSignIn()
    data class ConfirmPassword(val value: String) : EventsSignIn()
    object SignIn : EventsSignIn()
}

sealed class EventsLogin {
    data class ChangeEmail(val value: String) : EventsLogin()
    data class ChangePassword(val value: String) : EventsLogin()
    object Login : EventsLogin()
}