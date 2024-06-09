package com.vlad.sharaga.ui.budget.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.core.adapter.recycler.fingerprints.BudgetAssemblyItem
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ProductPreviewItem
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.domain.format
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface BudgetBrowseState {
    data object Loading : BudgetBrowseState
    data class Content(
        val assemblyPrice: String,
        val products: List<ProductPreviewItem>
    ) : BudgetBrowseState
    data object Error : BudgetBrowseState
}

@HiltViewModel(assistedFactory = BudgetBrowseViewModel.Factory::class)
class BudgetBrowseViewModel @AssistedInject constructor(
    @Assisted("assembly") private val assembly: BudgetAssemblyItem,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<BudgetBrowseState>(BudgetBrowseState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = BudgetBrowseState.Content(assembly.price.format(2), assembly.products)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("assembly") assembly: BudgetAssemblyItem
        ): BudgetBrowseViewModel
    }
}