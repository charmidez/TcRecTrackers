package com.charmidezassiobo.tcrec.ui.clientloginadmin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.db.SearchWordDatabaseHelper
import com.charmidezassiobo.tcrec.setup.dataclass.Sea
import com.charmidezassiobo.tcrec.databinding.FragmentClientHomeBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.setup.interfaces.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.setup.Adapter.SearchAdapter
import com.charmidezassiobo.tcrec.setup.db.GetSeaData
import com.charmidezassiobo.tcrec.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class ClientHomeFragment : Fragment(), OnBackPressedDispatcherOwner,
    RecyclerViewClickItemInterface {

    private var _binding: FragmentClientHomeBinding? = null
    private val binding get() = _binding!!

    val resultSousFragment: Fragment = ResultsearchFragment()
    lateinit var theAllFunctions: AllFunctions

    lateinit var itemsListSea: MutableList<Sea>
    lateinit var itemSearchingFound: List<String>
    //lateinit var getData: GetDataFromDB
    lateinit var getSea : GetSeaData
    lateinit var sea : Sea
    lateinit var adapter: SearchAdapter
    lateinit var getSearchingWord: SearchWordDatabaseHelper
    lateinit var sharedPreferences: SharedPreferences

    lateinit var resultFunSearch: MutableList<Sea>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mContext = binding.root.context

        //var resultFunSearch : MutableList<Tc>
        var recyclerSearcWord: RecyclerView

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val activeNetworkInfo = connectivityManager.activeNetwork
        val isConnected = activeNetworkInfo != null /*&& activeNetworkInfo.isConnectedOrConnecting*/

        var navController = findNavController()
        theAllFunctions = AllFunctions()
        //getData = GetDataFromDB()
        sea = Sea()
        getSea = GetSeaData(mContext, this@ClientHomeFragment, sea, null)

        getSearchingWord = SearchWordDatabaseHelper(mContext)

        var refrshPage = binding.refreshClientSearch
        var btnSearch = binding.btnSearch
        var editTextSearchView = binding.editTextSearchView
        var btnAdmin = binding.btnPersonnel
        var btnFind = binding.btnFinder

        itemsListSea = ArrayList()
        resultFunSearch = mutableListOf()

        recyclerSearcWord = binding.recyclerViewSearchItem

        //getData.seaCallBack { itemsListSea = getData.getSEAdataFromdb() }
        CoroutineScope(Dispatchers.Main).launch{
            itemsListSea = getSea.getItemList().await()

        }

        //button de recherche
        btnSearch.setOnClickListener {
            var inputSearchingWord = binding.editTextSearchView.text.toString()

            if (inputSearchingWord.isNullOrBlank()) {
                theAllFunctions.snackBarShowWarning(mContext, root, "Veuillez saisir un numéro Conteneur ou Booking")
            } else {
                resultFunSearch = theAllFunctions.filterResultSea(inputSearchingWord, itemsListSea)
                when (resultFunSearch.isEmpty()) {
                    true -> {
                        theAllFunctions.snackBarShowWarning(mContext, root, "Conteneur ou Booking introuvable")
                    }

                    false -> {
                        val bundle = Bundle()
                        bundle.putString("inputSearchingWord", inputSearchingWord)
                        bundle.putSerializable("resultFunSearch", resultFunSearch as Serializable)
                        inputSearchingWordInData(mContext, inputSearchingWord)
                        resultSousFragment.arguments = bundle
                        editTextSearchView.setText("")
                        navController.navigate(
                            R.id.action_clientHomeFragment_to_resultsearchFragment,
                            bundle
                        )
                    }
                }
            }
        }

        //réservez à l'administration
        btnAdmin.setOnClickListener {

            sharedPreferences = mContext.getSharedPreferences("app_state", Context.MODE_PRIVATE)
            val isAuthenticated = sharedPreferences.getBoolean("is_authenticated", false)
            if (isAuthenticated) {
                val i = Intent(mContext, MainActivity::class.java)
                startActivity(i)
            } else {
                navController.navigate(R.id.action_clientHomeFragment_to_adminloginFragment)
            }
        }

        // recherche approfondi
        btnFind.setOnClickListener {
            navController.navigate(R.id.action_clientHomeFragment_to_navigation_findtc)
        }

        //refrshPage
        refrshPage.setOnRefreshListener {
            if (isConnected){
                //Connection Internet
                refrshPage.isRefreshing = false
                theAllFunctions.snackBarShowSucces(mContext, root, "Page mis à jour avec succès")
            } else {
                // Pas de connexion Internet
                refrshPage.isRefreshing = false
                theAllFunctions.snackBarShowWarning(mContext, root, "Veuillez vous connecter à internet")
            }
        }

        var motChercher = getSearchingWordInData()

        if (motChercher.isEmpty()) {
            recyclerSearcWord.visibility = View.GONE
        } else {
            adapter = SearchAdapter(mContext, motChercher, this@ClientHomeFragment)
            Log.d("motChercher", motChercher.toString())
            recyclerSearcWord.adapter = adapter
        }


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
                activity?.finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        return root
    }

    fun inputSearchingWordInData(context: Context, searchWord: String) {
        getSearchingWord = SearchWordDatabaseHelper(context)

        // Sauvegarder un mot de recherche
        val insertedId = getSearchingWord.saveSearchWord(searchWord)
        if (insertedId != -1L) {
            Log.d("SearchWord", "Mot de recherche enregistré avec succès, ID = $insertedId")
        } else {
            Log.d("SearchWord", "Erreur lors de l'enregistrement du mot de recherche")
        }
    }

    fun getSearchingWordInData(): List<String> {
        // Récupérer tous les mots de recherche
        var searchWords = getSearchingWord.getAllSearchWords()
        val set: Set<String> = searchWords.toHashSet()
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
        resultFunSearch = theAllFunctions.filterResultSea(inputSearchingWord, itemsListSea)

        val mContext = binding.root.context
        val bundle = Bundle()
        bundle.putString("inputSearchingWord", inputSearchingWord)
        bundle.putSerializable("resultFunSearch", resultFunSearch as Serializable)
        inputSearchingWordInData(mContext, inputSearchingWord)
        resultSousFragment.arguments = bundle
        navController.navigate(R.id.action_clientHomeFragment_to_resultsearchFragment, bundle)
    }

    override fun onLongClickListener(position: Int) {}

    override fun getOnBackPressedDispatcher(): OnBackPressedDispatcher {
        return requireActivity().onBackPressedDispatcher
    }
}