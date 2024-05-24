package com.vlad.sharaga.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.data.preferences.locationKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SplashScreenState {
    data object Loading : SplashScreenState
    data class LocationChooser(val cityNames: List<String>) : SplashScreenState
    data object Dispose : SplashScreenState
}

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    fun onAcceptClicked(cityName: String?) {
        if (cityName != null) {
            viewModelScope.launch(Dispatchers.IO) {
                mainRepository.savePref(locationKey, cityName)
                _state.value = SplashScreenState.Dispose
            }
        }
    }

    private val _state = MutableStateFlow<SplashScreenState>(SplashScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = if (mainRepository.getPref(locationKey) != null) {
                SplashScreenState.Dispose
            } else {
                val cityNames = mainRepository.fetchCityNames()
                SplashScreenState.LocationChooser(
                    cityNames = cityNames
                )
            }
        }
    }

}