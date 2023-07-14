package com.charmidezassiobo.tcrec.ui.ajoutertc

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.dataclass.HeureStep
import com.charmidezassiobo.tcrec.databinding.FragmentAjoutertcBinding
import com.charmidezassiobo.tcrec.dataclass.Sea
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.setup.AllVariables
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AjoutertcFragment : Fragment() {

    val dataBasePath = AllVariables().dbPath

    private var _binding: FragmentAjoutertcBinding? = null
    private val binding get() = _binding!!

    private var db = Firebase.firestore
    private var allFun = AllFunctions()

    var getData = GetDataFromDB()

    var itemsSea : MutableList<Sea>  = getData.getSEAdataFromdb()
    var itemBookingList : ArrayList<String> = allFun.removeRedundance(getData.getSeaBookingWithTitledataFromdb())

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAjoutertcBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mContext = binding.root.context

        //*******All var mecanisme for Export**************//
        val radioGrp: RadioGroup = binding.radioGroupSeaAirRoad

        //AIR Variable
        val radioGrp_Air: RadioGroup = binding.radioGroupAirAjout
        val lnEditTextAirImport = binding.linearLayoutAirImportEditViewGroup
        val lnEditTextSeaImport = binding.linearLayoutSeaImportEditViewGroup

        //Export Variable
        val radioGrp_Sea: RadioGroup = binding.radioGroupSeaAjout
        val lnEditTextAirExport = binding.linearLayoutAirExportEditViewGroup
        val lnEditTextSeaExport = binding.linearLayoutSeaExportEditViewGroup
        val numBookingTc: TextInputEditText = binding.textInputBookingNum
        val numTc1: TextInputEditText = binding.textInputTcNum
        val numTc2: TextInputEditText = binding.textInputTcNum2
        val numCamion: TextInputEditText = binding.textInputCamionNum
        val numChauffeur: TextInputEditText = binding.textInputPhoneChauffeur
        val descTc: TextInputEditText = binding.textInputDesc
        val spinnerBooking = binding.spinnerBookingSeaExport

        //Road Variable
        val lnEditTextRoad = binding.linearLayoutRoadEditViewGroup

        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting

        var typeTransact = ""
        var typeSousTransact = ""

        //Prendre Les données du conteneur
        val butAjouter: Button = binding.ajouteTcButton


        /********************Début de mécanisme des radio button / Radio Button Mecanism Start*******************/
        radioGrp.setOnCheckedChangeListener { group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when (selectOption) {
                //When i choose AIR
                R.id.radioButton_air_ajout -> {
                    typeTransact = "AIR"
                    allFun.clearRadioBtnInAjoutTC(radioGrp_Sea)
                    allFun.resetFragmentInAjoutTC(
                        radioGrp,
                        radioGrp_Sea,
                        radioGrp_Air,
                        lnEditTextAirImport,
                        lnEditTextAirExport,
                        lnEditTextSeaImport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    radioGrp_Air.visibility = View.VISIBLE
                }
                //When i choose SEA
                R.id.radioButton_sea_ajout -> {
                    typeTransact = "SEA"
                    allFun.clearRadioBtnInAjoutTC(radioGrp_Air)
                    allFun.resetFragmentInAjoutTC(
                        radioGrp,
                        radioGrp_Sea,
                        radioGrp_Air,
                        lnEditTextAirImport,
                        lnEditTextAirExport,
                        lnEditTextSeaImport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    radioGrp_Sea.visibility = View.VISIBLE
                }
                //When i choose ROAD
                R.id.radioButton_road_ajout -> {
                    typeTransact = "ROAD"
                    allFun.clearRadioBtnInAjoutTC(radioGrp_Air)
                    allFun.clearRadioBtnInAjoutTC(radioGrp_Sea)
                    allFun.resetFragmentInAjoutTC(
                        radioGrp,
                        radioGrp_Sea,
                        radioGrp_Air,
                        lnEditTextAirImport,
                        lnEditTextAirExport,
                        lnEditTextSeaImport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    lnEditTextRoad.visibility = View.VISIBLE
                }
            }
        }


        /*****Export Radio Button Mecanism *******/
        radioGrp_Air.setOnCheckedChangeListener { group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when (selectOption) {
                //Je choisie SEA Import
                R.id.radioButton_sea_imp_ajout -> {
                    typeSousTransact = "IMPORT (SEA)"
                    allFun.resetAllLnInAjoutTC(
                        lnEditTextAirImport,
                        lnEditTextSeaImport,
                        lnEditTextAirExport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    lnEditTextSeaImport.visibility = View.VISIBLE
                }
                //Je choisie SEA Export
                R.id.radioButton_sea_exp_ajout -> {
                    typeSousTransact = "EXPORT (SEA)"
                    allFun.resetAllLnInAjoutTC(
                        lnEditTextAirImport,
                        lnEditTextSeaImport,
                        lnEditTextAirExport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    lnEditTextSeaExport.visibility = View.VISIBLE
                }
            }
        }

        /**** Import Radio Butto Mecanism *****/
        radioGrp_Sea.setOnCheckedChangeListener { group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when (selectOption) {
                //Je choisie AIR Import
                R.id.radioButton_air_import_ajout -> {
                    typeSousTransact = "IMPORT (AIR)"
                    allFun.resetAllLnInAjoutTC(
                        lnEditTextAirImport,
                        lnEditTextSeaImport,
                        lnEditTextAirExport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    lnEditTextSeaExport.visibility = View.VISIBLE
                    spinnerBookingList(mContext, spinnerBooking, numBookingTc)
                }
                //Je choisie AIR Export
                R.id.radioButton_air_exp_ajout -> {
                    typeSousTransact = "EXPORT (AIR)"
                    allFun.resetAllLnInAjoutTC(
                        lnEditTextAirImport,
                        lnEditTextSeaImport,
                        lnEditTextAirExport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    lnEditTextAirExport.visibility = View.VISIBLE
                }
            }
        }

        // Enréistrement dans la base de donnée
        butAjouter.setOnClickListener {
            if (isConnected) {
                when(typeTransact){
                    "Export" -> {
                        when(typeSousTransact){
                            "SEA Export" -> addSeaExport(mContext, root, typeTransact, typeSousTransact, numBookingTc, numCamion, numTc1, numTc2, numChauffeur, descTc,butAjouter)
                            "AIR Export" -> addAirExport()
                        }
                    }
                    "Import" -> {
                        when(typeSousTransact){
                            "SEA Import" -> addSeaImport()
                            "AIR Import" -> addAirImport()
                        }
                    }
                    "Road" -> {
                        typeTransact="Road Tracking  Only"
                        addRoadTracking()
                    }
                }

            } else {
                // Pas de connexion Internet
                val snack = Snackbar.make(binding.frameLayoutAjoutTc, "Veuillez vous connecter à internet", Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(ContextCompat.getColor(root.context, R.color.gray2))
                snack.show()
            }
        }
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addSeaExport(
        mContext: Context,
        rootView : View,
        typeTransact : String,
        typeSousTransact : String,
        numBooking : TextInputEditText,
        numCamion : TextInputEditText,
        numTc1 : TextInputEditText,
        numTc2 : TextInputEditText,
        numChauffeur : TextInputEditText,
        descTc : TextInputEditText,
        butAdd : Button){

        var step = 0
        var getDate = allFun.miseEnPlaceDate(true)
        var numPlomb1 = ""
        var numPlomb2 = ""
        var heureRealStartTc = AllFunctions().miseEnPlaceHeure()
        val dateRealChiffre = AllFunctions().miseEnPlaceDate(true)
        val dateRealDate = AllFunctions().miseEnPlaceDate(false)
        val lesStepHour = listOf(
            HeureStep(dateRealChiffre, dateRealDate, heureRealStartTc)
        )

        //removing all space
        val numBookingRmvSpx = AllFunctions().removeSpaces(numBooking.text.toString())
        val numTc1RmvSpx = AllFunctions().removeSpaces(numTc1.text.toString())
        val numTc2RmvSpx = AllFunctions().removeSpaces(numTc2.text.toString())
        val numCamionRmvSpx = AllFunctions().removeSpaces(numCamion.text.toString())
        val numPhoneChauffeurRmvSpx = AllFunctions().removeSpaces(numChauffeur.text.toString())
        val descContenuRmvSpx = AllFunctions().removeSpaces(descTc.text.toString())

        //Récupération des données saisies
        if (typeTransact.isNotBlank()){
            if (numBookingRmvSpx.isNotBlank()){
                if (numTc1RmvSpx.isNotBlank() && numCamionRmvSpx.isNotBlank()){
                    val registerSeaExport = hashMapOf(
                        "date_ajout_tc" to getDate,
                        "type_transact" to typeTransact,
                        "type_sous_transact" to typeSousTransact,
                        "num_booking" to numBookingRmvSpx,
                        "num_camion" to numCamionRmvSpx,
                        "num_tc_1" to numTc1RmvSpx,
                        "num_tc_2" to numTc2RmvSpx,
                        "num_plomb_tc_1" to numPlomb1,
                        "num_plomb_tc_2" to numPlomb2,
                        "step_tc" to step,
                        "desc_tc" to descContenuRmvSpx,
                        "num_phone_chauffeur" to numPhoneChauffeurRmvSpx,
                        "date_hour_step" to lesStepHour
                    )

                    //Enregistrement de données dans la base  de données
                    db.collection(dataBasePath).document().set(registerSeaExport)
                        .addOnSuccessListener {
                            val snack = Snackbar.make(rootView, "Le conteneur ${numTc1RmvSpx} a été bien enrégistré ce $getDate", Snackbar.LENGTH_LONG)
                            snack.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                            snack.setBackgroundTint(ContextCompat.getColor(mContext, R.color.blue))
                            snack.show()
                            numBooking.text?.clear()
                            numTc1.text?.clear()
                            numTc2.text?.clear()
                            numCamion.text?.clear()
                            descTc.text?.clear()
                            numChauffeur.text?.clear()
                            butAdd.text = getText(R.string.but_addtc)
                            butAdd.isEnabled = true
                            butAdd.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                        }
                        .addOnFailureListener {
                            val snack = Snackbar.make(rootView, "Le conteneur ${numTc1.text.toString()} na pas pu être enrégistré", Snackbar.LENGTH_LONG)
                            snack.show()
                            butAdd.text = getText(R.string.but_addtc)
                            butAdd.isEnabled = true
                            butAdd.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                        }
                } else if (numCamionRmvSpx.isBlank() || numTc1RmvSpx.isBlank()){
                    val snack = Snackbar.make(rootView, "Veuillez renseigner un Tc et un numéro camion", Snackbar.LENGTH_LONG)
                    snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    snack.setBackgroundTint(ContextCompat.getColor(mContext, R.color.gray2))
                    snack.show()
                    butAdd.text = getText(R.string.but_addtc)
                    butAdd.isEnabled = true
                    butAdd.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                }
            }
        }


    }

    private fun addSeaImport(){}

    private fun addAirImport(){}

    private fun addAirExport(){}

    private fun addRoadTracking(){}

    private fun spinnerBookingList(mContext: Context, spinnerView : Spinner, numBookingTc : TextInputEditText){
        //Spinner booking
        getData.seaCallBack {
            itemBookingList = allFun.removeRedundance(itemBookingList)
            var arrayAdapter = ArrayAdapter(mContext, android.R.layout.simple_list_item_1,  itemBookingList)
            spinnerView.adapter = arrayAdapter

            spinnerView.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
                var selectedItem : String? = null
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedItem = itemBookingList[position]
                    numBookingTc.setText(selectedItem)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}