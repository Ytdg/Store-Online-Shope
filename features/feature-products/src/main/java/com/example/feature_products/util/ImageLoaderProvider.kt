package com.example.feature_products.util

import android.content.Context
import androidx.compose.runtime.Immutable
import coil.ImageLoader
import coil.disk.DiskCache
import coil.imageLoader
import coil.memory.MemoryCache
@Immutable
enum class ImageLoading {
    InfrequentlyLoading, EmbeddedLoading
}

private object ImageLoaderInstanse {
    lateinit var infrequentlyImageLoader: ImageLoader
    fun checkInstanseInfrequentlyImageLoader() = this::infrequentlyImageLoader.isInitialized
}

fun Context.getSingleImageLoader(imageLoading: ImageLoading): ImageLoader {
    return when (imageLoading) {
        ImageLoading.InfrequentlyLoading -> {
            if (ImageLoaderInstanse.checkInstanseInfrequentlyImageLoader()) {
                ImageLoaderInstanse.infrequentlyImageLoader
            } else {
                createImageLoaderInfrequentlyLoading(this)
            }
        }

        ImageLoading.EmbeddedLoading -> {
            this.imageLoader
        }
    }
}

private fun createImageLoaderInfrequentlyLoading(context: Context): ImageLoader {
    ImageLoaderInstanse.infrequentlyImageLoader = ImageLoader(context).newBuilder().memoryCache {
        MemoryCache.Builder(context)
            .maxSizePercent(0.7)
            .build()
    }
        .diskCache {
            DiskCache.Builder()
                .maxSizePercent(0.2).directory(context.cacheDir)
                .build()
        }
        .build()
    return ImageLoaderInstanse.infrequentlyImageLoader
}
