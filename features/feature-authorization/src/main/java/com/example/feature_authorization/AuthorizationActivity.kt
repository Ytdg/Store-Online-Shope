package com.example.feature_authorization

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.extensions_ui.endTransition
import com.example.feature_authorization.ui.MainScreen
import com.example.feature_authorization.ui.models.ViewModelAuthorization
import com.example.feature_authorization.ui.navigate.Destination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthorizationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Main()
        }
    }

    @Composable
    fun Main() {
        val vm: ViewModelAuthorization = hiltViewModel()
        val navController = rememberNavController()
        val endNavTransihion = navController.endTransition(time = 450).value
        Log.d("pp{]", endNavTransihion.toString())
        NavHost(
            navController = navController,
            startDestination = Destination.ScreenLogin.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }) {
            composable(
                route = Destination.ScreenLogin.route
            ) {
                MainScreen(
                    destination = Destination.ScreenLogin,
                    navHostController = navController,
                    viewModel = vm,
                    endNavTransition = endNavTransihion
                )
            }
            composable(route = Destination.ScreenSign.route) {
                MainScreen(
                    destination = Destination.ScreenSign,
                    navHostController = navController,
                    viewModel = vm,
                    endNavTransition = endNavTransihion
                )
            }
        }
    }
}

