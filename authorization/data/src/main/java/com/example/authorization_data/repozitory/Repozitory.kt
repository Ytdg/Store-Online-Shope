package com.example.authorization_data.repozitory

import androidx.room.withTransaction
import com.example.autorizationapi.AutorizationApi
import com.example.autorizationapi.models.Response
import com.example.database.DataBaseLocalUser
import java.net.HttpURLConnection
import javax.inject.Inject


enum class TypeAuthotization {
    SignIn, Login
}

class Repozitory @Inject constructor(
    private val api: AutorizationApi,
    private val dataBaseLocalUser: DataBaseLocalUser
) {
    private val dao = dataBaseLocalUser.daoLocalUserReg

    suspend fun  requestSignUser():Boolean{
        val user= dataBaseLocalUser.daoLocalUserReg.getData() ?: return false
        return true
    }
    suspend fun requestAuthorization(
        user: User,
        typeAuthorization: TypeAuthotization
    ): ResponseResult {
        try {
            val response =
                if (typeAuthorization == TypeAuthotization.SignIn) api.signIn(user.toUserDAP()) else api.logIn(
                    user.toUserDAP()
                )
            val resultResponse =
                if (typeAuthorization == TypeAuthotization.SignIn) ProcessResponse.responseSignIn(
                    response
                ) else ProcessResponse.responseLogin(response)

            dataBaseLocalUser.withTransaction {
                dao.clearData()
                if (resultResponse is ResponseResult.Successfully<*>) {
                    response.totalResult?.let { userDAP ->
                        dao.updateData(
                            userDAP.toUserDb()
                        )
                    }
                }
            }
            return resultResponse
        } catch (ex: Exception) {
            return ResponseResult.Error("An error has occurred")
        }
    }
}

internal object ProcessResponse {
    fun <T> responseSignIn(response: Response<T>): ResponseResult {
        when (response.statusCode) {
            HttpURLConnection.HTTP_BAD_REQUEST -> {
                return ResponseResult.Error(message = "There is already such a user")
            }

            HttpURLConnection.HTTP_OK -> {
                return ResponseResult.Successfully(data = response.totalResult)
            }
        }
        return ResponseResult.Error(message = "Unknown server error")
    }

    fun <T> responseLogin(response: Response<T>): ResponseResult {
        when (response.statusCode) {
            HttpURLConnection.HTTP_BAD_REQUEST -> {
                return ResponseResult.Error(message = "No such user has been found")
            }

            HttpURLConnection.HTTP_OK -> {
                return ResponseResult.Successfully(data = response.totalResult)
            }

            470 -> {
                return ResponseResult.Error(
                    message = "Incorrect password or email",
                )
            }
        }
        return ResponseResult.Error(message = "Unknown server error")
    }

}

sealed class ResponseResult {
    data class Successfully<T>(val data: T?, val message: String? = null) : ResponseResult()
    data class Error(val message: String) : ResponseResult()
}