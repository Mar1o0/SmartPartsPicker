package com.vlad.sharaga.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.core.view.DEFAULT_CITY
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
    data object Dismiss : SplashScreenState
    data object Dispose : SplashScreenState
}

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun onAcceptClicked(cityName: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.appPreferences.save(locationKey, cityName ?: DEFAULT_CITY)
            _state.value = SplashScreenState.Dismiss
        }
    }

    fun onDefault() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.appPreferences.get(locationKey)?.let {
                _state.value = SplashScreenState.Dispose
            } ?: run {
                mainRepository.appPreferences.save(locationKey, DEFAULT_CITY)
                _state.value = SplashScreenState.Dispose
            }
        }
    }

    private val _state = MutableStateFlow<SplashScreenState>(SplashScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = if (mainRepository.appPreferences.get(locationKey) != null) {
                SplashScreenState.Dispose
            } else {
                val cityNames = mainRepository.apiClient.fetchCityNames()
                if (cityNames == null) {
                    SplashScreenState.Dispose
                } else {
                    SplashScreenState.LocationChooser(
                        cityNames = cityNames
                    )
                }
            }
        }
    }

}