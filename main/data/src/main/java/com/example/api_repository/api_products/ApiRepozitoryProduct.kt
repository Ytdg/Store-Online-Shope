package com.example.api_repository.api_products

import com.example.models.ProductEntire
import kotlinx.coroutines.flow.Flow


interface ApiRepozitoryProduct: GeneralApiProduct {
     fun getProductEntire(id: Int): Flow<ProductEntire>
    suspend fun removeProductToBasket(id: Int)
}