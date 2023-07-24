package com.charmidezassiobo.tcrec.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBaseBinding

    //lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}