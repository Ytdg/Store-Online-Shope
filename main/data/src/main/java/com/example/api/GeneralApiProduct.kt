package com.example.api

import com.example.models.ProductToBasket
import  kotlinx.coroutines.flow.Flow

interface GeneralApiProduct {
    suspend fun saveProductToBasket(productToBasket: ProductToBasket)
    fun getProductsToBasket(id: Int?): Flow<List<ProductToBasket>>
}