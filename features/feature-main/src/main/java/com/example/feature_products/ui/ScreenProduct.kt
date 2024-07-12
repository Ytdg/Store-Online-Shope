package com.example.feature_products.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.Destination
import com.example.DestinationProperty
import com.example.builder
import com.example.feature_products.events.EventsProduct
import com.example.feature_products.models.state_products.ViewModelProduct
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ScreenProduct(
    vm: ViewModelProduct = hiltViewModel(),
    navigate: (DestinationProperty) -> Unit,
    endTransition: State<Boolean>
) {
    val paddingBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val paddingTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val stateProductEntire = vm.stateProductEntire.collectAsState()
    val heght = (LocalConfiguration.current.screenHeightDp / 2).dp + paddingBottom
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingTop, bottom = paddingBottom)
            .background(Color.Gray)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(-25.dp)
        ) {
            item {
                Box(modifier = Modifier) {
                    ImagePager(
                        listImagesUri = if (endTransition.value) stateProductEntire.value.images else emptyList<String>().toImmutableList(),
                        modifier = Modifier
                            .fillParentMaxHeight(0.55f)
                            .fillMaxWidth()
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navigate(Destination.ScreenProducts.builder(null)) },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                        stateProductEntire.value.id?.let {
                            IconButton(
                                onClick = {
                                    vm.onEvent(
                                        EventsProduct.SetProductToBasket(
                                            it
                                        )
                                    )
                                },
                                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                            ) {
                                Icon(
                                    imageVector = if (stateProductEntire.value.stateProductToBasket == null) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = if (stateProductEntire.value.stateProductToBasket == null) Color.Black else Color.Red
                                )
                            }
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = heght),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ) {

                    Column(
                        Modifier.padding(start = 27.dp, end = 27.dp, top = 25.dp, bottom = 75.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Column() {
                            Text(
                                text = stateProductEntire.value.title,
                                fontSize = 22.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "$" + stateProductEntire.value.price,
                                fontSize = 24.sp,
                                color = Color(237, 170, 75)
                            )

                        }
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(text = "Description", fontSize = 18.sp, color = Color.Black)
                            Text(
                                fontSize = 15.sp,
                                color = Color.Gray,
                                text = stateProductEntire.value.description
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(60.dp)
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(237, 170, 75))
        ) {
            Text(text = "Buy Now", fontSize = 20.sp, color = Color.White)
        }
    }
    BackHandler {
        navigate(Destination.ScreenProducts.builder(null))
    }

}

@Composable
private fun ImagePager(listImagesUri: ImmutableList<String>, modifier: Modifier) {
    val state = rememberPagerState(pageCount = { listImagesUri.size })
    HorizontalPager(
        state = state,
        modifier = modifier
    ) {
        AsyncImage(
            model = listImagesUri[it],
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}