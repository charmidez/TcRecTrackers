package com.charmidezassiobo.tcrec.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.charmidezassiobo.tcrec.R

class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed(Runnable {
            val i = Intent(this@SplashscreenActivity, BaseActivity::class.java)
            startActivity(i)
            finish()
        }, 1000)
    }
}