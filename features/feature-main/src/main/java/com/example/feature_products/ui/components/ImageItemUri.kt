package com.example.feature_products.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.feature_products.util.ImageLoading
import com.example.feature_products.util.getSingleImageLoader


@Composable
 fun ImageItemUri(uri: String, modifier: Modifier, imageLoading: ImageLoading) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            imageLoader = LocalContext.current.getSingleImageLoader(imageLoading)
        )

    }
}