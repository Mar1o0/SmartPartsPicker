package com.vlad.sharaga.ui.budget.assemblies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.core.adapter.recycler.fingerprints.BudgetAssemblyItem
import com.vlad.sharaga.core.adapter.recycler.fingerprints.CategoryItem
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.models.ProductType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface BudgetBrowseState {
    data object Loading : BudgetBrowseState
    data class Content(
        val assemblies: List<BudgetAssemblyItem>
    ) : BudgetBrowseState

    data class Error(val message: String) : BudgetBrowseState
}


@HiltViewModel(assistedFactory = BudgetAssembliesViewModel.Factory::class)
class BudgetAssembliesViewModel @AssistedInject constructor(
    @Assisted("budgetPrice") private val budgetPrice: Int,
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<BudgetBrowseState>(BudgetBrowseState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val assemblies = mainRepository.apiClient.fetchAssemblies(budgetPrice)

            if (assemblies == null) {
                _state.value = BudgetBrowseState.Error("Failed to load assemblies")
                return@launch
            }

            _state.value = BudgetBrowseState.Content(assemblies)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("budgetPrice") budgetPrice: Int,
        ): BudgetAssembliesViewModel
    }
}