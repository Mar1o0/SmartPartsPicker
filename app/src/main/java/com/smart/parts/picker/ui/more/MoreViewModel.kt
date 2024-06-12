package com.smart.parts.picker.ui.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.core.view.DEFAULT_CITY
import com.smart.parts.picker.data.MainRepository
import com.smart.parts.picker.data.preferences.locationKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MoreState {
    data object Loading : MoreState
    data class Content(val city: String) : MoreState
    data class ChangeCityPopup(val cities: List<String>) : MoreState
}

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MoreState>(MoreState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val city = mainRepository.appPreferences.get(locationKey) ?: DEFAULT_CITY
            _state.value = MoreState.Content(city)
        }
    }

    fun changeCity() {
        viewModelScope.launch(Dispatchers.IO) {
            val cities = mainRepository.apiClient.fetchCityNames()

            if (cities.isNullOrEmpty()) return@launch

            _state.value = MoreState.ChangeCityPopup(cities)
        }
    }

    fun onCityChange(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.appPreferences.save(locationKey, city)
            load()
        }
    }

}