package com.example.feature_authorization.utils

object ValidationData{
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

    fun isValidEmail(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) {
            return false
        }
        return true
    }
    fun allData (password: String,email: String,confirmPassword:String):Boolean{
        if(isValidPassword(password)&&(password==confirmPassword)&&isValidEmail(email)){
            return true
        }
        return false
    }
}