package com.charmidezassiobo.tcrec.ui.suivietc.tabfragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.db.GetSeaData
import com.charmidezassiobo.tcrec.setup.dataclass.Sea
import com.charmidezassiobo.tcrec.databinding.FragmentTabSeaTrackingBinding
import com.charmidezassiobo.tcrec.setup.functions.AllFunctions
import com.charmidezassiobo.tcrec.setup.Adapter.SEAadapter
import com.charmidezassiobo.tcrec.setup.interfaces.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.ui.suivietc.bottomFragments.AddPlombBottomSheetFragment
import com.charmidezassiobo.tcrec.ui.suivietc.subfragments.SuivietcSousFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class TabSeaTrackingFragment : Fragment(), OnBackPressedDispatcherOwner,
    RecyclerViewClickItemInterface {

    //private var _binding : FragmentTabExportTrackingBinding? = null
    private var _binding : FragmentTabSeaTrackingBinding? = null
    private val binding get() = _binding!!

    private val selectedItems = HashSet<Sea>()
    private val sea = Sea()

    private val cardSelected = R.drawable.carditem_background_selected
    private val cardNotSelected = R.drawable.button_push

    private var isClickLong : Boolean = false
    private var isClick : Boolean = false

    //var getFromDB = GetDataFromDB()
    //var getSeaData = GetSeaData(null, null, sea, null)
    var allFun = AllFunctions()
    //var itemsTc = getFromDB.getSEAdataFromdb()
    lateinit var seaAdapter : SEAadapter
    lateinit var recyclerViewTc : RecyclerView
    val sousfragmentSuivieTc : Fragment = SuivietcSousFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabSeaTrackingBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context

        var searchViewTc = binding.searchViewTc
        recyclerViewTc = binding.recyclerViewSuivieTc
        var chargement = binding.linearLayoutEffectDesChargementSuivieTc
        var refresh = binding.swippRefreshLayoutExport
        var btnTrashTc = binding.btnTrash
        var lnRemoveBtn = binding.lnRemoveBtn
        var btnDeselectedAll = binding.btnBackRemoveSelected

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetwork
        val isConnected = activeNetworkInfo != null

        var sea  = Sea()

        var dataSea = GetSeaData(mContext, this@TabSeaTrackingFragment,  sea,recyclerViewTc)
        var itemsTc = dataSea.getItemList()

        //Reglage RecyclerView
        recyclerViewTc.setHasFixedSize(true)
        dataSea.retrieveSea(chargement)

        //Refreshing page
        refresh.setOnRefreshListener {
            when(true){
                isConnected -> {
                    refresh.isRefreshing = false
                    //getFromDB.inputItemInSeaRecyclerView(mContext, this@TabSeaTrackingFragment, chargement, recyclerViewTc)
                    dataSea.retrieveSea(chargement)
                    allFun.snackBarShowSucces(mContext, root, "Page mis à jour avec succès")
                }
                else -> {
                    refresh.isRefreshing = false
                    allFun.snackBarShowWarning(mContext, root, "Veuillez vous connecter à internet")
                }
            }
/*
            if (isConnected){
                //Connection Internet
                refresh.isRefreshing = false
                getFromDB.inputItemInSeaRecyclerView(mContext, this@TabSeaTrackingFragment, chargement, recyclerViewTc)
                allFun.snackBarShowSucces(mContext, root, "Page mis à jour avec succès")
            } else {
                // Pas de connexion Internet
                refresh.isRefreshing = false
                allFun.snackBarShowWarning(mContext, root, "Veuillez vous connecter à internet")
            }
            */
        }

        //Search in item
        searchViewTc.onFocusChangeListener.apply {
            searchViewTc.setOnQueryTextListener(object  : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(query: String): Boolean {
                    CoroutineScope(Dispatchers.Main).launch{
                        allFun.filterListWithRecyclerSea(mContext, this@TabSeaTrackingFragment, query,itemsTc.await() ,recyclerViewTc)
                    }
                    return false
                }
            })
        }

        //remove item
        btnTrashTc.setOnClickListener {

        }

        btnDeselectedAll.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch{
                for (i in 0 .. itemsTc.await().size){
                    seaAdapter.setItemBackground(i, recyclerViewTc , cardNotSelected )
                }
                isClickLong = false
                isClick = false
                lnRemoveBtn.visibility = View.GONE
                searchViewTc.visibility = View.VISIBLE
            }
        }

        return root
    }

    override fun getOnBackPressedDispatcher(): OnBackPressedDispatcher {
        return requireActivity().onBackPressedDispatcher
    }

    override fun onItemClick(position: Int) {
        var dataSea = GetSeaData(null, this@TabSeaTrackingFragment,  sea,recyclerViewTc)
        var itemsTc = dataSea.getItemList()

        CoroutineScope(Dispatchers.Main).launch{
            var itemsTc = itemsTc.await()
            seaAdapter = SEAadapter(requireContext(), itemsTc, this@TabSeaTrackingFragment )

            when(true){
                itemsTc[position].stepTc == 2 -> {
                    val addPlombBottomSheetFragment = AddPlombBottomSheetFragment()
                    //addPlombBottomSheetFragment.requireParentFragment()
                    addPlombBottomSheetFragment.show(parentFragmentManager, addPlombBottomSheetFragment.tag)
                }
                else -> {
                    when(isClickLong){
                        true -> {
                            itemsTc[position]
                            when(isClick){
                                false -> {
                                    isClick = true
                                    seaAdapter.setItemBackground(position, recyclerViewTc , cardSelected)
                                }
                                else -> {
                                    isClick = false
                                    seaAdapter.setItemBackground(position, recyclerViewTc , cardNotSelected)
                                }
                            }

                        }
                        else -> {
                            val inputTypeTransact = itemsTc[position].typeTransact
                            val inputSousTypeTransact = itemsTc[position].typeSousTransact
                            val inputPositionVoyages = itemsTc[position].stepTc
                            val inputDate = itemsTc[position].dateAjoutSea
                            val inputBooking = itemsTc[position].numBooking
                            val inputCamion = itemsTc[position].numCamion
                            val inputTc = itemsTc[position].numTc1
                            val inputTcSecond = itemsTc[position].numTc2
                            val inputPlomb = itemsTc[position].numPlomb1
                            val inputPlombSecond = itemsTc[position].numPlomb2
                            val inputTelChauffeur = itemsTc[position].numChauffeur
                            val inputStepDate = itemsTc[position].dateHourStep
                            val inputDesc = itemsTc[position].descTc

                            val bundle = Bundle()
                            bundle.putString("inputTypeTransact", inputTypeTransact)
                            bundle.putString("inputSousTypeTransact", inputSousTypeTransact)
                            bundle.putInt("inputPositionVoyages",inputPositionVoyages)
                            bundle.putString("inputDate",inputDate)
                            bundle.putString("inputBooking", inputBooking)
                            bundle.putString("inputCamion", inputCamion)
                            bundle.putString("inputTc", inputTc)
                            bundle.putString("inputTcSecond", inputTcSecond)
                            bundle.putString("inputPlomb", inputPlomb)
                            bundle.putString("inputPlombSecond", inputPlombSecond)
                            bundle.putString("inputTelChauffeur", inputTelChauffeur)
                            bundle.putString("inputDesc",inputDesc)
                            bundle.putSerializable("inputStepDate", inputStepDate as Serializable)

                            sousfragmentSuivieTc.arguments = bundle

                            var navController = findNavController()
                            navController.navigate(R.id.action_navigation_suivietc_to_suivietcSousFragment, bundle)
                            //navController.navigate(R.id.action_tabExportTrackingFragment_to_suivietcSousFragment)
                            Log.d("ItemAppuyer", "$position")
                        }
                    }
                }
            }



        }
    }

    override fun onLongClickListener(position: Int) {
        var dataSea = GetSeaData(null, this@TabSeaTrackingFragment,  sea,recyclerViewTc)
        var itemsTc = dataSea.getItemList()
        CoroutineScope(Dispatchers.Main).launch{
            val itemsTc = itemsTc.await()
            seaAdapter = SEAadapter(requireContext(), itemsTc, this@TabSeaTrackingFragment )
            when(isClickLong){
                false -> {
                    isClickLong = true
                    seaAdapter.setItemBackground(position, recyclerViewTc , cardSelected )
                    binding.lnRemoveBtn.visibility = View.VISIBLE
                    binding.cardViewSearchlistetc.visibility = View.GONE
                    //binding.btnTrash.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        }
    }

    override fun onAddNumPlomb(position: Int) {
        var test = 0
        val addPlombBottomSheetFragment = AddPlombBottomSheetFragment()
        addPlombBottomSheetFragment.show(
            parentFragmentManager, addPlombBottomSheetFragment.tag
        )
        test = test + 1
        Log.d("APPUYERATE", test.toString())
    }

    private fun toggleItemSelection(item: Sea) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }
    }

    private fun deleteSelectedItems(itemList : MutableList<Sea>) {
        // Supprimer les éléments sélectionnés de votre liste de données
        // Par exemple, si votre liste de données est une MutableList<Tc> nommée itemList :
        itemList.removeAll(selectedItems)
        selectedItems.clear()
    }
}