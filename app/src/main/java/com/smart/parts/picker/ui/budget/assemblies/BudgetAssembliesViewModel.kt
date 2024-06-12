package com.smart.parts.picker.ui.budget.assemblies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.core.adapter.recycler.fingerprints.BudgetAssemblyItem
import com.smart.parts.picker.data.MainRepository
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
        val assemblies: List<BudgetAssemblyItem>
    ) : BudgetBrowseState

    data class Error(val message: String) : BudgetBrowseState
}


@HiltViewModel(assistedFactory = BudgetAssembliesViewModel.Factory::class)
class BudgetAssembliesViewModel @AssistedInject constructor(
    @Assisted("budgetPrice") private val budgetPrice: Float,
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<BudgetBrowseState>(BudgetBrowseState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val assemblies = mainRepository.apiClient.fetchBudgetAssemblies(budgetPrice.toDouble())

            if (assemblies == null) {
                _state.value = BudgetBrowseState.Error("Failed to load assemblies")
                return@launch
            }

            _state.value = BudgetBrowseState.Content(assemblies.mapNotNull { BudgetAssemblyItem.create(it) })
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("budgetPrice") budgetPrice: Float,
        ): BudgetAssembliesViewModel
    }
}