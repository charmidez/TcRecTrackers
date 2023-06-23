package com.charmidezassiobo.tcrec.ui.suivietc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcStepdateSousBinding

class SuivietcStepdateSousFragment : Fragment() {

    private var _binding : FragmentSuivietcStepdateSousBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSuivietcStepdateSousBinding.inflate(inflater,container,false)
        val root : View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}