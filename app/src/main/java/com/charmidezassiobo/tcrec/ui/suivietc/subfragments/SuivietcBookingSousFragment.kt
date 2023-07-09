

package com.charmidezassiobo.tcrec.ui.suivietc.subfragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.data.HeureStep
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBookingSousBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.setup.TCAdapter
import com.charmidezassiobo.tcrec.setup.TCBookingAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class SuivietcBookingSousFragment : Fragment() {

    private var _binding : FragmentSuivietcBookingSousBinding? = null
    private val binding get() = _binding!!

    //Variable Publique
    //Others class
    val allFun : AllFunctions = AllFunctions()
    var getData : GetDataFromDB = GetDataFromDB()

    var itemsTc : MutableList<Tc>  = getData.getTcAllList()
    var itemBookingList : ArrayList<String> = allFun.removeRedundance(getData.getListBookingWithTitle())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentSuivietcBookingSousBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val navController = findNavController()
        val mContext : Context = binding.root.context

        //View Variable
        var recyclerViewBooking = binding.recyclerViewBookingTc
        var spinnerView = binding.spinner
        var txtView_charging = binding.textViewChargingBooking
        var progressBar_view = binding.progressBarIdBooking

        //récupérer les données et bosser
        getData.updateTc {
            txtView_charging.isVisible  = false
            progressBar_view.setVisibility(View.GONE)

            recyclerViewBooking.visibility = View.VISIBLE

            itemBookingList = allFun.removeRedundance(itemBookingList)
            var arrayAdapter = ArrayAdapter(mContext, android.R.layout.simple_list_item_1,  itemBookingList)
            spinnerView.adapter = arrayAdapter

            spinnerView.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
                var selectedItem : String? = null
                var filteredTcList = mutableListOf<Tc>()
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedItem = itemBookingList[position]
                    if (selectedItem == "Tous les bookings"){
                        recyclerViewBooking.adapter = TCBookingAdapter( this@SuivietcBookingSousFragment, itemsTc)
                    } else {
                        filteredTcList = allFun.filterResult(selectedItem, itemsTc)
                        recyclerViewBooking.adapter = TCBookingAdapter( this@SuivietcBookingSousFragment, filteredTcList)
                    }


                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    txtView_charging.visibility = View.VISIBLE
                    progressBar_view.setVisibility(View.VISIBLE)
                    recyclerViewBooking.visibility = View.INVISIBLE
                }
            })

        }

        binding.btnBackToPreviousFragment.setOnClickListener {
            navController.popBackStack(R.id.navigation_suivietc, false)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}