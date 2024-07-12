package com.example.feature_products.ui.components_products_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.feature_products.R
import com.example.feature_products.events.EventsProduct
import com.example.feature_products.models.state_products.StateProductPreview


@Composable
fun CardProductPreview(
    modifier: Modifier,
    product: StateProductPreview,
    onClickBtn: (EventsProduct) -> Unit
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 15.dp)
        ) {
            Column(modifier = Modifier.matchParentSize()) {
                Box(modifier = Modifier.fillMaxHeight(0.7f)) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()

                            .background(Color(138, 138, 138)),
                        model = product.imagePreviewUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 5.dp, end = 30.dp)
                    ) {
                        Text(
                            text = product.title,
                            fontSize = 14.sp,
                            color = Color(102, 102, 102),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "$${product.price}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    IconButton(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(y = 14.dp, x = 4.dp),
                        onClick = { onClickBtn(EventsProduct.SetProductToBasket(product.id))}) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.icons8_plus__1_),
                            modifier = Modifier.size(32.dp),
                            contentDescription = null,
                            tint = Color(237, 170, 75)
                        )
                    }
                }
            }

        }

    }
}


