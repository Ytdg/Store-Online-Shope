package com.example.storeonline.modules

import android.content.Context
import com.example.autorizationapi.AutorizationApi
import com.example.autorizationapi.buildApi
import com.example.database.DataBaseLocalUser
import com.example.database.databaseBuilder
import com.example.storeonline.API_KEY_AUTHORISATION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object authorization{

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): DataBaseLocalUser {
        return databaseBuilder(applicationContext = context)
    }

    @Provides
    @Singleton
    fun authorizationApi(): AutorizationApi {
        return buildApi(baseUrl = API_KEY_AUTHORISATION)
    }
}