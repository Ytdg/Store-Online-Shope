package com.example.feature_products.use_cases

import com.example.feature_products.use_cases.products.UseCaseProductToBusket
import javax.inject.Inject

class UseCasesProduct @Inject constructor(val useCaseProductToBusket: UseCaseProductToBusket)