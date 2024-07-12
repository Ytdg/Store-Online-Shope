package com.example.api_repository.api_products

import com.example.models.ProductToBasket
import  kotlinx.coroutines.flow.Flow

interface GeneralApiProduct {
    suspend fun saveProductToBasket(productToBasket: ProductToBasket)
    fun getProductsToBasket(id: Int?): Flow<List<ProductToBasket>>
}