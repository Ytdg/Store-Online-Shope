package com.example.feature_products.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.LoadState
import kotlinx.coroutines.delay

@Composable
fun ControlRefresh(loadState: LoadState, change: (Boolean) -> Unit) {
    when (loadState) {
        is LoadState.Loading -> {
            change(true)
        }

        is LoadState.NotLoading -> {
            LaunchedEffect(key1 = true) {
                delay(200)
                change(false)
            }
        }

        is LoadState.Error -> {
            LaunchedEffect(key1 = true) {
                delay(200)
                change(false)
            }
        }
    }
}