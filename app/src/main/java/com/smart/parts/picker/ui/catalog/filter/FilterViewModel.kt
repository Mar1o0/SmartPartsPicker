package com.smart.parts.picker.ui.catalog.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.core.adapter.Item
import com.smart.parts.picker.core.adapter.recycler.fingerprints.filter.FilterItem
import com.smart.parts.picker.core.adapter.recycler.fingerprints.filter.FilterPriceRangeItem
import com.smart.parts.picker.core.adapter.recycler.fingerprints.filter.FilterSelectItem
import com.smart.parts.picker.core.adapter.recycler.fingerprints.filter.FilterSelectVariantItem
import com.smart.parts.picker.data.MainRepository
import com.smart.parts.picker.models.types.ProductType
import com.smart.parts.picker.ui.NavHostViewModel
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

            if (prevSession.isNotEmpty() && sharedViewModel.currentProductType == productType) {
                _state.value = FilterState.Content(prevSession)
            } else {
                val filters = mainRepository.apiClient.fetchFilters(productType)

                if (filters == null) {
                    _state.value = FilterState.Error("Failed to load filters")
                    return@launch
                }

                val filterItems = mutableListOf<FilterItem>()
                filterItems.add(
                    FilterPriceRangeItem(
                        -1,
                        0f,
                        500000f,
                        0f,
                        500000f,
                    )
                )

                filters.filter { it.isValid }
                    .sortedBy { it.filterType }
                    .groupBy { it.filterType }
                    .forEach { (filterType, filters) ->
                        filterItems.add(
                            FilterSelectItem(
                                id = filterType!!,
                                title = filters.first().filterFriendlyName!!,
                                variants = filters.map { filter ->
                                    FilterSelectVariantItem(
                                        id = filter.id!!,
                                        name = filter.value!!,
                                        isSelected = false,
                                    )
                                }
                            )
                        )
                    }

                sharedViewModel.currentProductType = productType
                sharedViewModel.currentFilters.clear()
                sharedViewModel.currentFilters.addAll(filterItems)

                _state.value = FilterState.Content(filterItems)
            }
        }
    }

    fun applyFilters(
        currentList: MutableList<Item>,
        onResult: (priceMin: Float, priceMax: Float, filters: String) -> Unit,
    ) {
        val filterItems = currentList.filterIsInstance<FilterItem>()

        sharedViewModel.currentFilters.clear()
        sharedViewModel.currentFilters.addAll(filterItems)

        val filterPriceRangeItem = filterItems.filterIsInstance<FilterPriceRangeItem>().first()
        val filterSelectItems = filterItems.filterIsInstance<FilterSelectItem>()

        val priceMin = filterPriceRangeItem.from
        val priceMax = filterPriceRangeItem.to
        val filters = filterSelectItems
            .flatMap { it.variants }
            .filter { it.isSelected }
            .map { it.id }
            .joinToString(",")

        onResult(priceMin, priceMax, filters)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("sharedViewModel") sharedViewModel: NavHostViewModel,
            @Assisted("productType") productType: ProductType,
        ): FilterViewModel
    }

}