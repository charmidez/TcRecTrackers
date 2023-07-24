package com.charmidezassiobo.tcrec.ui.clientloginadmin.findtc

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentFindtcBinding
import com.charmidezassiobo.tcrec.setup.AllVariables

class FindtcFragment : Fragment() , OnBackPressedDispatcherOwner {

    private var _binding : FragmentFindtcBinding? = null
    private val binding get() = _binding!!

    private fun clearRadioBtn(v : RadioGroup){
        if(v.checkedRadioButtonId != -1){
            val rdBtn : RadioButton? = v.findViewById(v.checkedRadioButtonId)
            rdBtn?.isChecked = false
        }
    }

    private fun resetFragment(radioBtn1:RadioGroup, radioBtn2:RadioGroup, radioBtn3:RadioGroup, ln1:View,  rc:View?, ln2:View?){
        radioBtn1.visibility = View.VISIBLE
        radioBtn2.visibility = View.GONE
        radioBtn3.visibility = View.GONE
        ln1.visibility = View.GONE
        rc?.visibility = View.GONE
        ln2?.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFindtcBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context
        val navController = findNavController()
        val allVar = AllVariables()

        /*Début des déclarations des variables*/
        var btnInitialise = binding.imgViewBtnReset
        var radioGroupSeaAirRoad = binding.radioGroupAirSeaRoad
        var radioSeaTracking = binding.radioGroupSeaTracking
        var radioAirTracking = binding.radioGroupAirTracking
        var lnSearch = binding.lineairLayoutSearch
        var searchBarTc = binding.editTextSearchBar
        //var recyclerResult = binding.recyclerViewResult
        //var lnCardResult = binding.lineairLayoutResultRequest
        var btnBack = binding.btnBackToPreviousFragment
        var btnCallRec = binding.btnCallRec


        //Appui du premier radiogroup exp imp road btn
        radioGroupSeaAirRoad.setOnCheckedChangeListener{ group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when(selectOption){
                R.id.radioButton_air_trucking -> {
                    clearRadioBtn(radioSeaTracking)
                    resetFragment(radioGroupSeaAirRoad,radioSeaTracking, radioAirTracking,  lnSearch, null, null)
                    radioAirTracking.visibility = View.VISIBLE
                }
                R.id.radioButton_sea_trucking -> {
                    clearRadioBtn(radioAirTracking)
                    resetFragment(radioGroupSeaAirRoad,radioSeaTracking, radioAirTracking,  lnSearch, null, null)
                    radioSeaTracking.visibility = View.VISIBLE
                }
                R.id.radioButton_road_trucking -> {
                    resetFragment(radioGroupSeaAirRoad,radioSeaTracking, radioAirTracking,  lnSearch, null, null)
                    lnSearch.visibility = View.VISIBLE
                    searchBarTc.hint = "Numéro réference..."
                }
            }

        }

        //Appui du groupe de button Import
        radioAirTracking.setOnCheckedChangeListener{ group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when(selectOption){
                R.id.radioButton_air_exp -> {
                    lnSearch.visibility = View.VISIBLE
                    searchBarTc.hint = "Numéro MAWB ou HAWB... "
                }
                R.id.radioButton_air_imp -> {
                    lnSearch.visibility = View.VISIBLE
                    searchBarTc.hint = "Numéro MAWB ou HAWB..."
                }
            }
        }

        //Appui du groupe de button Export
        radioSeaTracking.setOnCheckedChangeListener{ group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when(selectOption){
                R.id.radioButton_sea_exp -> {
                    lnSearch.visibility = View.VISIBLE
                    searchBarTc.hint = "Numéro Booking ou TC..."
                }
                R.id.radioButton_sea_imp -> {
                    lnSearch.visibility = View.VISIBLE
                    searchBarTc.hint = "Numéro BL ou TC..."
                }
            }
        }

        //Reset The Search !
        btnInitialise.setOnClickListener {
            clearRadioBtn(radioGroupSeaAirRoad)
            resetFragment(radioGroupSeaAirRoad,radioSeaTracking, radioAirTracking,  lnSearch, null, null)
        }

        btnBack.setOnClickListener {
            navController.popBackStack(R.id.clientHomeFragment, false)
        }

        btnCallRec.setOnClickListener {
            val phoneNumber = allVar.CALL_REC_NUMBER
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$phoneNumber")
            mContext.startActivity(intent)
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                onStop()
                navController.popBackStack(R.id.clientHomeFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return root
    }

    override fun getOnBackPressedDispatcher(): OnBackPressedDispatcher {
        return requireActivity().onBackPressedDispatcher
    }

}