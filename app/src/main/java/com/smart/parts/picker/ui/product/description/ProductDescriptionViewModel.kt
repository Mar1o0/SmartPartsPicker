package com.smart.parts.picker.ui.product.description

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.core.adapter.recycler.fingerprints.SpecificationItem
import com.smart.parts.picker.data.MainRepository
import com.smart.parts.picker.data.database.tables.assembly.AssemblyData
import com.smart.parts.picker.models.Product
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
    data class Content(
        val product: Product,
        val specs: List<SpecificationItem>,
    ) : ProductDescriptionState

    data class Error(val message: String) : ProductDescriptionState

    data class AddToAssemblyPopup(val assemblies: List<AssemblyData>) : ProductDescriptionState
    data object Empty : ProductDescriptionState
}

@HiltViewModel(assistedFactory = ProductDescriptionViewModel.Factory::class)
class ProductDescriptionViewModel @AssistedInject constructor(
    @Assisted("product") private val product: Product?,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ProductDescriptionState>(ProductDescriptionState.Loading)
    val state = _state.asStateFlow()

    private var content: ProductDescriptionState.Content? = null

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            if (product?.id == null) {
                _state.value = ProductDescriptionState.Error("No product to show")
                return@launch
            }

            val productSpec = mainRepository.apiClient.fetchProductSpecs(product.id) ?: emptyList()
            _state.value = ProductDescriptionState.Content(
                product = product,
                specs = productSpec.mapNotNull(SpecificationItem::create)
            ).also {
                content = it
            }
        }
    }

    fun createAssembly(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newAssembly = AssemblyData(
                title = name,
                products = listOf(product?.id!!)
            )
            mainRepository.assemblyDao.insert(newAssembly)
        }
    }

    fun addProductToAssembly(assemblyId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (product?.id == null) {
                return@launch
            }

            val assembly = mainRepository.assemblyDao.getById(assemblyId)
            if (assembly.products.contains(product.id)) {
                return@launch
            }

            val updatedAssembly = assembly.copy(products = assembly.products + product.id)
            mainRepository.assemblyDao.update(updatedAssembly)
        }
    }

    fun loadPopup() {
        _state.value = ProductDescriptionState.Empty
        viewModelScope.launch(Dispatchers.IO) {
            val assemblies = mainRepository.assemblyDao.getAll()
            _state.value = ProductDescriptionState.AddToAssemblyPopup(assemblies)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("product") product: Product?
        ): ProductDescriptionViewModel
    }
}