package com.charmidezassiobo.tcrec.ui.suivietc.tabfragment

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.db.GetSeaData
import com.charmidezassiobo.tcrec.setup.data.Sea
import com.charmidezassiobo.tcrec.databinding.FragmentTabSeaTrackingBinding
import com.charmidezassiobo.tcrec.setup.functions.AllFunctions
import com.charmidezassiobo.tcrec.setup.Adapter.SEAadapter
import com.charmidezassiobo.tcrec.setup.interfaces.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.ui.suivietc.bottomfragments.AddPlombBottomSheetFragment
import com.charmidezassiobo.tcrec.ui.suivietc.subfragments.SuivietcSousFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class TabSeaTrackingFragment : Fragment(), OnBackPressedDispatcherOwner,
    RecyclerViewClickItemInterface {

    private var _binding : FragmentTabSeaTrackingBinding? = null
    private val binding get() = _binding!!

    private val selectedItems = HashSet<Sea>()
    private val sea = Sea()

    private val cardSelected = R.drawable.carditem_background_selected
    private val cardNotSelected = R.drawable.button_push

    private var isClickLong : Boolean = false
    private var isClick : Boolean = false

    var allFun = AllFunctions()

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
        var lnRemoveBtn = binding.lnRemoveBtn

        //Remove Transaction Lineair Layout
        var btnDeselectedAll = binding.btnBackSelectedTransact
        var btnTrashTc = binding.btnTrashRmvTransact

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetwork
        val isConnected = activeNetworkInfo != null

        var sea  = Sea()
        //RecyclerView.ViewHolder

        var dataSea = GetSeaData(mContext, this@TabSeaTrackingFragment,  sea, recyclerViewTc)
        var itemsTc = dataSea.getItemList()

        refresh.isRefreshing = false

        //Reglage RecyclerView
        recyclerViewTc.setHasFixedSize(true)
        dataSea.retrieveSea(chargement)


        //Refreshing page
        refresh.setOnRefreshListener {
            when(true){
                isConnected -> {
                    refresh.isRefreshing = false
                    dataSea.retrieveSea(chargement)
                    allFun.snackBarShowSucces(mContext, root, "Page mis à jour avec succès")
                }
                else -> {
                    refresh.isRefreshing = false
                    allFun.snackBarShowWarning(mContext, root, "Veuillez vous connecter à internet")
                }
            }
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
            //showRemoveConfirmation(itemsTc,root,recyclerViewTc, seaViewHolder)

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

                    val inputBooking = itemsTc[position].numBooking
                    val inputTc = itemsTc[position].numTc1
                    val inputTcSecond = itemsTc[position].numTc2

                    val bundle = Bundle()
                    bundle.putString("inputBooking", inputBooking)
                    bundle.putString("inputTc", inputTc)
                    bundle.putString("inputTcSecond", inputTcSecond)

                    val addPlombBottomSheetFragment = AddPlombBottomSheetFragment()
                    addPlombBottomSheetFragment.arguments = bundle
                    addPlombBottomSheetFragment.show(childFragmentManager, addPlombBottomSheetFragment.tag)
                }
                else -> {
                    when(isClickLong){
                        true -> {
                            itemsTc[position]
                            when(isClick){
                                false -> {
                                    isClick = true
                                    seaAdapter.setItemBackground(position, recyclerViewTc , cardSelected)
                                    toggleItemSelection( sea)
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
                }
                else -> {

                }
            }
        }
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


    fun showRemoveConfirmation(itemsTc : Deferred<MutableList<Sea>>, root : View, recyclerView: RecyclerView, v: RecyclerView.ViewHolder){

        val mContext = binding.root.context
        val builder = AlertDialog.Builder(requireContext())
        val getSeaData = GetSeaData(mContext, this@TabSeaTrackingFragment, null, recyclerView)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)
        builder.setView(dialogView)
        val titleDialog = dialogView.findViewById<TextView>(R.id.txtView_titleAlertDialog)
        val messageDialog = dialogView.findViewById<TextView>(R.id.txtView_messageAlertDialog)
        val btnOui = dialogView.findViewById<Button>(R.id.buttonOui)
        val btnNon = dialogView.findViewById<Button>(R.id.buttonNon)
        titleDialog.text = "Confirmation"
        messageDialog.text = "Voulez-vous supprimer ces élements ?"
        val alertDialog  = builder.create()
        btnOui.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                deleteSelectedItems(itemsTc.await())
                getSeaData.removeSea(mContext, root, recyclerView, v)
            }
        }
        btnNon.setOnClickListener {
            dialogView.visibility = View.GONE
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


}