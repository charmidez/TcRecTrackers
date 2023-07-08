package com.charmidezassiobo.tcrec.ui.clientloginadmin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.data.QueryDatabaseHelper
import com.charmidezassiobo.tcrec.data.SearchWordDatabaseHelper
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentClientHomeBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.setup.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.setup.SearchAdapter
import com.charmidezassiobo.tcrec.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable


class ClientHomeFragment : Fragment() , RecyclerViewClickItemInterface {

    private var _binding : FragmentClientHomeBinding? = null
    private val binding get() = _binding!!

    val resultSousFragment : Fragment = ResultsearchFragment()
    lateinit var theAllFunctions : AllFunctions

    lateinit var itemsListTc : MutableList<Tc>

    lateinit var itemSearchingFound : List<String>

    lateinit var getData : GetDataFromDB

    lateinit var adapter : SearchAdapter

    lateinit var getSearchingWord : SearchWordDatabaseHelper

    lateinit var sharedPreferences : SharedPreferences

    lateinit var resultFunSearch : MutableList<Tc>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientHomeBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context

        //var resultFunSearch : MutableList<Tc>
        var recyclerSearcWord : RecyclerView

        var navController = findNavController()
        theAllFunctions = AllFunctions()
        getData = GetDataFromDB()

        getSearchingWord = SearchWordDatabaseHelper(mContext)

        itemsListTc = ArrayList()
        resultFunSearch = mutableListOf()

        recyclerSearcWord = binding.recyclerViewSearchItem

        getData.updateTc { itemsListTc = getData.getTcAllList() }

        binding.btnSearch.setOnClickListener {
            var inputSearchingWord = binding.editTextSearchView.text.toString()

            if ( inputSearchingWord.length < 6){
                val snack = Snackbar.make(binding.root,"Veuillez saisir un numéro Conteneur ou Booking", Snackbar.LENGTH_LONG)
                snack.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                snack.setBackgroundTint(ContextCompat.getColor(mContext, R.color.gray2))
                snack.show()
            } else {
                resultFunSearch = theAllFunctions.filterResult(inputSearchingWord, itemsListTc)
                when (resultFunSearch.isEmpty()){
                    true -> {
                        val snack = Snackbar.make(binding.root,"Conteneur ou Booking introuvable", Snackbar.LENGTH_LONG)
                        snack.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                        snack.setBackgroundTint(ContextCompat.getColor(mContext, R.color.gray2))
                        snack.show()
                    }
                    false -> {
                        val bundle = Bundle()
                        bundle.putString("inputSearchingWord",inputSearchingWord)
                        bundle.putSerializable("resultFunSearch", resultFunSearch as Serializable)
                        inputSearchingWordInData(mContext, inputSearchingWord)
                        resultSousFragment.arguments = bundle
                        navController.navigate(R.id.action_clientHomeFragment_to_resultsearchFragment, bundle)
                    }
                }
            }
        }

        //réservez à l'administration
        binding.btnPersonnel.setOnClickListener {

            sharedPreferences = mContext.getSharedPreferences("app_state", Context.MODE_PRIVATE)
            val isAuthenticated = sharedPreferences.getBoolean("is_authenticated", false)
            if (isAuthenticated){
                val i = Intent(mContext, MainActivity::class.java)
                startActivity(i)
            } else {
                navController.navigate(R.id.action_clientHomeFragment_to_adminloginFragment)
            }
        }

        // recherche approfondi
        binding.btnFinder.setOnClickListener {
            navController.navigate(R.id.action_clientHomeFragment_to_navigation_findtc)
        }

        var motChercher = getSearchingWordInData()

        if (motChercher.isEmpty()){
            recyclerSearcWord.visibility = View.GONE
        } else {
            adapter = SearchAdapter(mContext, motChercher, this@ClientHomeFragment)
            Log.d("motChercher", motChercher.toString())
            recyclerSearcWord.adapter = adapter
        }

        return root
    }


    fun inputSearchingWordInData(context: Context, searchWord : String){
        getSearchingWord = SearchWordDatabaseHelper(context)

        // Sauvegarder un mot de recherche
        val insertedId = getSearchingWord.saveSearchWord(searchWord)
        if (insertedId != -1L) {
            Log.d("SearchWord", "Mot de recherche enregistré avec succès, ID = $insertedId")
        } else {
            Log.d("SearchWord", "Erreur lors de l'enregistrement du mot de recherche")
        }
    }

    fun getSearchingWordInData() : List<String>{
        // Récupérer tous les mots de recherche
        var searchWords = getSearchingWord.getAllSearchWords()
        val set : Set<String> = searchWords.toHashSet()
        val searchWordWhithoutDoublons = mutableListOf<String>()
        searchWordWhithoutDoublons.addAll(set)
        searchWords = searchWordWhithoutDoublons
        Log.d("SearchWord", "Liste des mots de recherche : $searchWords")

        return searchWords
    }

    override fun onItemClick(position: Int) {
        val navController = findNavController()

        itemSearchingFound = getSearchingWordInData()

        val inputSearchingWord = itemSearchingFound[position]

        Log.d("itemSearchingWord", inputSearchingWord)
        resultFunSearch = theAllFunctions.filterResult(inputSearchingWord, itemsListTc)

        val mContext = binding.root.context
        val bundle = Bundle()
        bundle.putString("inputSearchingWord",inputSearchingWord)
        bundle.putSerializable("resultFunSearch", resultFunSearch as Serializable)
        inputSearchingWordInData(mContext, inputSearchingWord)
        resultSousFragment.arguments = bundle
        navController.navigate(R.id.action_clientHomeFragment_to_resultsearchFragment, bundle)
    }
}