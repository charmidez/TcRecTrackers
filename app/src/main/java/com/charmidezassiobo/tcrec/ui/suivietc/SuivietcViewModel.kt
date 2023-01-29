package com.charmidezassiobo.tcrec.ui.suivietc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SuivietcViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is suivietc Fragment"
    }
    val text: LiveData<String> = _text
}