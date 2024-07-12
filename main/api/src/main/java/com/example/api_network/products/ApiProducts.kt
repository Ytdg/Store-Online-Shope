package com.example.api_network.products

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiProducts {
    @GET("products")
    suspend fun GetProducts(
        @Query("offset") numPage: Int,
        @Query("limit") limit: Int,
        @Query("title") title: String? = null,
        @Query("price_min") priceMin: String? = null,
        @Query("price_max") priceMax: String? = null,
        @Query("categoryId") category: Int? = null,
    ): List<ProductDAP>

    @GET("categories")
    suspend fun getCategories(): List<CategoryDAP>
}

fun builderApiProducts(baseUrl: String): ApiProducts {

    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build().create(ApiProducts::class.java)

}