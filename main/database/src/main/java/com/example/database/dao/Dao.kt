package com.example.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.database.models.ProductToBasketDB
import com.example.database.models.ProductDB
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoProductsCashed {
    @Upsert
    suspend fun instertProducts(listProductDB: List<ProductDB>)

    @Query("SELECT * FROM ProductDB")
    fun getProducts(): PagingSource<Int, ProductDB>

    @Query("DELETE FROM ProductDB")
    suspend fun clear()
}

@Dao
interface DaoProduct {
    @Query("SELECT * FROM ProductDB where id=:id")
    suspend fun getProduct(id: Int): ProductDB

    @Upsert
    suspend fun saveProductToBasket(favouriteProductDB: ProductToBasketDB)

    @Query("SELECT * FROM ProductToBasketDB where idProduct =  :id OR :id IS NULL")
   fun getProductToBasket(id: Int?): Flow<List<ProductToBasketDB>>


    @Query("DELETE FROM ProductToBasketDB WHERE idProduct = :id")
    suspend fun removeFavouriteProduct(id: Int)
}