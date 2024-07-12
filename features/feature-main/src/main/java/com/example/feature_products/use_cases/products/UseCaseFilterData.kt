package com.example.feature_products.use_cases.products

import com.example.models.FilterData
import com.example.api_repository.api_products.ApiRepozitoryProducts
import com.example.feature_products.models.state_products.FilterDataState
import com.example.feature_products.models.state_products.ValuePlaceHolderFilter
import com.example.feature_products.util.toFilterData
import javax.inject.Inject

class UseCaseFilterData @Inject constructor(val apiRepozitoryProducts: ApiRepozitoryProducts) {
    operator fun invoke(filterDataState: FilterDataState) {
        apiRepozitoryProducts.filterData(filterData =filterDataState.defineValuesDataFilter())
    }

    private fun FilterDataState.defineValuesDataFilter(): FilterData {
        var filterData = this.toFilterData()
        with(filterData) {
            if (priceMin == null && priceMax != null) {
                filterData = filterData.copy(priceMin = ValuePlaceHolderFilter.priceMin)
            }
            if (priceMin != null && priceMax == null) {
                filterData = filterData.copy(priceMax = ValuePlaceHolderFilter.priceMax)
            }
            title?.let {
                if (it.isBlank()) {
                    filterData=filterData.copy(title = null)
                }
            }
        }
        return  filterData
    }
}