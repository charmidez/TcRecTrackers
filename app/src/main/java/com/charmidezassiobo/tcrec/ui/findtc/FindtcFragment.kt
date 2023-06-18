package com.charmidezassiobo.tcrec.ui.findtc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentFindtcBinding

class FindtcFragment : Fragment() {


    private var _binding : FragmentFindtcBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /* _binding = FragmentAjoutertcBinding.inflate(inflater, container, false)
        val root: View = binding.root*/
        _binding = FragmentFindtcBinding.inflate(inflater, container, false)
        val root : View = binding.root




        return root
    }

}