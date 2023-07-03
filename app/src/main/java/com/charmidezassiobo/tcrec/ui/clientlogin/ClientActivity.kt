package com.charmidezassiobo.tcrec.ui.clientlogin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.ActivityClientBinding
import com.charmidezassiobo.tcrec.setup.TCBookingAdapter
import com.charmidezassiobo.tcrec.setup.TCResultAdapter
import com.charmidezassiobo.tcrec.ui.LoginActivity
import com.charmidezassiobo.tcrec.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

private lateinit var binding : ActivityClientBinding

class ClientActivity : AppCompatActivity() {

    var items_tc : MutableList<Tc> = ArrayList()
    lateinit var getData : GetDataFromDB
    lateinit var adapter : TCResultAdapter

    private fun filterListClientResult( query : String?, recyclerView_TC : RecyclerView){

        if (query  != null ){
            val filteredList = ArrayList<Tc>()
            for (i in items_tc){
                if(i.num_booking.lowercase(Locale.ROOT).contains(query) || i.num_booking.uppercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
                if(i.num_TC.lowercase(Locale.ROOT).contains(query) || i.num_TC.uppercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
                if(i.num_TCSecond.lowercase(Locale.ROOT).contains(query) || i.num_TCSecond.uppercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                //recyclerView_TC.adapter = TCResultAdapter(this@ClientActivity, items_tc)
                recyclerView_TC.visibility = View.GONE
                val snack = Snackbar.make(binding.root,"Conteneur ou Booking introuvable", Snackbar.LENGTH_LONG)
                snack.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                snack.setBackgroundTint(ContextCompat.getColor(applicationContext, R.color.gray2))
                snack.show()
            } else {
                recyclerView_TC.adapter = TCResultAdapter( this@ClientActivity, filteredList)
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                recyclerView_TC.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackToPreviousFragment.setOnClickListener {
            val i = Intent(this@ClientActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }

        var recyclerViewResult = binding.recyclerViewResult
        recyclerViewResult.visibility = View.GONE

        var imgCargaison = binding.imgViewCargaison

        getData = GetDataFromDB()
        items_tc = getData.itemListTc
        adapter = TCResultAdapter(this, items_tc)

        getData.updateTc {
            recyclerViewResult.adapter = adapter
        }


        var  searchViewBar = binding.editTextSearchView
        var btnSearch = binding.btnSearch
        var lnSearch = binding.lineairLayoutSearchBar
        var btnNewSearch = binding.btnNouvelleRecherche
        var txtViewNotaBene = binding.txtViewNotabebe
        btnNewSearch.visibility = View.GONE

        btnSearch.setOnClickListener {
            var getSearchQuery = searchViewBar.text.toString()
            if (getSearchQuery.length<7){
                val snack = Snackbar.make(binding.root,"Veuillez saisir un numéro Conteneur ou Booking", Snackbar.LENGTH_LONG)
                snack.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                snack.setBackgroundTint(ContextCompat.getColor(applicationContext, R.color.gray2))
                snack.show()
            } else {
                filterListClientResult(getSearchQuery, recyclerViewResult).apply {
                    imgCargaison.visibility = View.GONE
                    lnSearch.visibility = View.GONE
                    txtViewNotaBene.visibility = View.GONE
                    btnNewSearch.visibility = View.VISIBLE
                }
            }
        }

        btnNewSearch.setOnClickListener {
            imgCargaison.visibility = View.VISIBLE
            lnSearch.visibility = View.VISIBLE
            searchViewBar.setText("")
            recyclerViewResult.visibility = View.GONE
            btnNewSearch.visibility = View.GONE
            txtViewNotaBene.visibility = View.VISIBLE
        }

    }

}