package com.charmidezassiobo.tcrec.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.databinding.ActivityMainBinding
import com.charmidezassiobo.tcrec.ui.suivietc.SuivietcSousFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 1
    private val CALL_PHONE_PERMISSION_CODE = 1

    private val repo = GetDataFromDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Autoriser stockage
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_PERMISSION_CODE)
        } else {
            Log.d("Storage","Storage Ok")
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                WRITE_EXTERNAL_STORAGE_PERMISSION_CODE)
        } else {
            Log.d("Call Phone","Phone Call Ok")
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener{_,destination,_->
            if (  destination.id == R.id.navigation_findtc ||
                    destination.id == R.id.navigation_suivietc ||
                    destination.id == R.id.navigation_ajoutertc ||
                    destination.id == R.id.navigation_reglages
                    )
                navView.visibility = View.VISIBLE
            else {
                navView.visibility = View.GONE
            }
        }
        repo.updateTc {
            navView.setupWithNavController(navController)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Storage on Request","Storage Ok")
            } else {
                // La permission a été refusée, vous ne pouvez pas écrire sur le stockage
                Log.d("Storage on Request","Storage Pas Ok")
            }
        }

        if(requestCode == CALL_PHONE_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Call on Request","Call Phone Ok")
            } else {
                Log.d("Call on Request","Call Phone Pas Ok")
            }
        }


    }
}