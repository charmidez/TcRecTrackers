package com.charmidezassiobo.tcrec.ui.suivietc.bottomfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charmidezassiobo.tcrec.databinding.BottomSheetAddPlombBinding
import com.charmidezassiobo.tcrec.setup.db.GetSeaData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddPlombBottomSheetFragment : BottomSheetDialogFragment(){
    private var _binding : BottomSheetAddPlombBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetAddPlombBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context

        //Recup all variable for Tc wish i have push
        val getSeaData = GetSeaData(mContext, null, null, null)

        val data = arguments
        var recupNumBooking = data?.getString("inputBooking")
        var recupNumTc1 =  data?.getString("inputTc")
        var recupNumTc2 =  data?.getString("inputTcSecond")

        var txtFieldTc1 = binding.textFieldPlombNumTc1
        var txtFieldTc2 = binding.textFieldPlombNumTc2
        var numPlombTc1Get = binding.textInputPlombNumTc1.text.toString()
        var numPlombTc2Get = binding.textInputPlombNumTc2.text.toString()
        var btnAddAllPlomb = binding.btnRecupPlombNum

        if (recupNumTc2.isNullOrBlank()){
            txtFieldTc2.visibility = View.GONE
        }

        btnAddAllPlomb.setOnClickListener {
            getSeaData.addPlombNum(recupNumBooking!!, recupNumTc1!!, recupNumTc2, numPlombTc1Get, numPlombTc2Get)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}