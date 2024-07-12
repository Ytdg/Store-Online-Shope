package com.example.feature_products.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
 fun EmptyItemsMessage(
    isRefreshing: State<Boolean>,
    modifier: Modifier,
    message: String
) {
    if (!isRefreshing.value) {
        Text(text = message, color = Color.Gray, fontSize = 16.sp, modifier = modifier)
    }
}
