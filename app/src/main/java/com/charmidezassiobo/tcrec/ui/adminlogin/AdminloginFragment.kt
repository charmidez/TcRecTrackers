package com.charmidezassiobo.tcrec.ui.adminlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentAdminloginBinding


class AdminloginFragment : Fragment() {

    private  var _binding : FragmentAdminloginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminloginBinding.inflate(inflater, container, false)
        val root : View = binding.root
        var navController = findNavController()

        binding.btnBackToPreviousFragment.setOnClickListener {
            navController.popBackStack(R.id.clientHomeFragment, false)
        }

        return root
    }
}