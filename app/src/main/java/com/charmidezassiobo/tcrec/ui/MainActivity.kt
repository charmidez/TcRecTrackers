package com.charmidezassiobo.tcrec.ui

import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
//import com.charmidezassiobo.tcrec.Manifest
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Autoriser internet
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, Array(1) { android.Manifest.permission.INTERNET},8427)
        }

        //FireStore
        FirebaseApp.initializeApp(this)
        val db = Firebase.firestore


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        //val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_suivietc, R.id.navigation_ajoutertc, R.id.navigation_reglages))

        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 8427 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }
}