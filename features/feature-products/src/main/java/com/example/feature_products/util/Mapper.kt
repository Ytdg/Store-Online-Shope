package com.example.feature_products.util

import com.example.models.FilterData
import com.example.models.ProductPreview
import com.example.feature_products.models.FilterDataState
import com.example.feature_products.models.StateBasket
import com.example.feature_products.models.StateProductEntire
import com.example.feature_products.models.StateProductPreview
import com.example.feature_products.models.StateProductToBasket
import com.example.models.ProductEntire
import com.example.models.ProductToBasket
import kotlinx.collections.immutable.toImmutableList

fun FilterData.toFilterDataState() = FilterDataState(selectedCategoryId, priceMin, priceMax, title)
fun FilterDataState.toFilterData() = FilterData(selectedCategoryId, priceMin, priceMax, title)
fun ProductPreview.toStateProductPreview() = StateProductPreview(id, title, price, imagePreviewUri)
fun ProductToBasket.toStateProductToBasket() = StateProductToBasket(idProduct, count)
fun ProductEntire.toStateProductEnitre() = StateProductEntire(
    id,
    title,
    price,
    description,
    images.toImmutableList(),
    stateProductToBasket = productToBasket?.toStateProductToBasket()
)
fun List<ProductToBasket>.toStatePropertyBasket(): StateBasket {
   val countAll= this.sumOf { it.count }
    return StateBasket(countAll)
}
