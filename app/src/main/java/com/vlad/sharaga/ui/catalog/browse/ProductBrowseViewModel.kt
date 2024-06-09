package com.vlad.sharaga.ui.catalog.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ProductPreviewItem
import com.vlad.sharaga.core.adapter.spinner.SearchResultItem
import com.vlad.sharaga.models.Product
import com.vlad.sharaga.models.ProductImage
import com.vlad.sharaga.models.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface BrowseState {
    data object Loading : BrowseState
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

    fun load(productType: ProductType) {
        viewModelScope.launch(Dispatchers.IO) {
            val products = mainRepository.apiClient
                .fetchProducts(productType.id)
                ?.mapNotNull { product: Product ->
                    val productPrices = product.price
                    val productImage = ProductImage(product.id, product.id, product.image)

                    if (productPrices == null || productImage == null) {
                        return@mapNotNull null
                    }

                    ProductPreviewItem.create(
                        product = product,
                        productImage = productImage,
                        productPrices = productPrices
                    )
                }

            if (products == null) {
                _state.value = BrowseState.Error
                return@launch
            }

            _state.value = BrowseState.Content(products)
        }
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val products = mainRepository.apiClient.searchProducts(query) ?: emptyList()
            _state.value = BrowseState.Autocomplete(products)
        }
    }

}