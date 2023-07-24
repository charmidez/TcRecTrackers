package com.charmidezassiobo.tcrec.ui.ajoutertc.subfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentUpdateStepSubBinding

// Cette classe va servir à modifier  les step selon les types de voyages
class UpdateStepSubFragment : Fragment() {

    private var _binding : FragmentUpdateStepSubBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpdateStepSubBinding.inflate(inflater, container, false )
        val root : View = binding.root
        val mContext = binding.root.context
        var navController = findNavController()

        val btnBack = binding.btnBackToPreviousFragment
        btnBack.setOnClickListener {
            navController.popBackStack(R.id.navigation_ajoutertc, false)
        }



        return root
    }
}