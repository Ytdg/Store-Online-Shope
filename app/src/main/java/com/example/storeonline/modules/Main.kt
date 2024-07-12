package com.example.storeonline.modules

import android.content.Context
import com.example.api_network.products.ApiProducts
import com.example.api_repository.api_products.ApiRepozitoryProduct
import com.example.api_repository.api_products.ApiRepozitoryProducts
import com.example.api_network.products.builderApiProducts
import com.example.data.products.RepozitoryProducts
import com.example.database.DataBaseProducts
import com.example.database.dataBaseProductsBuilder
import com.example.storeonline.API_KEY_PRODUCTS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object main {

    @Provides
    @Singleton
     fun apiProducts(): ApiProducts {
        return builderApiProducts(baseUrl = API_KEY_PRODUCTS)
    }

    @Provides
    @Singleton
     fun dataBaseProduct(@ApplicationContext context: Context): DataBaseProducts {
        return dataBaseProductsBuilder(context)
    }

    @Provides
    @Singleton
    fun apiReposzitoryProducts(repozitoryProducts: RepozitoryProducts): ApiRepozitoryProducts {
        return  repozitoryProducts
    }
    @Provides
    @Singleton
    fun apiReposzitoryProduct(repozitoryProducts: RepozitoryProducts): ApiRepozitoryProduct {
        return  repozitoryProducts
    }

}