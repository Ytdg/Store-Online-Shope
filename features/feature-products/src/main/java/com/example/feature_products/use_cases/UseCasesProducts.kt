package com.example.feature_products.use_cases

import com.example.feature_products.use_cases.products.UseCaseFilterData
import com.example.feature_products.use_cases.products.UseCaseGetProducts
import com.example.feature_products.use_cases.products.UseCaseProductToBusket
import javax.inject.Inject


class UseCasesProducts @Inject constructor(
    val useCaseGetProducts: UseCaseGetProducts,
    val useCaseFilterData: UseCaseFilterData,
    val useCaseProductToBusket: UseCaseProductToBusket
)