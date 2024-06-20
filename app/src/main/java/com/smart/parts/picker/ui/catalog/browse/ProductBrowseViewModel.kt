package com.smart.parts.picker.ui.catalog.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.data.MainRepository
import com.smart.parts.picker.core.adapter.recycler.fingerprints.ProductPreviewItem
import com.smart.parts.picker.core.adapter.spinner.SearchResultItem
import com.smart.parts.picker.models.Product
import com.smart.parts.picker.models.types.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface BrowseState {
    data object Loading : BrowseState
    data object LoadMore : BrowseState
    data class Content(val products: List<ProductPreviewItem>) : BrowseState
    data class Autocomplete(val result: List<SearchResultItem>) : BrowseState
    data object Error : BrowseState
}

@HiltViewModel
class ProductBrowseViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<BrowseState>(BrowseState.Loading)
    val state = _state.asStateFlow()

    private val perPage = 20
    private var page = 1

    fun load(
        productType: ProductType,
        priceMin: Float,
        priceMax: Float,
        filters: String,
    ) {
        _state.value = BrowseState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val products = mainRepository.apiClient
                .fetchProducts(
                    productType = productType,
                    priceMin = priceMin.toDouble(),
                    priceMax = priceMax.toDouble(),
                    filters = filters,
                    page = page,
                    perPage = perPage,
                )
                ?.mapNotNull { product: Product ->
                    ProductPreviewItem.create(
                        product = product,
                    )
                }

            if (products == null) {
                _state.value = BrowseState.Error
                return@launch
            }

            _state.value = BrowseState.Content(products)
        }
    }

    fun search(
        productType: ProductType,
        query: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val products = mainRepository.apiClient.searchProducts(productType, query) ?: emptyList()
            _state.value =
                BrowseState.Autocomplete(products.mapNotNull { SearchResultItem.create(it) })
        }
    }

    fun loadMore(productType: ProductType, priceMin: Float, priceMax: Float, filters: String) {
        if (_state.value !is BrowseState.Content) return
        val products = (_state.value as BrowseState.Content).products
        _state.value = BrowseState.LoadMore

        viewModelScope.launch(Dispatchers.IO) {
            val newProducts = mainRepository.apiClient
                .fetchProducts(
                    productType = productType,
                    priceMin = priceMin.toDouble(),
                    priceMax = priceMax.toDouble(),
                    filters = filters,
                    page = ++page,
                    perPage = perPage,
                )
                ?.mapNotNull { product: Product ->
                    ProductPreviewItem.create(
                        product = product,
                    )
                }

            if (newProducts == null) {
                _state.value = BrowseState.Error
                return@launch
            }

            _state.value = BrowseState.Content(products + newProducts)
        }

    }

}