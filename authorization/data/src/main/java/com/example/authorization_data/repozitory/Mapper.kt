package com.example.authorization_data.repozitory

import com.example.autorizationapi.models.UserDAP
import com.example.model.LocalUserDb


fun  User.toUserDAP():UserDAP{
    return  UserDAP(id, password, firstName, lastName, email, verifiedAccount)
}
fun UserDAP.toUserDb():LocalUserDb{

    return  LocalUserDb(id, email, password, firstName)
}