package com.vlad.sharaga.ui.assemblies.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ProductPreviewItem
import com.vlad.sharaga.data.MainRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AssemblyBrowseState {
    data object Loading : AssemblyBrowseState
    data class Loaded(val products: List<ProductPreviewItem>) : AssemblyBrowseState
    data object Error : AssemblyBrowseState
}

@HiltViewModel(assistedFactory = AssemblyBrowseViewModel.Factory::class)
class AssemblyBrowseViewModel @AssistedInject constructor(
    @Assisted("assemblyId") private val assemblyId: Int,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AssemblyBrowseState>(AssemblyBrowseState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val assemblyData = mainRepository.assemblyDao.getById(assemblyId)
            val products = assemblyData.products.mapNotNull { productId ->
                val product = mainRepository.apiClient.fetchProduct(productId)
                val productImage = mainRepository.apiClient.fetchProductImage(productId)
                val productPrices = mainRepository.apiClient.fetchProductPrices(productId)

                if (product == null || productImage == null || productPrices == null) {
                    _state.value = AssemblyBrowseState.Error
                    return@mapNotNull null
                }

                ProductPreviewItem.create(
                    product = product,
                    productImage = productImage,
                    productPrices = productPrices
                )
            }
            _state.value = AssemblyBrowseState.Loaded(products)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("assemblyId") assemblyId: Int
        ): AssemblyBrowseViewModel
    }
}