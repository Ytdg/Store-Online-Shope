package com.example.core.extentions

import com.example.models.Category
import com.example.api_network.products.CategoryDAP
import com.example.models.ProductToBasket
import com.example.models.ProductPreview
import com.example.api_network.products.ProductDAP
import com.example.database.models.ProductToBasketDB
import com.example.database.models.ProductDB
import com.example.models.ProductEntire

fun ProductDAP.toProductDB() = ProductDB(id, title, price, description, images.toListUriImages())
fun ProductDB.toProductPreview() =
    ProductPreview(id, title, price, if (images.isEmpty()) "" else images.first())
fun ProductToBasket.toProductToBasketDB() = ProductToBasketDB(idProduct, count)
fun ProductToBasketDB.toProductToBasket() = ProductToBasket(idProduct, countProducts)
fun CategoryDAP.toCategory() = Category(id, name, image)

fun ProductDB.toProductEntire(productToBasket: ProductToBasket?) =
    ProductEntire(
        id,
        title,
        price,
        description,
        images,
        productToBasket = productToBasket
    )
