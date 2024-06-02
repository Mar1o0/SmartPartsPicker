package com.vlad.sharaga.ui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.core.adapter.recycler.fingerprints.GameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface GamesState {
    data object Loading : GamesState
    data class Content(val items: List<GameItem>) : GamesState
    data class Error(val message: String) : GamesState
}


@HiltViewModel
class GamesViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<GamesState>(GamesState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = mainRepository.apiClient
                .fetchGames()
                ?.map { it.toGameItem() }

            if (items == null) {
                _state.value = GamesState.Error("Failed to load games")
                return@launch
            }

            _state.value = GamesState.Content(items)
        }
    }

}