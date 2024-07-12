package com.example.feature_products.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.ripple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.annotation.ExperimentalCoilApi
import com.example.Destination
import com.example.DestinationProperty
import com.example.builder
import com.example.feature_products.R
import com.example.feature_products.events.EventsFilter
import com.example.feature_products.events.EventsProduct
import com.example.feature_products.models.state_products.ViewModelProducts
import com.example.feature_products.ui.components_products_screen.BottomBarShoppingBasket
import com.example.feature_products.ui.components_products_screen.CardProductPreview
import com.example.feature_products.ui.components_products_screen.FilterBottomSheetContent
import com.example.feature_products.ui.components.CustomBottomSheet
import com.example.feature_products.ui.components.ControlRefresh
import com.example.feature_products.ui.components.EmptyItemsMessage
import com.example.feature_products.ui.components.ErrorConnectionMessage
import com.example.feature_products.ui.components.TopBar
import com.example.feature_products.util.ImageLoading
import com.example.feature_products.util.getSingleImageLoader
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalCoilApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ScreenProducts(
    vm: ViewModelProducts,
    drawerState: DrawerState,
    navigate: (DestinationProperty) -> Unit
) {
    val listProducts = vm.products.collectAsLazyPagingItems()
    val filterState = vm.stateFilter.collectAsState()
    val stateBasket = vm.statePropertyBasket.collectAsState(initial = null)
    val coroutine = rememberCoroutineScope()
    val navDrawerOpen = remember(drawerState) {
        { coroutine.launch { drawerState.open() } }
    }

    val visibleBottomSheet = remember {
        mutableStateOf(false)
    }
    val loadServiceDataFilter = remember(vm) {
        { vm.onEventsFilter(EventsFilter.LoadDataFilter) }
    }
    val context = LocalContext.current
    val imageLoaderFilter = remember {
        context.getSingleImageLoader(ImageLoading.InfrequentlyLoading)
    }
    val isRefreshing = rememberSaveable {
        mutableStateOf(true)
    }
    val onRefresh = remember {
        { listProducts.refresh() }
    }

    CustomBottomSheet(
        stateVisibleBottomSheet = visibleBottomSheet,
        onClose = {
            visibleBottomSheet.value =
                false;coroutine.launch { imageLoaderFilter.memoryCache?.clear();imageLoaderFilter.diskCache?.clear() }
        }, heightSheet = 500.dp
    ) { state, boxScope ->
        val onFilter = remember(state) {
            { state.value.close();vm.onEventsFilter(EventsFilter.DataFilter);listProducts.refresh() }
        }
        boxScope.FilterBottomSheetContent(
            filterDataState = filterState,
            stateBottomSheet = state,
            onFilter = onFilter,
            vm::onEventsFilter
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                text = "Shopping Basket",
                onClickNavIcon = { navDrawerOpen(); },
                onClickActionIcon = { visibleBottomSheet.value = true;loadServiceDataFilter() },
                iconNav = Icons.AutoMirrored.Filled.ArrowBack,
                iconAction = ImageVector.vectorResource(
                    id = R.drawable.sort
                )
            )
        }, bottomBar = {
            BottomBarShoppingBasket(
                modifier = Modifier.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
                stateBasket = stateBasket
            )
        }) { paddingValues ->

        val onEventsProduct = remember(vm) {
            { event: EventsProduct -> vm.onEventsProduct(event); }
        }
        PullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                )
                .clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
                .background(Color(243, 245, 246))
        ) {

            CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = paddingValues.calculateBottomPadding()),
                    contentPadding = PaddingValues(
                        top = 26.dp,
                        end = 27.dp,
                        start = 27.dp,
                        bottom = 40.dp,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(17.dp),
                    verticalArrangement = Arrangement.spacedBy(27.dp)
                ) {
                    if (listProducts.loadState.refresh != LoadState.Loading && listProducts.loadState.refresh !is LoadState.Error) {
                        items(
                            count = listProducts.itemCount,
                            key = listProducts.itemKey { it.id }) {
                            listProducts[it]?.let { product ->
                                CardProductPreview(
                                    product = product,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(15.dp))
                                        .width(152.dp)
                                        .height(259.dp)
                                        .clickable(onClick = {
                                            navigate(Destination.ScreenProduct.builder(product.id))
                                        },
                                            indication = ripple(),
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            }),
                                    onClickBtn = { event: EventsProduct ->
                                        onEventsProduct(event)
                                    }
                                )
                            }
                        }
                        item(span = { GridItemSpan(2) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(42.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (listProducts.loadState.append is LoadState.Loading) {
                                    CircularProgressIndicator(color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
            if (listProducts.itemCount == 0 && listProducts.loadState.refresh != LoadState.Loading && listProducts.loadState.refresh !is LoadState.Error) {
                EmptyItemsMessage(
                    isRefreshing = isRefreshing,
                    modifier = Modifier.align(Alignment.Center),
                    message = "No products available"
                )
            }
            if (listProducts.loadState.refresh is LoadState.Error) {
                ErrorConnectionMessage(modifier = Modifier.align(Alignment.Center))
            }
        }
        ControlRefresh(loadState = listProducts.loadState.refresh) {
            isRefreshing.value = it
        }
    }
}





