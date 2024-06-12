package com.smart.parts.picker.ui.assemblies.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.core.adapter.recycler.fingerprints.ProductPreviewItem
import com.smart.parts.picker.data.MainRepository
import com.smart.parts.picker.domain.average
import com.smart.parts.picker.models.types.ProductId
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
    data class Content(
        val assemblyTitle: String,
        val averagePrice: Double,
        val products: List<ProductPreviewItem>
    ) : AssemblyBrowseState

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
            val products = mutableListOf<ProductPreviewItem>()
            val prices = mutableListOf<Double>()


            var isSuccessful = true
            for (productId in assemblyData.products) {
                val product = mainRepository.apiClient.fetchProduct(productId)

                if (product == null) {
                    isSuccessful = false
                    _state.value = AssemblyBrowseState.Error
                    break
                }

                prices.add(
                    product.price
                        ?.mapNotNull { it.price }
                        ?.average() ?: 0.0
                )

                val productPreviewItem = ProductPreviewItem.create(
                    product = product,
                )
                if (productPreviewItem != null) {
                    products.add(productPreviewItem)
                }
            }

            if (isSuccessful) {
                _state.value =
                    AssemblyBrowseState.Content(assemblyData.title, prices.sum(), products)
            }
        }
    }

    fun deleteProduct(productId: ProductId) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = AssemblyBrowseState.Loading
            val assemblyData = mainRepository.assemblyDao.getById(assemblyId)
            val newAssemblyData = assemblyData.copy(products = assemblyData.products - productId)
            mainRepository.assemblyDao.update(newAssemblyData)
            load()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("assemblyId") assemblyId: Int
        ): AssemblyBrowseViewModel
    }
}