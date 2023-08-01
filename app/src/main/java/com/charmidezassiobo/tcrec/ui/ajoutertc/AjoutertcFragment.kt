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
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.dataclass.HeureStep
import com.charmidezassiobo.tcrec.databinding.FragmentAjoutertcBinding
import com.charmidezassiobo.tcrec.setup.db.GetSeaData
import com.charmidezassiobo.tcrec.setup.functions.AllFunctions
import com.charmidezassiobo.tcrec.setup.functions.AllVariables
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class AjoutertcFragment : Fragment() {

    val dataBasePath = AllVariables().DBPATH
    val SEAPATH = AllVariables().SEA_COLLECTION
    val AIRPATH = AllVariables().AIR_COLLECTION
    val ROADPATH = AllVariables().ROAD_COLLECTION

    private var _binding: FragmentAjoutertcBinding? = null
    private val binding get() = _binding!!

    private var allFun = AllFunctions()
    //private var getData = GetDataFromDB()

    //var itemsSea : MutableList<Sea>  = getData.getSEAdataFromdb()
    //var itemBookingList : ArrayList<String> = allFun.removeRedundance(getData.getSeaBookingWithTitledataFromdb())

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAjoutertcBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mContext = binding.root.context
        val navController = findNavController()

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
        //val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val activeNetworkInfo = connectivityManager.activeNetwork
        //val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        val isConnected = activeNetworkInfo != null /*&& activeNetworkInfo.isConnectedOrConnecting*/

        var typeTransact = ""
        var typeSousTransact = ""

        val butUpdateStep : ImageButton = binding.buttonUpdateStep
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
        radioGrp_Sea.setOnCheckedChangeListener { group, i ->
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
        radioGrp_Air.setOnCheckedChangeListener { group, i ->
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
                    lnEditTextAirImport.visibility = View.VISIBLE
                    //spinnerBookingList(mContext, spinnerBooking, numBookingTc)
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
            buttonModifier(butAjouter, false)
            if (isConnected) {
                when(typeTransact){
                    "SEA" -> {
                        when(typeSousTransact){
                            "EXPORT (SEA)" -> {
                                addSeaExport(mContext, root, typeTransact, typeSousTransact, numBookingTc, numCamion, numTc1, numTc2, numChauffeur, descTc)
                                buttonModifier(butAjouter, true)
                            }

                            "IMPORT (SEA)" -> {
                                addSeaImport()
                                buttonModifier(butAjouter, true)
                            }
                        }
                    }
                    "AIR" -> {
                        when(typeSousTransact){
                            "EXPORT (AIR)" -> {
                                addAirExport()
                                buttonModifier(butAjouter, true)
                            }
                            "IMPORT (AIR)" -> {
                                addAirImport()
                                buttonModifier(butAjouter, true)
                            }
                        }
                    }
                    "ROAD" -> {
                        //typeTransact="Road Tracking  Only"
                        typeSousTransact = "Road Tracking"
                        addRoadTracking()
                        buttonModifier(butAjouter, true)
                    }
                }

            } else {
                // Pas de connexion Internet
                val snack = Snackbar.make(binding.frameLayoutAjoutTc, "Veuillez vous connecter à internet", Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(ContextCompat.getColor(root.context, R.color.gray2))
                snack.show()
                buttonModifier(butAjouter, true)
            }
        }

        butUpdateStep.setOnClickListener {
            navController.navigate(R.id.action_navigation_ajoutertc_to_updateStepSubFragment)
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
        descTc : TextInputEditText){

        var dbSea = GetSeaData(mContext, null, null, null).dbSea
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

        when(true){
            numBookingRmvSpx.isNullOrBlank() -> {
                allFun.snackBarShowWarning(mContext, rootView, "Veilez saisir le numéro booking")
            }
            numCamionRmvSpx.isNullOrBlank() -> {
                allFun.snackBarShowWarning(mContext, rootView, "Veilez saisir la plaque d'immatriculation du camion")
            }
            numTc1RmvSpx.isNullOrBlank() && numTc2RmvSpx.isNullOrBlank()  -> {
                allFun.snackBarShowWarning(mContext, rootView, "Veilez saisir au moins un numéro conteneur")
            }
            else -> {

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
                dbSea.document().set(registerSeaExport)
                    .addOnSuccessListener {
                        numBooking.text?.clear()
                        numTc1.text?.clear()
                        numTc2.text?.clear()
                        numCamion.text?.clear()
                        descTc.text?.clear()
                        numChauffeur.text?.clear()
                        allFun.snackBarShowSucces(mContext, rootView, "La transaction a bien été ajouté ce $getDate")
                    }
                    .addOnFailureListener {
                        allFun.snackBarShowSucces(mContext, rootView, "La transaction n'a pu être enrégistrée")
                    }
            }
        }


    }

    private fun addSeaImport(){}

    private fun addAirImport(){}

    private fun addAirExport(){}

    private fun addRoadTracking(){}

    private fun buttonModifier(but : Button, boolean: Boolean ){

        but.isEnabled = boolean
        when(boolean){
            false -> {
                but.setBackgroundResource(R.drawable.btn_drawable_not_selected)
                but.text = getText(R.string.but_charging).toString()
            }
            true -> {
                but.setBackgroundResource(R.drawable.btn_drawable_red)
                but.text = getText(R.string.but_addtc).toString()
            }
        }
        but.text = "AJOUTER TRANSACTION"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}