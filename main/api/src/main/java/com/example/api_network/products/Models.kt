package com.example.api_network.products


data class CategoryDAP(
    val id: Int,
    val name: String,
    val image: String
)

data class ProductDAP(
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val images: List<String>,
)