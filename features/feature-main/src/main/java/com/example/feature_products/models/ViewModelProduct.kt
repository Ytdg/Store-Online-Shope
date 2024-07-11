package com.example.feature_products.models

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.ProductToBasket
import com.example.api.ApiRepozitoryProduct
import com.example.feature_products.events.EventsProduct
import com.example.feature_products.use_cases.UseCasesProduct
import com.example.feature_products.use_cases.products.ActionProductToBasket
import com.example.feature_products.util.toStateProductEnitre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ViewModelProduct @Inject constructor(
    private val apiRepozitoryProduct: ApiRepozitoryProduct,
    private val useCasesProduct: UseCasesProduct,
    savedStateHandele: SavedStateHandle
) : ViewModel() {
    private val id: Int = savedStateHandele["id"]!!


    val stateProductEntire: StateFlow<StateProductEntire> =
        apiRepozitoryProduct.getProductEntire(id).map { it.toStateProductEnitre() }.stateIn(
            viewModelScope,
            SharingStarted.Lazily, StateProductEntire()
        )

    private var job: Job = Job()
    fun onEvent(eventsProduct: EventsProduct) {
        job.cancel()
        when (eventsProduct) {
            is EventsProduct.SetProductToBasket -> {
                job = viewModelScope.launch {
                    if (stateProductEntire.value.stateProductToBasket == null) {
                        useCasesProduct.useCaseProductToBusket(
                            StateProductToBasket(id),
                            actionProductToBasket = ActionProductToBasket.Put
                        )
                    } else {
                        useCasesProduct.useCaseProductToBusket(
                            StateProductToBasket(idProduct = id),
                            actionProductToBasket = ActionProductToBasket.Remove
                        )
                    }
                }

            }
        }
    }
}