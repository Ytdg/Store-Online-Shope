package com.example.storeonline.init

import com.example.database.DataBaseLocalUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LocalUserReg @Inject constructor(val dataBaseLocalUser: DataBaseLocalUser) {

    fun requestLocalUser(): Boolean {
        var res = false
        runBlocking {
            val localUser = dataBaseLocalUser.daoLocalUserReg.getData()
            localUser?.let { res = true }
        }
        return res
    }
}