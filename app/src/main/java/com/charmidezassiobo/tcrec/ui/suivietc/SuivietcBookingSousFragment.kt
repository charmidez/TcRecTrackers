

package com.charmidezassiobo.tcrec.ui.suivietc

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
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBookingSousBinding
import com.charmidezassiobo.tcrec.setup.TCAdapter
import com.charmidezassiobo.tcrec.setup.TCBookingAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class SuivietcBookingSousFragment : Fragment() {

    private var _binding : FragmentSuivietcBookingSousBinding? = null
    private val binding get() = _binding!!

    var items_tc : MutableList<Tc> = ArrayList()
    lateinit var getData : GetDataFromDB
    lateinit var adapter : TCBookingAdapter

    private fun filterList(query : String?, recyclerView_TC : RecyclerView){

        if (query  != null ){
            val filteredList = ArrayList<Tc>()

            for (i in items_tc){

                if(i.num_booking.lowercase(Locale.ROOT).contains(query) || i.num_booking.uppercase(
                        Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                recyclerView_TC.adapter = TCBookingAdapter(this@SuivietcBookingSousFragment, items_tc)
            } else {
                recyclerView_TC.adapter = TCBookingAdapter( this@SuivietcBookingSousFragment, filteredList)
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentSuivietcBookingSousBinding.inflate(inflater, container, false)
        val root : View = binding.root

        var recyclerViewBooking = binding.recyclerViewBookingTc

        var bookingList = ArrayList<String>()
        var bookingListRD : List<String>

        var spinner = binding.spinner
        var txtView_charging = binding.textViewChargingBooking
        var progressBar_view = binding.progressBarIdBooking
        val context : Context
        context = requireContext()

        getData = GetDataFromDB()
        items_tc = getData.itemListTc
        adapter = TCBookingAdapter(this@SuivietcBookingSousFragment, items_tc)

        bookingListRD = listOf()
        getData.updateTc {
            txtView_charging.isVisible  = false
            progressBar_view.setVisibility(View.GONE)
            recyclerViewBooking.adapter = adapter
        }

        getData.updateTc {
            bookingList = getData.listBooking
            //bookingListRD = bookingList.distinct().toList()
            val set: Set<String> =  bookingList.toHashSet()
            bookingList.clear()
            bookingList.add("Tous les TC")
            bookingList.addAll(set)

            val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, bookingList)
            spinner.adapter  = adapter
        }
        bookingList.add("")

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            var selectedItem : String? = null
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = bookingList[position]
                Log.d("NumBooking","$selectedItem")
                filterList(selectedItem, recyclerViewBooking)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedItem = null
                filterList(selectedItem, recyclerViewBooking)
            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}