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
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentTabExportTrackingBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.setup.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.ui.suivietc.subfragments.SuivietcSousFragment
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class TabExportTrackingFragment : Fragment(), OnBackPressedDispatcherOwner, RecyclerViewClickItemInterface {

    private var _binding : FragmentTabExportTrackingBinding? = null
    private val binding get() = _binding!!

    var getDataFromDB = GetDataFromDB()
    var allFun = AllFunctions()
    var itemsTc = mutableListOf<Tc>()
    val sousfragmentSuivieTc : Fragment = SuivietcSousFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabExportTrackingBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context

        var searchViewTc = binding.searchViewTc
        var recyclerViewTc = binding.recyclerViewSuivieTc
        var chargement = binding.linearLayoutEffectDesChargementSuivieTc
        var refresh = binding.swippRefreshLayout

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting

        itemsTc = getDataFromDB.inputItemInRecyclerView(this@TabExportTrackingFragment, chargement, recyclerViewTc)

        refresh.setOnRefreshListener {
            if (isConnected){
                //Connection Internet
                refresh.isRefreshing = false
                getDataFromDB.inputItemInRecyclerView(this@TabExportTrackingFragment, chargement, recyclerViewTc)
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

        searchViewTc.onFocusChangeListener.apply {
            searchViewTc.setOnQueryTextListener(object  : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(query: String): Boolean {
                    allFun.filterListWithRecycler(this@TabExportTrackingFragment, query,itemsTc,recyclerViewTc)
                    return false
                }
            })
        }

        return root
    }

    override fun getOnBackPressedDispatcher(): OnBackPressedDispatcher {
        return requireActivity().onBackPressedDispatcher
    }

    override fun onItemClick(position: Int) {

        val inputTypeTransact = itemsTc[position].type_transat
        val inputPositionVoyages = itemsTc[position].step_TC
        val inputDate = itemsTc[position].date_tc
        val inputBooking = itemsTc[position].num_booking
        val inputCamion = itemsTc[position].num_Camion
        val inputTc = itemsTc[position].num_TC
        val inputTcSecond = itemsTc[position].num_TCSecond
        val inputPlomb = itemsTc[position].num_plomb
        val inputPlombSecond = itemsTc[position].num_plomb_second
        val inputTelChauffeur = itemsTc[position].num_tel_chauffeur
        val inputStepDate = itemsTc[position].lesStepDateHour
        val inputDesc = itemsTc[position].desc_TC

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
}