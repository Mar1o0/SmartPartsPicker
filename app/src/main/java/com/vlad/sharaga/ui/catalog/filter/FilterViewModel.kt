package com.vlad.sharaga.ui.catalog.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.core.adapter.recycler.fingerprints.filter.FilterItem
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.models.ProductType
import com.vlad.sharaga.ui.NavHostViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface FilterState {
    data object Loading : FilterState
    data class Content(val filters: List<FilterItem>) : FilterState
    data class Error(val message: String) : FilterState
}

@HiltViewModel(assistedFactory = FilterViewModel.Factory::class)
class FilterViewModel @AssistedInject constructor(
    @Assisted("sharedViewModel") private val sharedViewModel: NavHostViewModel,
    @Assisted("productType") private val productType: ProductType,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FilterState>(FilterState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val prevSession = sharedViewModel.currentFilters

            if (prevSession.isNotEmpty()) {
                _state.value = FilterState.Content(prevSession)
            } else {
                val filters = mainRepository.apiClient.fetchFilters(productType)

                if (filters == null) {
                    _state.value = FilterState.Error("Failed to load filters")
                    return@launch
                }

                val filterItems = filters.map(FilterItem::fromFilter)
                _state.value = FilterState.Content(filterItems)

                sharedViewModel.currentFilters.clear()
                sharedViewModel.currentFilters.addAll(filterItems)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("sharedViewModel") sharedViewModel: NavHostViewModel,
            @Assisted("productType") productType: ProductType,
        ): FilterViewModel
    }

}