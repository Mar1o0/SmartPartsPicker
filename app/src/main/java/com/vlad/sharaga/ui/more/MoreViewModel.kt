package com.vlad.sharaga.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(

) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is more Fragment"
    }
    val text: LiveData<String> = _text
}