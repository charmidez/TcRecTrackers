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
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.dataclass.Sea
import com.charmidezassiobo.tcrec.databinding.FragmentTabSeaTrackingBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.interfaces.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.setup.Adapter.TCAdapter
import com.charmidezassiobo.tcrec.ui.suivietc.subfragments.SuivietcSousFragment
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class TabSeaTrackingFragment : Fragment(), OnBackPressedDispatcherOwner,
    RecyclerViewClickItemInterface {

    //private var _binding : FragmentTabExportTrackingBinding? = null
    private var _binding : FragmentTabSeaTrackingBinding? = null
    private val binding get() = _binding!!

    private val selectedItems = HashSet<Sea>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<TCAdapter.TCViewHolder>

    var getDataFromDB = GetDataFromDB()
    var allFun = AllFunctions()
    var itemsTc = mutableListOf<Sea>()
    val sousfragmentSuivieTc : Fragment = SuivietcSousFragment()

    lateinit var btnTrashTc : AppCompatImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabSeaTrackingBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context

        var searchViewTc = binding.searchViewTc
        var recyclerViewTc = binding.recyclerViewSuivieTc
        var chargement = binding.linearLayoutEffectDesChargementSuivieTc
        var refresh = binding.swippRefreshLayoutExport

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting

        btnTrashTc = binding.btnTrash

        //Reglage RecyclerView
        recyclerViewTc.setHasFixedSize(true)

        //input in recyclerView and get itemTc
        //itemsTc = getDataFromDB.?inputItemInRecyclerView(this@TabSeaTrackingFragment, chargement, recyclerViewTc)

        //Refreshing page
        refresh.setOnRefreshListener {
            if (isConnected){
                //Connection Internet
                refresh.isRefreshing = false
                getDataFromDB.inputItemInSeaRecyclerView(this@TabSeaTrackingFragment, chargement, recyclerViewTc)
                val snack = Snackbar.make(binding.root,"Page mis à jour avec succès", Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.blue))
                snack.show()
            } else {
                // Pas de connexion Internet
                refresh.isRefreshing = false
                val snack = Snackbar.make(binding.root,"Veuillez vous connecter à internet",Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.gray2))
                snack.show()
            }
        }

        //Search in item
        searchViewTc.onFocusChangeListener.apply {
            searchViewTc.setOnQueryTextListener(object  : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(query: String): Boolean {
                    allFun.filterListWithRecyclerSea(this@TabSeaTrackingFragment, query,itemsTc,recyclerViewTc)
                    return false
                }
            })
        }

        //remove item
        btnTrashTc.setOnClickListener {

        }


        return root
    }

    override fun getOnBackPressedDispatcher(): OnBackPressedDispatcher {
        return requireActivity().onBackPressedDispatcher
    }

    override fun onItemClick(position: Int) {

        val inputTypeTransact = itemsTc[position].typeTransact
        val inputPositionVoyages = itemsTc[position].stepTc
        val inputDate = itemsTc[position].dateAjoutTc
        val inputBooking = itemsTc[position].numBooking
        val inputCamion = itemsTc[position].numCamion
        val inputTc = itemsTc[position].numTc1
        val inputTcSecond = itemsTc[position].numTc2
        val inputPlomb = itemsTc[position].numPlomb1
        val inputPlombSecond = itemsTc[position].numPlomb2
        val inputTelChauffeur = itemsTc[position].numChauffeur
        val inputStepDate = itemsTc[position].dateHourStep
        val inputDesc = itemsTc[position].descTc

        Log.d("inputCamion", inputCamion)

        val bundle = Bundle()
        bundle.putString("inputTypeTransact", inputTypeTransact)
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

        Log.d("ItemAppuyer", "$position")
    }

    override fun onLongClickListener(position: Int) {
        val item = itemsTc[position]
        btnTrashTc.visibility = View.VISIBLE
       //allFun.toggleItemSelection(selectedItems, item)
        toggleItemSelection(item)
        //adapter.notifyDataSetChanged()
        //true
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