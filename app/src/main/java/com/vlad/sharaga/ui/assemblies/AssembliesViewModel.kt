package com.vlad.sharaga.ui.assemblies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.core.adapter.recycler.fingerprints.AssemblyItem
import com.vlad.sharaga.data.database.tables.assembly.AssemblyData
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
            val items = assemblies.map { assembly ->
                AssemblyItem(
                    id = assembly.id,
                    title = assembly.title,
                    count = assembly.products.size,
                    previewUrl1 = assembly.products.getOrNull(0)?.let { productId ->
                        mainRepository.apiClient.fetchProductImage(productId)?.imageUrl
                    },
                    previewUrl2 = assembly.products.getOrNull(1)?.let { productId ->
                        mainRepository.apiClient.fetchProductImage(productId)?.imageUrl
                    },
                    previewUrl3 = assembly.products.getOrNull(2)?.let { productId ->
                        mainRepository.apiClient.fetchProductImage(productId)?.imageUrl
                    },
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

    fun deleteAssembly(assemblyId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.assemblyDao.deleteById(assemblyId)
            load()
        }
    }

}