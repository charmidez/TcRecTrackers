package com.charmidezassiobo.tcrec.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.charmidezassiobo.tcrec.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login

    }

}
