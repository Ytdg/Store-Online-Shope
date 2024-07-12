package com.example.feature_products.ui.components_products_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.ripple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.example.feature_products.events.EventsFilter
import com.example.feature_products.models.state_products.FilterDataState
import com.example.feature_products.models.state_products.TypeLoadDataFilter
import com.example.feature_products.ui.components.BottomSheetState
import com.example.feature_products.ui.components.BottomSheetVisible
import com.example.feature_products.ui.components.ImageItemUri
import com.example.feature_products.util.ImageLoading
import com.example.storeonline.ui.products.components.filter.components.TextFieldTitleFilter

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
 fun BoxScope.FilterBottomSheetContent(
    filterDataState: State<FilterDataState>,
    stateBottomSheet: State<BottomSheetState>,
    onFilter: () -> Unit,
    onEventsFilter: (EventsFilter) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item(key = 1) {
            Box(Modifier.fillMaxWidth()) {
                Text(
                    text = "Filter",
                    fontSize = 28.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 5.dp),
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { onEventsFilter(EventsFilter.ClearDataFilter) },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }

        if (stateBottomSheet.value.bottomSheetVisible == BottomSheetVisible.Expand) {
            val offsetItems = 20.dp
            val containerColumn =
                @Composable { modifier: Modifier, content: @Composable () -> Unit ->
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(
                            modifier = modifier,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            content()
                        }
                        Divider()
                    }
                }
            val textNameFilter = @Composable { modifier: Modifier, text: String ->
                Text(
                    text = text,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                )
            }
            if (filterDataState.value.load == TypeLoadDataFilter.Load) {
                item(key = -1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Gray)
                    }
                }
            }
            if (filterDataState.value.load != TypeLoadDataFilter.Load) {
                item(key = 2) {
                    if (filterDataState.value.listCategory.isNotEmpty()) {
                        containerColumn(Modifier) {
                            textNameFilter(Modifier.offset(x = offsetItems), "Category")
                            CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                                val modifierImageUri = remember {
                                    Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(Color.Gray)
                                }

                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = offsetItems),
                                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    items(filterDataState.value.listCategory, key = { it.id }) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(4.dp),
                                            modifier = Modifier
                                                .width(70.dp)
                                                .heightIn(max = 120.dp)
                                        ) {
                                            ImageItemUri(
                                                uri = it.imageUri,
                                                modifier = modifierImageUri.clickable(
                                                    interactionSource = null,
                                                    onClick = {
                                                        onEventsFilter(
                                                            EventsFilter.SetCategory(
                                                                it.id
                                                            )
                                                        )
                                                    },
                                                    indication = ripple()
                                                ),
                                                imageLoading = ImageLoading.InfrequentlyLoading
                                            )
                                            Text(
                                                text = it.name,
                                                overflow = TextOverflow.Ellipsis,
                                                fontWeight = if (it.id == filterDataState.value.selectedCategoryId) FontWeight.Bold else null
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                item(key = 3) {
                    containerColumn(Modifier.offset(x = offsetItems)) {
                        textNameFilter(Modifier, "Price")
                        TextFieldsPriceFilter(
                            modifier = Modifier
                                .width(150.dp)
                                .height(30.dp),
                            priceMin = filterDataState.value.priceMin,
                            priceMax = filterDataState.value.priceMax,
                            onEventFilter = {
                                onEventsFilter(it)
                            })
                    }
                }

                item(key = 4) {
                    containerColumn(Modifier.offset(x = offsetItems)) {
                        textNameFilter(Modifier, "Title")
                        TextFieldTitleFilter(
                            modifier = Modifier
                                .width(150.dp)
                                .height(30.dp),
                            value = filterDataState.value.title,
                            onEventFilter = { onEventsFilter(it) })
                    }
                }
                item(key = 5) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 10.dp), contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = onFilter,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    237,
                                    170,
                                    75
                                )
                            )
                        ) {
                            Text(
                                text = "Done",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

