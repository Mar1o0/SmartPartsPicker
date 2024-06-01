package com.vlad.sharaga.ui.catalog.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ProductPreviewItem
import com.vlad.sharaga.models.Product
import com.vlad.sharaga.models.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface BrowseState {
    data object Loading : BrowseState
    data class Loaded(val products: List<ProductPreviewItem>) : BrowseState
    data object Error : BrowseState
}

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<BrowseState>(BrowseState.Loading)
    val state = _state.asStateFlow()

    fun fetchCategory(productType: ProductType) {
        viewModelScope.launch(Dispatchers.IO) {
            val products = mainRepository.apiClient
                .fetchProducts(productType.id)
                ?.mapNotNull { product: Product ->
                    val productPrices = mainRepository.apiClient.fetchProductPrices(product.id)
                    val productImage = mainRepository.apiClient.fetchProductImage(product.id)

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

            _state.value = BrowseState.Loaded(products)
        }
    }

}