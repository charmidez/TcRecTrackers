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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = BottomSheetShowStepDateBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context

        //Récup list
        val data = arguments
        //var listStepDesc = data?.getStringArrayList("listStepDescription")
        var listStepDesc : ArrayList<String>
        listStepDesc = (data?.getSerializable("listStepDescription")!! as? ArrayList<String>)!!

        Log.d("listStepDescRecup", listStepDesc.toString())
        var itemTest = arrayListOf<String>("Elément un", "Elément deux", "Element trois", "Element quatre")

        var recyclerViewShowStepDate = binding.recyclerViewStepDate
        //recyclerViewShowStepDate.adapter = listStepDesc?.let { ShowStepAdapter(mContext, it) }
        recyclerViewShowStepDate.adapter = ShowStepAdapter(mContext, listStepDesc!!)

        return root
    }

}