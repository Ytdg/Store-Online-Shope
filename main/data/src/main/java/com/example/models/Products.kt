package com.example.models


data class ProductPreview(
    val id: Int,
    val title: String,
    val price: String,
    val imagePreviewUri: String
)

data class  ProductEntire(
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val images: List<String>,
    val productToBasket: ProductToBasket?
)

data class FilterData(
    val selectedCategoryId:Int?=null,
    val priceMin: String? = null,
    val priceMax:String?=null,
    val title:String?=null
)

data class ProductToBasket(
    val idProduct:Int,
    val count:Int
)

data class Category(
    val id: Int,
    val name: String,
    val imageUri: String
)