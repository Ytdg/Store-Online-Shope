package com.example.autorizationapi

import com.example.autorizationapi.models.Response
import com.example.autorizationapi.models.UserDAP
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AutorizationApi{
    @POST("/registration/sign_in")
    suspend fun signIn( @Body userDAP: UserDAP): Response<UserDAP>

    @POST("/registration/log_in")
    suspend fun logIn( @Body userDAP: UserDAP): Response<UserDAP>
}

fun buildApi(baseUrl:String):AutorizationApi{
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build().create(AutorizationApi::class.java)
}