package com.example.feature_products.models

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class StateProductPreview(
    val id: Int,
    val title: String,
    val price: String,
    val imagePreviewUri: String,
)

@Immutable
data class StateProductEntire(
    val id: Int? = null,
    val title: String = String(),
    val price: String = String(),
    val description: String = String(),
    val images: ImmutableList<String> = emptyList<String>().toImmutableList(),
    val stateProductToBasket: StateProductToBasket? = null
)

@Immutable
data class StateBasket(
    val countProducts: Int
)

@Immutable
data class StateProductToBasket(
    val idProduct: Int,
    val countProducts: Int = 0
)
