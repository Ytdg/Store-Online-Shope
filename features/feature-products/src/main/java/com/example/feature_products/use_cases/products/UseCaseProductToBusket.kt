package com.example.feature_products.use_cases.products

import com.example.api.ApiRepozitoryProduct
import com.example.feature_products.models.StateProductToBasket
import com.example.models.ProductToBasket
import kotlinx.coroutines.flow.first
import javax.inject.Inject

enum class ActionProductToBasket {
    Put, Remove
}

class UseCaseProductToBusket @Inject constructor(private val apiRepozitoryProduct: ApiRepozitoryProduct) {
    suspend operator fun invoke(
        stateProductToBasket: StateProductToBasket,
        actionProductToBasket: ActionProductToBasket
    ) {
        when (actionProductToBasket) {
            ActionProductToBasket.Put -> {
                apiRepozitoryProduct.saveProductToBasket(stateProductToBasket.Put())
            }

            ActionProductToBasket.Remove -> {
                apiRepozitoryProduct.removeProductToBasket(stateProductToBasket.idProduct)
            }
        }
    }

    private suspend fun StateProductToBasket.Put(): ProductToBasket {
        return apiRepozitoryProduct.getProductsToBasket(idProduct).first().firstOrNull()?.let {
            it.copy(count = it.count + 1)
        } ?: ProductToBasket(idProduct, count = 1)
    }
}