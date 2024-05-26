package com.vlad.sharaga.ui.assemblies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.ButtonItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AssembliesState {
    data object Loading : AssembliesState
    data class Content(val assemblies: List<Item>) : AssembliesState
    data object Empty : AssembliesState
    data object Error : AssembliesState
}


@HiltViewModel
class AssembliesViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AssembliesState>(AssembliesState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = AssembliesState.Content(listOf(
                ButtonItem("Создать новую сборку"),
            ))
        }
    }

}