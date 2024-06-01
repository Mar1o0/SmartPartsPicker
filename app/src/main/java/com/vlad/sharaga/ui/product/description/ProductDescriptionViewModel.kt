package com.vlad.sharaga.ui.product.description

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.models.ProductDescription
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProductDescriptionState {
    data object Loading : ProductDescriptionState
    data class Loaded(val productDescription: ProductDescription) : ProductDescriptionState
    data class Error(val message: String) : ProductDescriptionState
}

@HiltViewModel(assistedFactory = ProductDescriptionViewModel.Factory::class)
class ProductDescriptionViewModel @AssistedInject constructor(
    @Assisted("productId") private val productId: ProductId?,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ProductDescriptionState>(ProductDescriptionState.Loading)
    val state = _state.asStateFlow()


    fun fetchArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            if (productId == null) {
                _state.value = ProductDescriptionState.Error("Product id is null")
                return@launch
            }

            val product = mainRepository.apiClient.fetchProduct(productId)
            val productPrices = mainRepository.apiClient.fetchProductPrices(productId)
            val productImages = mainRepository.apiClient.fetchProductImages(productId)
            val productSpecs = mainRepository.apiClient.fetchProductSpecs(productId)

            if (product == null || productPrices == null || productImages == null || productSpecs == null) {
                _state.value = ProductDescriptionState.Error("Failed to load product")
                return@launch
            }

            val productDescription = ProductDescription.create(
                product = product,
                productPrices = productPrices,
                productImages = productImages,
                productSpecs = productSpecs
            )

            _state.value = ProductDescriptionState.Loaded(productDescription)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("productId") productId: ProductId?
        ): ProductDescriptionViewModel
    }
}