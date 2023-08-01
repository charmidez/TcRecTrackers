package com.charmidezassiobo.tcrec.ui.reglages.bottomfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charmidezassiobo.tcrec.databinding.BottomSheetAddUserBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddUserBottomSheetFragment  : BottomSheetDialogFragment() {
    private var _binding : BottomSheetAddUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetAddUserBinding.inflate(inflater, container, false)
        val root : View = binding.root

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
