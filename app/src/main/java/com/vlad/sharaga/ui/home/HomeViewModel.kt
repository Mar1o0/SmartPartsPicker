package com.vlad.sharaga.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.core.adapter.recycler.fingerprints.CategoryItem
import com.vlad.sharaga.models.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeState {
    data object Loading : HomeState
    data class Content(
        val categories: List<CategoryItem>
    ) : HomeState

    data class Error(val message: String) : HomeState
}


@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = listOf(
                CategoryItem(
                    productType = ProductType.CPU,
                    title = "Процессор",
                    previewUrl = "file:///android_asset/cpu.png",
                ),
                CategoryItem(
                    productType = ProductType.MOTHERBOARD,
                    title = "Материнская плата",
                    previewUrl = "file:///android_asset/motherboard.png",
                ),
                CategoryItem(
                    productType = ProductType.RAM,
                    title = "Оперативная память",
                    previewUrl = "file:///android_asset/ram.png",
                ),
                CategoryItem(
                    productType = ProductType.STORAGE,
                    title = "Носители информации",
                    previewUrl = "file:///android_asset/storage.png",
                ),
                CategoryItem(
                    productType = ProductType.GPU,
                    title = "Видеокарта",
                    previewUrl = "file:///android_asset/gpu.png",
                ),
                CategoryItem(
                    productType = ProductType.POWER_SUPPLY,
                    title = "Блок питания",
                    previewUrl = "file:///android_asset/power_supply.png",
                ),
                CategoryItem(
                    productType = ProductType.CASING,
                    title = "Корпус",
                    previewUrl = "file:///android_asset/casing.png",
                ),
            )

            _state.value = HomeState.Content(categories)
        }
    }
}