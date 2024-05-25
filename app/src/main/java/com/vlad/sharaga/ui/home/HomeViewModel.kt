package com.vlad.sharaga.ui.home

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

sealed interface HomeState {
    data object Loading : HomeState
    data class Loaded(
        val items: List<Item>
    ) : HomeState
}


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val feed = mainRepository.fetchHomeFeed()
            _state.value = HomeState.Loaded(feed)
        }
    }
}