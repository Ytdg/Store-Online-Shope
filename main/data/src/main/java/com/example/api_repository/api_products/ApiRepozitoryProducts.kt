package com.example.api_repository.api_products

import androidx.paging.PagingData
import com.example.models.Category
import com.example.models.FilterData
import com.example.models.ProductPreview
import com.example.data.products.ResponseResult
import kotlinx.coroutines.flow.Flow


interface  ApiRepozitoryProducts: GeneralApiProduct {
    fun getAll(): Flow<PagingData<ProductPreview>>
    fun filterData(filterData: FilterData)
    suspend fun getCategory(): ResponseResult<List<Category>>
    fun clearDataFilter()
}