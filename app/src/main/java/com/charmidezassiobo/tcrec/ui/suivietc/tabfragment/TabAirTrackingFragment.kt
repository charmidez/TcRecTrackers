package com.charmidezassiobo.tcrec.ui.suivietc.tabfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charmidezassiobo.tcrec.databinding.FragmentTabAirTrackingBinding

class TabAirTrackingFragment : Fragment() {

    private var _binding : FragmentTabAirTrackingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabAirTrackingBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context



        return root
    }
}