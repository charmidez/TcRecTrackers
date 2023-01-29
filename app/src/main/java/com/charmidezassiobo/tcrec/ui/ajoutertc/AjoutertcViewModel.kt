package com.charmidezassiobo.tcrec.ui.ajoutertc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AjoutertcViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Chargement..."
    }
    val text: LiveData<String> = _text
}