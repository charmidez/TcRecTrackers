package com.charmidezassiobo.tcrec.ui.suivietc.bottomfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charmidezassiobo.tcrec.databinding.BottomSheetShowStepDateBinding
import com.charmidezassiobo.tcrec.setup.Adapter.ShowStepAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShowStepDateBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding : BottomSheetShowStepDateBinding? = null
    private val binding get() = _binding!!

    companion object{
        private const val ARG_DATA_LIST = "dataList"
        fun newInstance(dataList: ArrayList<String>) : ShowStepDateBottomSheetFragment{
            val args = Bundle()
            args.putStringArrayList(ARG_DATA_LIST, dataList)
            val fragment = ShowStepDateBottomSheetFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = BottomSheetShowStepDateBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context

        val listStepDesc = arguments?.getStringArrayList(ARG_DATA_LIST) as ArrayList<String>

        var recyclerViewShowStepDate = binding.recyclerViewStepDate
        recyclerViewShowStepDate.adapter = ShowStepAdapter(mContext, listStepDesc)

        return root
    }

}