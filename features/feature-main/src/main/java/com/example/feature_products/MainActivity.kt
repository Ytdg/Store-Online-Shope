package com.example.feature_products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.Destination
import com.example.DestinationProperty
import com.example.builder
import com.example.extensions_ui.endTransition
import com.example.feature_products.ui.ScreenProduct
import com.example.feature_products.ui.ScreenProducts
import com.example.feature_products.ui.ScreenProfile
import com.example.feature_products.ui.components_products_screen.NavDrawer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            NavScreen()
        }
    }

    @Composable
    fun NavScreen() {
        val navController = rememberNavController()
        val endNavTransihion = navController.endTransition(time = 900)
        val navDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val navigate = remember {
            { destinationProperty: DestinationProperty ->
                navController.onNavigate(destinationProperty)
            }
        }
        NavHost(
            navController = navController,
            startDestination = Destination.ScreenProducts.baseRoute,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(350)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(350)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(350)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(350)
                )
            }
        ) {
            composable(route = Destination.ScreenProfile.baseRoute) {
                ScreenProfile()
            }
            composable(route = Destination.ScreenProducts.baseRoute) {
                NavDrawer(
                    drawerState = navDrawerState,
                    currentDestination = Destination.ScreenProducts,
                    navigate = { navigate(it) }
                ) {
                    ScreenProducts(
                        vm = hiltViewModel(),
                        drawerState = navDrawerState,
                        navigate = navigate,
                    )
                }
            }
            composable(route = Destination.ScreenProduct.baseRoute + "{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )) {
                ScreenProduct(navigate = navigate, endTransition = endNavTransihion)
            }
        }
    }
}

fun NavController.onNavigate(destinationProperty: DestinationProperty) {

    when (destinationProperty.destination) {
        is Destination.ScreenProducts -> {
            if (currentDestination!!.route != Destination.ScreenProducts.baseRoute) {
                popBackStack()
            }
        }

        is Destination.ScreenProduct -> {
            if (currentDestination!!.route != Destination.ScreenProduct.baseRoute + "{id}") {
                navigate(destinationProperty.endRoute)
            }
        }

        is Destination.ScreenProfile -> {
            if (currentDestination!!.route != Destination.ScreenProfile.baseRoute) {
                navigate(destinationProperty.endRoute)
            }
        }

        else -> {
            if (currentDestination!!.route != Destination.ScreenProfile.baseRoute) {
                navigate(destinationProperty.endRoute)
            }
        }
    }
}

