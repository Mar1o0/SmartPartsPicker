package com.smart.parts.picker.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.data.MainRepository
import com.smart.parts.picker.models.Product
import com.smart.parts.picker.models.types.ProductId
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProductState {
    data object Loading : ProductState
    data class Content(
        val product: Product
    ) : ProductState

    data class Error(val message: String) : ProductState
}

@HiltViewModel(assistedFactory = ProductViewModel.Factory::class)
class ProductViewModel @AssistedInject constructor(
    @Assisted("productId") private val productId: ProductId,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ProductState>(ProductState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val product = mainRepository.apiClient.fetchProduct(productId)
            if (product == null) {
                _state.value = ProductState.Error("No product to show")
                return@launch
            }

            _state.value = ProductState.Content(
                product = product
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("productId") productId: ProductId
        ): ProductViewModel
    }

}