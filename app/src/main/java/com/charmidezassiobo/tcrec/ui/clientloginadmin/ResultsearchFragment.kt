package com.charmidezassiobo.tcrec.ui.clientloginadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentResultsearchBinding
import com.charmidezassiobo.tcrec.setup.TCResultAdapter


class ResultsearchFragment : Fragment() {

    private var _binding : FragmentResultsearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsearchBinding.inflate(inflater, container, false)
        val root : View = binding.root
        var navController = findNavController()
        val mContext = binding.root.context

        var txtViewAfficheTitre = binding.txtViewNumbookingChercherResult
        var recyclerViewResult = binding.recyclerViewResult
        var listResult : MutableList<Tc>
        var adapter : TCResultAdapter
        var searchingWord : String

        listResult = mutableListOf<Tc>()

        val data = arguments
        searchingWord = data?.getString("inputSearchingWord").toString()
        listResult = data!!.getSerializable("resultFunSearch") as MutableList<Tc>

        txtViewAfficheTitre.text = searchingWord

        adapter = TCResultAdapter(mContext, listResult)
        recyclerViewResult.adapter = adapter

        binding.btnBackToPreviousFragment.setOnClickListener {
            navController.popBackStack(R.id.clientHomeFragment, false)
        }


        return root
    }
}