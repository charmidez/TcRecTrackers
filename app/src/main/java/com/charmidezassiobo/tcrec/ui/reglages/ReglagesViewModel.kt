package com.charmidezassiobo.tcrec.ui.reglages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReglagesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is reglages Fragment"
    }
    val text: LiveData<String> = _text
}