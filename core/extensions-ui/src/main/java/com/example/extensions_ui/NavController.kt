package com.example.extensions_ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NavController.endTransition(time: Long): State<Boolean> {
    val d = this.currentBackStackEntryAsState()
    var stateEndTransition by remember {
        mutableStateOf(false)
    }
    val coroutine = rememberCoroutineScope()
    d.value?.let { backStack ->
        DisposableEffect(key1 = backStack.destination.route) {
            val job = coroutine.launch {
                delay(time)
                stateEndTransition = true
            }
            onDispose {
                job.cancel()
                stateEndTransition = false
            }
        }
    }

    return rememberUpdatedState(newValue = stateEndTransition)
}

