package com.vlad.sharaga.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(

) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is catalog Fragment"
    }
    val text: LiveData<String> = _text
}