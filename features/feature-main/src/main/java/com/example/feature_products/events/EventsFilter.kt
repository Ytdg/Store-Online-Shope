package com.example.feature_products.events

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
 sealed class EventsFilter {
    data class SetCategory(val id: Int) : EventsFilter()
    data class SetTitle(val title: String) : EventsFilter()
    data class SetPriceMin(val priceMin: String) : EventsFilter()
    data class SetPriceMax(val priceMax: String) : EventsFilter()
    object  ClearDataFilter: EventsFilter()
    object  DataFilter:EventsFilter()
    object  LoadDataFilter:EventsFilter()
}

