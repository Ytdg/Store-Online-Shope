package com.example.feature_products.ui.components_products_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.Destination
import com.example.DestinationProperty
import com.example.builder
import com.example.feature_products.R
import okhttp3.internal.immutableListOf

@Composable
fun NavDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    currentDestination: Destination,
    navigate: (DestinationProperty) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(
            modifier = Modifier.width(145.dp),
            drawerContainerColor = Color.Transparent
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(237, 170, 75)), contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(59.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NavDrawer.navDrawerItems.forEach {

                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier.clickable(
                                        interactionSource = null,
                                        indication = ripple(),
                                        onClick = { navigate(it.destination.builder(null)) })
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = it.icon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp),
                                        tint = if (currentDestination == it.destination) Color.White else Color.Unspecified
                                    )
                                }
                                Text(text = it.name)
                            }
                        }
                    }
                }
            }
        }
    }, modifier = modifier) {
        content()
    }
}

data class NavDrawerItem(val destination: Destination, val icon: Int, val name: String)
object NavDrawer {
    val navDrawerItems = immutableListOf(
        NavDrawerItem(
            destination = Destination.ScreenProducts,
            icon = R.drawable.product,
            name = "Products"
        ),
        NavDrawerItem(
            destination = Destination.ScreenProfile,
            icon = R.drawable.user__1__1,
            name = "Profile"
        ),
        NavDrawerItem(
            destination = Destination.Settings,
            icon = R.drawable.settings_1,
            name = "Settings"
        ),
        NavDrawerItem(
            destination = Destination.Messages,
            icon = R.drawable.email,
            name = "Messages"
        )

    )
}

