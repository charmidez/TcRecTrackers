package com.charmidezassiobo.tcrec.ui.suivietc

import android.content.ClipData.Item
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.data.HeureStep
import com.charmidezassiobo.tcrec.setup.TCAdapter
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBinding
import com.charmidezassiobo.tcrec.setup.RecyclerViewClickItemInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.util.Locale

class SuivietcFragment : Fragment(), RecyclerViewClickItemInterface{

    private var _binding: FragmentSuivietcBinding? = null
    private val binding get() = _binding!!

    // Variable des View
    lateinit var recyclerView_TC : RecyclerView
    lateinit var refresh : SwipeRefreshLayout
    lateinit var txtView_charging : TextView
    lateinit var progressBar_view : ProgressBar
    lateinit var searchView_tc : androidx.appcompat.widget.SearchView
    lateinit var convertPdfBtn : FloatingActionButton
    lateinit var imgView_list_tc_bk : ImageView

    val db = Firebase.firestore
    //val voyRef = db.collection("Voyage")
    val voyRef = db.collection("Voyagetest")

    val sousfragment : Fragment = SuivietcSousFragment()
    val sousfragmentbooking : Fragment = SuivietcBookingSousFragment()

    var items_tc : MutableList<Tc> = ArrayList()
    var tempArrayList : MutableList<Tc> = ArrayList()

    //var navController : NavController =  findNavController()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentSuivietcBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting

        var navController = findNavController()

        recyclerView_TC = binding.recyclerViewSuivieTc
        refresh = binding.swippRefreshLayout
        txtView_charging  = binding.textViewCharging
        progressBar_view  = binding.progressBarId
        searchView_tc  = binding.searchViewTc
        convertPdfBtn = binding.fab
        imgView_list_tc_bk = binding.imageViewBkTc
        items_tc = mutableListOf()

        progressBar_view.setVisibility(View.VISIBLE)

        
        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.END  or ItemTouchHelper.START)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.END -> {
                        removedItem(viewHolder, recyclerView_TC)
                    }
                    ItemTouchHelper.START -> {
                        removedItem(viewHolder, recyclerView_TC)
                    }
                }
            }
        })

        inputItemInRecyclerView(txtView_charging,progressBar_view,recyclerView_TC)

        tempArrayList = arrayListOf<Tc>()

        //Bien réorganiser le Pdf
        createPdf("En cours de création","Tc.pdf", convertPdfBtn, recyclerView_TC)
        touchHelper.attachToRecyclerView(recyclerView_TC)

        searchView_tc.onFocusChangeListener.apply {
            searchView_tc.setOnQueryTextListener(object  : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(query: String): Boolean {
                    filterList(query)
                    return false
                }
            })
        }

        //voirListDesTc(imgView_list_tc_bk)
        voirListDesTc(imgView_list_tc_bk,navController)


        refresh.setOnRefreshListener{
            items_tc.clear()
            if (isConnected) {
                // Connexion Internet disponible
                inputItemInRecyclerView(txtView_charging,progressBar_view,recyclerView_TC)
                refresh.isRefreshing = false
                val snack = Snackbar.make(binding.root,"Page mis à jour avec succès",Snackbar.LENGTH_LONG)
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

        return root
    }

    private fun filterList(query : String){

        if (query  != null ){
            val filteredList = ArrayList<Tc>()
            for (i in items_tc){

                if(i.num_TC.lowercase(Locale.ROOT).contains(query) || i.num_TC.uppercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
                if( i.num_TCSecond.lowercase(Locale.ROOT).contains(query) || i.num_TCSecond.uppercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
                if(i.num_Camion.lowercase(Locale.ROOT).contains(query) || i.num_Camion.uppercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
                if(i.num_booking.lowercase(Locale.ROOT).contains(query) || i.num_booking.uppercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                recyclerView_TC.adapter = TCAdapter(filteredList, this@SuivietcFragment)

            } else {
                recyclerView_TC.adapter = TCAdapter(filteredList, this@SuivietcFragment)
            }
        }

    }

    fun createPdf(contenuDuPdf : String, nomDuPdf : String, convertPdfBtn : FloatingActionButton, recyclerView : RecyclerView){
        //Bien réorganiser le Pdf de sorte qu'on puisse obtenir un tableau complet dans lequel se trouve la liste des Tc
        convertPdfBtn.setOnClickListener {
            // Créez un document PDF
            val document = Document()
            val filePath = "/storage/emulated/0/Documents/$nomDuPdf"

            // Initialisez l'objet FileOutputStream pour écrire dans le fichier PDF
            val fileOutputStream = FileOutputStream(filePath)
            PdfWriter.getInstance(document, fileOutputStream)

            // Ouvrez le document PDF et ajoutez du texte dans le document PDF
            document.open()
            val paragraph = Paragraph(contenuDuPdf)
            document.add(paragraph)

            // Fermez le document PDF et le fichier PDF
            document.close()
            fileOutputStream.close()
            //Log.d("item_tc",contenuDuPdf)

            val snack = Snackbar.make(recyclerView,"PDF Créé avec Succès", Snackbar.LENGTH_LONG)
            snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.blue))
            snack.show()

        }
    }

    fun removedItem(v: RecyclerView.ViewHolder, recyclerView_TC : RecyclerView ) {
        // Suppression de l'élément de la RecyclerView
        val position = v.adapterPosition
        val tc = items_tc[position]
        items_tc.removeAt(position)
        recyclerView_TC.adapter!!.notifyItemRemoved(position)

        // Suppression de l'élément de Firebase Firestore
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Voyage")
            .whereEqualTo("num_TC", tc.num_TC)
            .whereEqualTo("num_Camion", tc.num_Camion)
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val docRef = db.collection("Voyage").document(document.id)
                docRef.delete().addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully deleted!")
                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error deleting document", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error getting documents", e)
        }

        // Affichage du message de confirmation
        val snack = Snackbar.make(recyclerView_TC, "TC Supprimé", Snackbar.LENGTH_LONG)
        snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.gray2))
        snack.show()
    }

    fun inputItemInRecyclerView(txtView_charging : TextView, progressBar_view : ProgressBar, recyclerView_TC: RecyclerView){
        items_tc.clear()
        voyRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (documents != null){
                        val idtc_ok = document.getLong("step_TC")?.toInt()
                        val numtc_ok = document.data.get("num_TC").toString()
                        val num_booking_tc = document.data.get("num_Booking").toString()
                        val num_cam_ok = document.data.get("num_Camion").toString()
                        val step_tc_ok = document.getLong("step_TC")?.toInt()
                        val date_ok = document.data.get("Date").toString()
                        val plomb_ok = document.data.get("num_plomb_TC").toString()
                        val num_phone_chauffeur_ok = document.data.get("phone_chauffeur_TC").toString()
                        val numtcsecond_ok = document.data.get("num_TC_Second").toString()
                        val numplombsecond_ok = document.data.get("num_plomb_TC_2").toString()
                        val type_transact = document.data.get("import_export")
                        val heureDeChaqueStep  = document.data.get("lesStepDateHour") as List<HeureStep>

                        if ( idtc_ok != null){
                            if (step_tc_ok != null ){
                                items_tc.add(Tc( "$numtc_ok",
                                    "$numtcsecond_ok",
                                    "$num_cam_ok",
                                    "$num_phone_chauffeur_ok",
                                    " $num_booking_tc",
                                    "$plomb_ok",
                                    "$date_ok",
                                    step_tc_ok,
                                    "$numplombsecond_ok",
                                    "$type_transact",
                                    heureDeChaqueStep
                                    )
                                )
                                recyclerView_TC.apply {
                                    recyclerView_TC.adapter = TCAdapter(items_tc, this@SuivietcFragment)
                                }
                                txtView_charging.isVisible  = false
                                progressBar_view.setVisibility(View.GONE)
                                items_tc.sortWith(compareBy({it.step_TC}))
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    override fun onItemClick(position: Int) {

        val inputTypeTransact = items_tc[position].type_transat
        val inputPositionVoyages = items_tc[position].step_TC
        val inputDate = items_tc[position].date_tc
        val inputBooking = items_tc[position].num_booking
        val inputCamion = items_tc[position].num_Camion
        val inputTc = items_tc[position].num_TC
        val inputTcSecond = items_tc[position].num_TCSecond
        val inputPlomb = items_tc[position].num_plomb
        val inputPlombSecond = items_tc[position].num_plomb_second
        val inputTelChauffeur = items_tc[position].num_tel_chauffeur
        val inputStepDate = items_tc[position].lesStepDateHour

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

        bundle.putSerializable(" inputStepDate", ArrayList(inputStepDate))


        sousfragment.arguments = bundle

        /*parentFragmentManager
            .beginTransaction().apply {
                replace(R.id.nav_host_fragment_activity_main,sousfragment)
                .addToBackStack(null)
                .commit()
            }*/

        val navController = findNavController()
        //val tc = Tc(inputTc,inputTcSecond,inputCamion,inputTelChauffeur,inputBooking,inputPlomb,inputDate,inputPositionVoyages,inputPlombSecond,inputTypeTransact)
        //val action = SuivietcFragmentDirections.actionNavigationSuivietcToSuivietcSousFragment(tc)
        navController.navigate(R.id.action_navigation_suivietc_to_suivietcSousFragment, bundle)
        //com.charmidezassiobo.tcrec.data.Tc


    }

    fun voirListDesTc(img :  ImageView, navController : NavController){

        img.setOnClickListener {
            navController.navigate(R.id.action_navigation_suivietc_to_suivietcBookingSousFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}