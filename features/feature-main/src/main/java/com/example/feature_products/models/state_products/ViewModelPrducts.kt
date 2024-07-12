package com.example.feature_products.models.state_products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.api_repository.api_products.ApiRepozitoryProducts
import com.example.data.products.ResponseResult
import com.example.feature_products.events.EventsFilter
import com.example.feature_products.events.EventsProduct
import com.example.feature_products.use_cases.UseCasesProducts
import com.example.feature_products.use_cases.products.ActionProductToBasket
import com.example.feature_products.util.toStatePropertyBasket
import com.example.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelProducts @Inject constructor(
    private val apiRepozitoryProducts: ApiRepozitoryProducts,
    private val useCasesProducts: UseCasesProducts
) : ViewModel() {
    val products = useCasesProducts.useCaseGetProducts().cachedIn(viewModelScope)
    private var _stateDataFilter: MutableStateFlow<FilterDataState> = MutableStateFlow(
        FilterDataState()
    )
    val stateFilter: StateFlow<FilterDataState> = _stateDataFilter

    val statePropertyBasket = apiRepozitoryProducts.getProductsToBasket(null).map { it.toStatePropertyBasket() }

    fun onEventsProduct(eventsProduct: EventsProduct) {
        job.cancel()
        when (eventsProduct) {
            is EventsProduct.SetProductToBasket -> {
                job = viewModelScope.launch {
                    useCasesProducts.useCaseProductToBusket(
                        StateProductToBasket(idProduct = eventsProduct.id),
                        actionProductToBasket = ActionProductToBasket.Put
                    )
                }
            }
        }
    }


    private var job: Job = Job()
    fun onEventsFilter(event: EventsFilter) {
        job.cancel()
        when (event) {
            is EventsFilter.LoadDataFilter -> {
                _stateDataFilter.update { it.copy(load = TypeLoadDataFilter.Load) }
                job = viewModelScope.launch {
                    delay(1000)
                    val responseResult = apiRepozitoryProducts.getCategory()
                    when (responseResult) {
                        is ResponseResult.Successfully -> {
                            val value = responseResult.data?.toImmutableList()
                            value?.let {
                                _stateDataFilter.update {
                                    it.copy(
                                        load = TypeLoadDataFilter.Successfull,
                                        listCategory = value
                                    )
                                }
                            }
                        }

                        is ResponseResult.Error -> {
                            _stateDataFilter.update {
                                it.copy(
                                    load = TypeLoadDataFilter.Error,
                                    listCategory = emptyList<Category>().toImmutableList()
                                )
                            }
                        }
                    }
                }
            }

            is EventsFilter.DataFilter -> {
                useCasesProducts.useCaseFilterData(filterDataState = stateFilter.value)
            }

            is EventsFilter.ClearDataFilter -> {
                _stateDataFilter.update {
                    FilterDataState(listCategory = it.listCategory)
                }
                apiRepozitoryProducts.clearDataFilter()
            }

            is EventsFilter.SetPriceMax -> {
                _stateDataFilter.update {
                    it.copy(priceMax = if (event.priceMax == "") null else event.priceMax)
                }
            }

            is EventsFilter.SetPriceMin -> {
                _stateDataFilter.update {
                    it.copy(priceMin = if (event.priceMin == "") null else event.priceMin)
                }
            }

            is EventsFilter.SetCategory -> {
                if (event.id == _stateDataFilter.value.selectedCategoryId) {
                    _stateDataFilter.update { it.copy(selectedCategoryId = null) }
                } else {
                    _stateDataFilter.update {
                        it.copy(selectedCategoryId = event.id)
                    }
                }
            }

            is EventsFilter.SetTitle -> {
                _stateDataFilter.update {
                    it.copy(title = if (event.title == "") null else event.title)
                }
            }
        }
    }


}