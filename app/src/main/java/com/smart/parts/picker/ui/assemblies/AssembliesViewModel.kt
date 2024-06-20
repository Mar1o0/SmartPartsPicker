package com.smart.parts.picker.ui.assemblies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.data.MainRepository
import com.smart.parts.picker.core.adapter.recycler.fingerprints.AssemblyItem
import com.smart.parts.picker.core.adapter.recycler.fingerprints.ProductPreviewItem
import com.smart.parts.picker.data.database.tables.assembly.AssemblyData
import com.smart.parts.picker.domain.average
import com.smart.parts.picker.ui.assemblies.browse.AssemblyBrowseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AssembliesState {
    data object Loading : AssembliesState
    data class Content(val assemblies: List<AssemblyItem>) : AssembliesState
    data object Error : AssembliesState
}

@HiltViewModel
class AssembliesViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AssembliesState>(AssembliesState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val assemblies = mainRepository.assemblyDao.getAll()
            val items = assemblies.map { assembly: AssemblyData ->
                val prices = mutableListOf<Double>()
                var previewUrl1: String? = null
                var previewUrl2: String? = null
                var previewUrl3: String? = null

                assembly.products.forEachIndexed { i, productId ->
                    val product = mainRepository.apiClient.fetchProduct(productId)

                    prices.add(
                        product?.price
                            ?.mapNotNull { it.price }
                            ?.average() ?: 0.0
                    )

                    when (i) {
                        0 -> previewUrl1 = product?.image
                        1 -> previewUrl2 = product?.image
                        2 -> previewUrl3 = product?.image
                    }
                }

                AssemblyItem(
                    id = assembly.id,
                    title = assembly.title,
                    count = assembly.products.size,
                    price = prices.sum(),
                    previewUrl1 = previewUrl1,
                    previewUrl2 = previewUrl2,
                    previewUrl3 = previewUrl3,
                )
            }

            _state.value = AssembliesState.Content(items)
        }
    }

    fun createAssembly(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newAssembly = AssemblyData(
                title = name,
                products = emptyList()
            )
            mainRepository.assemblyDao.insert(newAssembly)
            load()
        }
    }

    fun deleteAssembly(assemblyId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.assemblyDao.deleteById(assemblyId)
            load()
        }
    }

}