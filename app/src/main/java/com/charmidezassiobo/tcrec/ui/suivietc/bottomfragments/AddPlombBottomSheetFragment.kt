package com.charmidezassiobo.tcrec.ui.suivietc.bottomfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.BottomSheetAddPlombBinding
import com.charmidezassiobo.tcrec.setup.data.Sea
import com.charmidezassiobo.tcrec.setup.db.seadata.GetSeaData
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

        var sea = Sea()

        //Recup all variable for Tc wish i have push
        val getSeaData = GetSeaData(sea)

        val data = arguments
        var recupNumBooking = data?.getString("inputBooking")
        var recupNumTc1 =  data?.getString("inputTc")
        var recupNumTc2 =  data?.getString("inputTcSecond")

        var txtFieldTc1 = binding.textFieldPlombNumTc1
        var txtFieldTc2 = binding.textFieldPlombNumTc2
        val numPlombInput1 =  binding.textInputPlombNumTc1
        val numPlombInput2 =  binding.textInputPlombNumTc2
        var numPlombTc1Get  : String
        var numPlombTc2Get : String
        var btnAddAllPlomb = binding.btnRecupPlombNum



        if (recupNumTc2.isNullOrBlank()){
            txtFieldTc2.visibility = View.GONE
        }

        btnAddAllPlomb.setOnClickListener {
            numPlombTc1Get = numPlombInput1.text.toString()
            numPlombTc2Get =  numPlombInput2.text.toString()
            getSeaData.addPlombNum(recupNumBooking!!, recupNumTc1!!, recupNumTc2, numPlombTc1Get, numPlombTc2Get)
            Log.d("PLOMB1_SAISIE","$numPlombTc1Get")
            Log.d("PLOMB2_SAISIE","$numPlombTc2Get")

            numPlombInput1.text?.clear()
            numPlombInput2.text?.clear()

            /*
            * but.setBackgroundResource(R.drawable.btn_drawable_red)
                but.text = getText(R.string.but_addtc).toString()*/



            btnAddAllPlomb.setBackgroundResource(R.drawable.btn_drawable_not_selected)
            btnAddAllPlomb.text = "PLOMB AJOUTÉ"

        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}