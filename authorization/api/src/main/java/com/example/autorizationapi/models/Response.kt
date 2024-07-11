package com.example.autorizationapi.models
class Response<T>(
    val message:String,
    val statusCode:Int,
    val totalResult:T?
)