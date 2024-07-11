package com.example.api

import androidx.paging.PagingData
import com.example.models.Category
import com.example.models.FilterData
import com.example.models.ProductPreview
import com.example.data.ResponseResult
import kotlinx.coroutines.flow.Flow


interface  ApiRepozitoryProducts:GeneralApiProduct{
    fun getAll(): Flow<PagingData<ProductPreview>>
    fun filterData(filterData: FilterData)
    suspend fun getCategory():ResponseResult<List<Category>>
    fun clearDataFilter()
}