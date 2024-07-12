package com.example.feature_products.models.state_products

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_repository.api_products.ApiRepozitoryProduct
import com.example.feature_products.events.EventsProduct
import com.example.feature_products.use_cases.UseCasesProduct
import com.example.feature_products.use_cases.products.ActionProductToBasket
import com.example.feature_products.util.toStateProductEnitre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

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