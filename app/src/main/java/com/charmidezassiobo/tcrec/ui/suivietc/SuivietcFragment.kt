package com.charmidezassiobo.tcrec.ui.suivietc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.TCAdapter
import com.charmidezassiobo.tcrec.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBinding
import com.shuhart.stepview.StepView

class SuivietcFragment : Fragment() {

    private var _binding: FragmentSuivietcBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val suivietcViewModel =
            ViewModelProvider(this).get(SuivietcViewModel::class.java)

        _binding = FragmentSuivietcBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView_TC : RecyclerView = binding.recyclerViewSuivieTc

        val items_tc = listOf(

            Tc ( "MEDU1807661 20 DV", "TG-2253-AX"),
            Tc ( "MSDU2941187 20 DV", "TG-2835-X"),
            Tc ( "MSCU6870920 20 DV", "TG-6525-AI"),
            Tc ( "GLDU3457516 20 DV", "TG-2253-AX"),
            Tc ( "MSDU1807661 20 DV", "TG-0053-AM"),
            Tc ( "MSMU1805660 20 DV", "TG-2113-AP")

        )

        recyclerView_TC.apply {
            //recyclerView_TC.layoutManager = LinearLayoutManager(_binding)
            recyclerView_TC.adapter = TCAdapter(items_tc)
        }

        /*val textView: TextView = binding.textHome
        suivietcViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}