

package com.charmidezassiobo.tcrec.ui.suivietc.subfragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.db.seadata.GetSeaData
import com.charmidezassiobo.tcrec.setup.data.Sea
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBookingSousBinding
import com.charmidezassiobo.tcrec.setup.Adapter.TCBookingAdapter
import com.charmidezassiobo.tcrec.setup.functions.AllFunctions
import com.charmidezassiobo.tcrec.setup.interfaces.RecyclerViewClickItemInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuivietcBookingSousFragment : Fragment(), RecyclerViewClickItemInterface {

    private var _binding : FragmentSuivietcBookingSousBinding? = null
    private val binding get() = _binding!!

    //Variable Publique
    //Others class
    val allFun : AllFunctions = AllFunctions()
    var sea = Sea()
    var getData : GetSeaData = GetSeaData(sea)

    var itemsTc : MutableList<Sea>  = getData.retrieveSea()
    var itemBookingList : ArrayList<String> = allFun.removeRedundance(getData.retrieveSeaBookingList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentSuivietcBookingSousBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val navController = findNavController()
        val mContext : Context = binding.root.context

        //View Variable
        var recyclerViewBooking = binding.recyclerViewBookingTc
        var spinnerView = binding.spinner
        var chargement = binding.textViewChargingBooking

        CoroutineScope(Dispatchers.IO).launch{
            var dataSea = GetSeaData(sea )
            dataSea.retrieveSeaBookingList()

        }

        val adapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_dropdown_item, itemBookingList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerView.adapter = adapter


        spinnerView.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            var selectedItem : String? = null
            var filteredTcList = mutableListOf<Sea>()

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = itemBookingList[position]
                if (selectedItem == "Tous les bookings"){
                    recyclerViewBooking.adapter = TCBookingAdapter( this@SuivietcBookingSousFragment, itemsTc)
                } else {
                    filteredTcList = allFun.filterResultSea(selectedItem, itemsTc)
                    recyclerViewBooking.adapter = TCBookingAdapter( this@SuivietcBookingSousFragment, filteredTcList)
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                chargement.visibility = View.VISIBLE
                recyclerViewBooking.visibility = View.INVISIBLE
            }
        })


        binding.btnBackToPreviousFragment.setOnClickListener {
            navController.popBackStack(R.id.navigation_suivietc, false)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        Log.d("FATIMA","${itemBookingList.get(position)}")
    }

    override fun onLongClickListener(position: Int) {
        TODO("Not yet implemented")
    }

}