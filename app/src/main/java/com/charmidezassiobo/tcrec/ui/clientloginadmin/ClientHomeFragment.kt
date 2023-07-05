package com.charmidezassiobo.tcrec.ui.clientloginadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentClientHomeBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.google.android.material.snackbar.Snackbar


class ClientHomeFragment : Fragment() {

    private var _binding : FragmentClientHomeBinding? = null
    private val binding get() = _binding!!

    val resultSousFragment : Fragment = ResultsearchFragment()
    lateinit var theAllFunctions : AllFunctions

    lateinit var itemsListTc : MutableList<Tc>
    lateinit var getData : GetDataFromDB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientHomeBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context

        var resultFunSearch : Boolean

        var navController = findNavController()
        theAllFunctions = AllFunctions()
        getData = GetDataFromDB()

        itemsListTc = ArrayList()

        getData.updateTc { itemsListTc = getData.itemListTc }

        binding.btnSearch.setOnClickListener {
            var inputSearchingWord = binding.editTextSearchView.text.toString()

            if ( inputSearchingWord.length<6){
                val snack = Snackbar.make(binding.root,"Veuillez saisir un numéro Conteneur ou Booking", Snackbar.LENGTH_LONG)
                snack.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                snack.setBackgroundTint(ContextCompat.getColor(mContext, R.color.gray2))
                snack.show()
            } else {
                resultFunSearch = theAllFunctions.filterResult(inputSearchingWord, itemsListTc)
                when (resultFunSearch){
                    false -> {
                        val snack = Snackbar.make(binding.root,"Conteneur ou Booking introuvable", Snackbar.LENGTH_LONG)
                        snack.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                        snack.setBackgroundTint(ContextCompat.getColor(mContext, R.color.gray2))
                        snack.show()
                    }
                    true -> {
                        val bundle = Bundle()
                        bundle.putString("inputSearchingWord",inputSearchingWord)
                        resultSousFragment.arguments = bundle
                        navController.navigate(R.id.action_clientHomeFragment_to_resultsearchFragment)
                    }
                }
            }
        }



        //réservez à l'administration
        binding.btnPersonnel.setOnClickListener {
            navController.navigate(R.id.action_clientHomeFragment_to_adminloginFragment)
        }



        return root
    }
}