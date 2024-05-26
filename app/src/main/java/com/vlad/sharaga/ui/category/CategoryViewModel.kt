package com.vlad.sharaga.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.domain.adapter.recycler.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CategoryState {
    data object Loading : CategoryState
    data class Loaded(val items: List<Item>) : CategoryState
    data object Error : CategoryState
}

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val state = _state.asStateFlow()

    fun fetchCategory(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = mainRepository.apiRepository.fetchCategory(categoryId)
            _state.value = CategoryState.Loaded(items)
        }
    }

}