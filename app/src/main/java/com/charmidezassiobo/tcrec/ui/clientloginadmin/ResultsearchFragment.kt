package com.charmidezassiobo.tcrec.ui.clientloginadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentResultsearchBinding
import com.charmidezassiobo.tcrec.dataclass.Sea
import com.charmidezassiobo.tcrec.setup.Adapter.TCResultAdapter


class ResultsearchFragment : Fragment(), OnBackPressedDispatcherOwner {

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
        var listResult : MutableList<Sea>
        var adapter : TCResultAdapter
        var searchingWord : String

        listResult = mutableListOf<Sea>()

        val data = arguments
        searchingWord = data?.getString("inputSearchingWord").toString()
        listResult = data!!.getSerializable("resultFunSearch") as MutableList<Sea>

        txtViewAfficheTitre.text = searchingWord

        adapter = TCResultAdapter(mContext, listResult)
        recyclerViewResult.adapter = adapter

        binding.btnBackToPreviousFragment.setOnClickListener {
            navController.popBackStack(R.id.clientHomeFragment, false)
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                onStop()
                navController.popBackStack(R.id.clientHomeFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        return root
    }

    override fun getOnBackPressedDispatcher(): OnBackPressedDispatcher {
        return requireActivity().onBackPressedDispatcher
    }
}