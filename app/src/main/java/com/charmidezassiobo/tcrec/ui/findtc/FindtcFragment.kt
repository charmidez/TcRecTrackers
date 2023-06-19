package com.charmidezassiobo.tcrec.ui.findtc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentFindtcBinding

class FindtcFragment : Fragment() {


    private var _binding : FragmentFindtcBinding? = null
    private val binding get() = _binding!!

    private fun resetFragment(vBase:View, v2:View, v3:View, v4:View,  v5:View, v6:View){
        vBase.visibility = View.VISIBLE
        v2.visibility = View.GONE
        v3.visibility = View.GONE
        v4.visibility = View.GONE
        v5.visibility = View.GONE
        v6.visibility = View.GONE
        //v7.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFindtcBinding.inflate(inflater, container, false)
        val root : View = binding.root

        /*Début des déclarations des variables*/
        var btnInitialise = binding.imgViewBtnReset
        var radioGroupExpImpRoad = binding.radioGroupImpExpRoad
        var radioExpTracking = binding.radioGroupExportTracking
        var radioImpTracking = binding.radioGroupImportTracking
        var lnSearch = binding.lineairLayoutSearch
        var recyclerResult = binding.recyclerViewResult
        var lnCardResult = binding.lineairLayoutResultRequest




        btnInitialise.setOnClickListener {
            resetFragment(radioGroupExpImpRoad,radioExpTracking, radioImpTracking,  lnSearch, recyclerResult, lnCardResult)
        }

        return root
    }

}