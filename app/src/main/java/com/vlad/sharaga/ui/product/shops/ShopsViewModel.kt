package com.vlad.sharaga.ui.product.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ShopItem
import com.vlad.sharaga.core.view.DEFAULT_CITY
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.data.MainRepository
import com.vlad.sharaga.data.preferences.locationKey
import com.vlad.sharaga.models.ProductPrice
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

@HiltViewModel(assistedFactory = ShopsViewModel.Factory::class)
class ShopsViewModel @AssistedInject constructor(
    @Assisted("productId") private val productId: ProductId?,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ShopsState>(ShopsState.Loading)
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            if (productId == null) {
                _state.value = ShopsState.Error("Product id is null")
                return@launch
            }

            val productPrices = mainRepository.apiClient.fetchProductPrices(productId)
            if (productPrices == null) {
                _state.value = ShopsState.Error("Failed to load product prices")
                return@launch
            }

            val shopItems = productPrices.map { productPrice: ProductPrice ->
                ShopItem(
                    logoUrl = productPrice.imageUrl,
                    price = productPrice.price,
                    deliveryDescription = productPrice.shopName,
                    deliveryPrice = productPrice.price,
                    url = productPrice.url
                )
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
            @Assisted("productId") productId: ProductId?
        ): ShopsViewModel
    }
}