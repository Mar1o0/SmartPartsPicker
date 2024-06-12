package com.smart.parts.picker.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.core.adapter.recycler.fingerprints.CategoryItem
import com.smart.parts.picker.models.types.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CatalogState {
    data object Loading : CatalogState
    data class Content(val categories: List<CategoryItem>) : CatalogState
    data object Error : CatalogState
}


@HiltViewModel
class CatalogViewModel @Inject constructor(
//    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CatalogState>(CatalogState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = ProductType.entries.map { productType ->
                when (productType) {
                    ProductType.GPU -> CategoryItem(
                        productType = productType,
                        title = "Видеокарта",
                        previewUrl = "file:///android_asset/gpu.png",
                    )

                    ProductType.CPU -> CategoryItem(
                        productType = productType,
                        title = "Процессор",
                        previewUrl = "file:///android_asset/cpu.png",
                    )

                    ProductType.MB -> CategoryItem(
                        productType = productType,
                        title = "Материнская плата",
                        previewUrl = "file:///android_asset/motherboard.png",
                    )

                    ProductType.RAM -> CategoryItem(
                        productType = productType,
                        title = "Оперативная память",
                        previewUrl = "file:///android_asset/ram.png",
                    )

                    ProductType.HDD -> CategoryItem(
                        productType = productType,
                        title = "Жесткие диски",
                        previewUrl = "file:///android_asset/hdd.jpg",
                    )

                    ProductType.SSD -> CategoryItem(
                        productType = productType,
                        title = "SSD накопители",
                        previewUrl = "file:///android_asset/storage.png",
                    )

                    ProductType.CHASSIS -> CategoryItem(
                        productType = productType,
                        title = "Корпус",
                        previewUrl = "file:///android_asset/casing.png",
                    )

                    ProductType.PSU -> CategoryItem(
                        productType = productType,
                        title = "Блок питания",
                        previewUrl = "file:///android_asset/power_supply.png",
                    )
                }
            }

            _state.value = CatalogState.Content(categories)
        }
    }

}