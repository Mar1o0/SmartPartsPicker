package com.smart.parts.picker.ui.product.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.parts.picker.core.adapter.recycler.fingerprints.ShopItem
import com.smart.parts.picker.core.view.DEFAULT_CITY
import com.smart.parts.picker.data.MainRepository
import com.smart.parts.picker.data.preferences.locationKey
import com.smart.parts.picker.models.Product
import com.smart.parts.picker.models.ProductPrice
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ShopsState {
    data object Loading : ShopsState
    data class Content(
        val article: List<ShopItem>,
        val cityName: String
    ) : ShopsState
    data class Error(val message: String) : ShopsState
}

@HiltViewModel(assistedFactory = ProductShopsViewModel.Factory::class)
class ProductShopsViewModel @AssistedInject constructor(
    @Assisted("product") private val product: Product?,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ShopsState>(ShopsState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            if (product == null) {
                _state.value = ShopsState.Error("No product to show")
                return@launch
            }

            val shopItems = product.price?.mapNotNull { productPrice: ProductPrice ->
                if (productPrice.isValid) {
                    ShopItem(
                        id = productPrice.id!!,
                        logoUrl = productPrice.shopImage!!,
                        price = productPrice.price!!,
                        deliveryDescription = productPrice.shopName!!,
                        deliveryPrice = 0.0,
                        url = productPrice.href!!,
                    )
                } else {
                    null
                }
            }

            if (shopItems.isNullOrEmpty()) {
                _state.value = ShopsState.Error("No shops to show")
                return@launch
            }

            val cityName = mainRepository.appPreferences.get(locationKey) ?: DEFAULT_CITY

            _state.value = ShopsState.Content(
                shopItems,
                cityName
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("product") product: Product?
        ): ProductShopsViewModel
    }
}