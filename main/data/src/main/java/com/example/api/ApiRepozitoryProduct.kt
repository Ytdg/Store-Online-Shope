package com.example.api

import com.example.models.ProductEntire
import com.example.models.ProductToBasket
import kotlinx.coroutines.flow.Flow


interface ApiRepozitoryProduct:GeneralApiProduct {
     fun getProductEntire(id: Int): Flow<ProductEntire>
    suspend fun removeProductToBasket(id: Int)
}