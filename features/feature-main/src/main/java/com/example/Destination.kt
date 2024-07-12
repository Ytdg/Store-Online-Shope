package com.example

import androidx.compose.runtime.Immutable

@Immutable
sealed class Destination(var baseRoute: String) {

    object ScreenProduct : Destination(baseRoute = "product/")

    object ScreenProducts : Destination(baseRoute = "products/")

    object ScreenProfile : Destination(baseRoute = "profile/")

    object Settings : Destination(baseRoute = "settings/")

    object Messages : Destination(baseRoute = "messages/")
}

@Immutable
data class DestinationProperty(
    val destination: Destination,
    val endRoute: String
)

fun <T> Destination.builder(arg: T?): DestinationProperty {
    return if (arg == null) {
        DestinationProperty(destination = this, endRoute = baseRoute)
    } else {
        DestinationProperty(destination = this, endRoute = this.baseRoute + "$arg")
    }
}





