package com.example.feature_products.models.state_products

import androidx.compose.runtime.Immutable
import com.example.models.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


object ValuePlaceHolderFilter {
    const val priceMin = 1.toString()
    const val priceMax = 1000.toString()
}

enum class TypeLoadDataFilter {
    Nothing, Load, Error, Successfull
}
@Immutable
data class FilterDataState(
    val selectedCategoryId: Int? = null,
    val priceMin: String? = null,
    val priceMax: String? = null,
    val title: String? = null,
    val load: TypeLoadDataFilter = TypeLoadDataFilter.Nothing,
    val listCategory: ImmutableList<Category> = emptyList<Category>().toImmutableList()
)
