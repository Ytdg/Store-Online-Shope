package com.example.data.products

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.map
import com.example.models.Category
import com.example.models.ProductToBasket
import com.example.models.FilterData
import com.example.models.ProductPreview
import com.example.api_network.products.ApiProducts
import com.example.api_repository.api_products.ApiRepozitoryProduct
import com.example.api_repository.api_products.ApiRepozitoryProducts
import com.example.database.DataBaseProducts
import com.example.database.models.ProductDB
import com.example.core.extentions.toCategory
import com.example.core.extentions.toProductDB
import com.example.core.extentions.toProductEntire
import com.example.core.extentions.toProductPreview
import com.example.core.extentions.toProductToBasket
import com.example.core.extentions.toProductToBasketDB
import com.example.models.ProductEntire
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

sealed class ResponseResult<T> {
    data class Successfully<T>(val data: T?, val message: String? = null) : ResponseResult<T>()
    data class Error<K>(val message: String) : ResponseResult<K>()
}

class RepozitoryProducts @Inject constructor(
    private val dataBaseProducts: DataBaseProducts,
    private val apiProducts: ApiProducts
) : ApiRepozitoryProduct, ApiRepozitoryProducts {

    @ExperimentalPagingApi
    override fun getAll(): Flow<PagingData<ProductPreview>> {
        val products = BuildRemoteMediator(
            pagingSourceFactory = { dataBaseProducts.daoProductsCashed.getProducts() },
            remoteMediator = ProductsRemoteMediator(
                dataBaseProducts = dataBaseProducts,
                apiProducts = apiProducts,
            )
        ).build().map { pagingData ->
            pagingData.map {
                it.toProductPreview()
            }
        }
        return products
    }

    override fun filterData(filterData: FilterData) {
        Configuration.dataFilter = filterData
    }

    override suspend fun getCategory(): ResponseResult<List<Category>> {
        try {
            val responseResult = ResponseResult.Successfully(
                data = apiProducts.getCategories().map { it.toCategory() })
            return responseResult
        } catch (ex: Exception) {
            return ResponseResult.Error(message = "Failed to upload")
        }
    }

    override fun clearDataFilter() {
        Configuration.dataFilter = null
    }

    override  fun getProductEntire(id: Int): Flow<ProductEntire> {
          return  dataBaseProducts.daoProduct.getProductToBasket(id)
                .map {
                    dataBaseProducts.daoProduct.getProduct(id = id)
                        .toProductEntire(it.firstOrNull()?.toProductToBasket())
                }
    }


    override suspend fun saveProductToBasket(productToBasket: ProductToBasket) {
        dataBaseProducts.daoProduct.saveProductToBasket(productToBasket.toProductToBasketDB())
    }

    override fun getProductsToBasket(id: Int?): Flow<List<ProductToBasket>> {
        return dataBaseProducts.daoProduct.getProductToBasket(id)
            .map { it.map { it.toProductToBasket() } }
    }

    override suspend fun removeProductToBasket(id: Int) {
        dataBaseProducts.daoProduct.removeFavouriteProduct(id)
    }
}

internal object Configuration {
    var offset: Int = 0
    val limit: Int = 6
    var dataFilter: FilterData? = null
}

internal class BuildRemoteMediator<Key : Any, T : Any> @OptIn(ExperimentalPagingApi::class) constructor(
    private val pagingSourceFactory: () -> PagingSource<Key, T>,
    private val remoteMediator: RemoteMediator<Key, T>
) {
    @OptIn(ExperimentalPagingApi::class)
    fun build(): Flow<PagingData<T>> {
        val products: Flow<PagingData<T>> = Pager(
            config = PagingConfig(
                pageSize = 6
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = remoteMediator
        ).flow

        return products

    }
}

@OptIn(ExperimentalPagingApi::class)
internal class ProductsRemoteMediator(
    private val dataBaseProducts: DataBaseProducts,
    private val apiProducts: ApiProducts,
) :
    RemoteMediator<Int, ProductDB>() {


    override suspend fun initialize(): InitializeAction {
        Log.d("?x", "init")
        dataBaseProducts.daoProductsCashed.clear()
        return super.initialize()
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductDB>
    ): MediatorResult {
        with(Configuration) {

            when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("?x", "refresh")
                    offset = 0
                }

                LoadType.PREPEND -> {
                    Log.d("?x", "prepend")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    delay(1000)
                    Log.d("?x", "append")
                    offset += limit
                }
            }
            try {
                if (loadType == LoadType.REFRESH) {
                    dataBaseProducts.daoProductsCashed.clear()
                }
                val products = if (dataFilter != null) {
                    apiProducts.GetProducts(
                        numPage = offset,
                        limit = limit,
                        title = dataFilter!!.title,
                        priceMin = dataFilter!!.priceMin,
                        priceMax = dataFilter!!.priceMax,
                        category = dataFilter!!.selectedCategoryId
                    )
                } else {
                    apiProducts.GetProducts(
                        numPage = offset,
                        limit = limit
                    )
                }

                val endOfPagination = products.isEmpty()
                dataBaseProducts.daoProductsCashed.instertProducts(products.map {
                    it.toProductDB()
                })

                return MediatorResult.Success(endOfPaginationReached = endOfPagination)
            } catch (error: Exception) {
                return MediatorResult.Error(error)
            }
        }
    }
}

