package com.charmidezassiobo.tcrec.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.ActivityLoginBinding
import com.charmidezassiobo.tcrec.ui.clientlogin.ClientActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences("app_state",Context.MODE_PRIVATE)
        val isAuthenticated = sharedPreferences.getBoolean("is_authenticated", false)
        if (isAuthenticated){
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        val username = binding.editTextUsername
        val password = binding.editTextPassword
        val loginBtn = binding.loginButton

        //sharedPreferences = this.getSharedPreferences("app_state",Context.MODE_PRIVATE)


        val lineairLayoutAdmin = binding.lineairLayoutAdmin

        val radioBtnGroup = binding.radioGroupAdminClient
        radioBtnGroup.setOnCheckedChangeListener { group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when(selectOption){
                R.id.radio_button_admin -> {
                    lineairLayoutAdmin.visibility = View.VISIBLE

                }
                R.id.radio_button_client -> {
                    lineairLayoutAdmin.visibility = View.INVISIBLE
                    //Aller sur la page de client en même temps
                    val i = Intent(this@LoginActivity, ClientActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }

        }

        loginBtn.setOnClickListener {
            var recupUsername = username.text.toString()
            var recupPassword = password.text.toString()
            if (username.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                val snack = Snackbar.make(binding.root,"Veuillez entrer votre nom d'utilisateur et mot de passe", Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(ContextCompat.getColor(this, R.color.gray2))
                snack.show()
            } else if (recupUsername == "recAdmin" || recupPassword == "recMotDePasse") {
                username.setText("")
                password.setText("")

                //enregistement dans les shared préferences le booleean isAuthentica
                val editor = sharedPreferences.edit()
                editor.putBoolean("is_authenticated",true).apply()

                val i = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }

    }

}
