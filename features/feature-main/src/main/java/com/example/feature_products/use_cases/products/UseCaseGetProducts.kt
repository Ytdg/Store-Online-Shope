package com.example.feature_products.use_cases.products

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.api.ApiRepozitoryProducts
import com.example.feature_products.models.StateProductPreview
import com.example.feature_products.util.toStateProductPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UseCaseGetProducts @Inject constructor(val apiRepozitoryProducts: ApiRepozitoryProducts) {
    operator fun invoke(): Flow<PagingData<StateProductPreview>> {
        return apiRepozitoryProducts.getAll()
            .map { pagingData -> pagingData.map { it.toStateProductPreview() } }
    }
}