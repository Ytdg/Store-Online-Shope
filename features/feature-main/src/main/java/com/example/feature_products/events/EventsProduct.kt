package com.example.feature_products.events

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
sealed class EventsProduct{
    data class SetProductToBasket(val id: Int) : EventsProduct()
}