package com.charmidezassiobo.tcrec.ui.ajoutertc

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.data.HeureStep
import com.charmidezassiobo.tcrec.databinding.FragmentAjoutertcBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.setup.AllVariables
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*

class AjoutertcFragment : Fragment() {

    val dataBasePath = AllVariables().dbPath

    private var _binding: FragmentAjoutertcBinding? = null
    private val binding get() = _binding!!

    private var db = Firebase.firestore
    private var allFun = AllFunctions()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAjoutertcBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mContext = binding.root.context

        val allFun = AllFunctions()

        //*******All var mecanisme for Export**************//
        val radioGrp: RadioGroup = binding.radioGroupOk

        //Import Variable
        val radioGrp_import: RadioGroup = binding.radioGroupImportAirSeaTrackingAjout
        val lnEditTextAirImport = binding.linearLayoutAirImportEditViewGroup
        val lnEditTextSeaImport = binding.linearLayoutSeaImportEditViewGroup

        //Export Variable
        val radioGrp_export: RadioGroup = binding.radioGroupExportAirSeaTrackingAjout
        val lnEditTextAirExport = binding.linearLayoutAirExportEditViewGroup
        val lnEditTextSeaExport = binding.linearLayoutSeaExportEditViewGroup

        //Road Varaible
        val lnEditTextRoad = binding.linearLayoutRoadEditViewGroup
        val numBookingTc: TextInputEditText = binding.textInputBookingNum
        val numTc1: TextInputEditText = binding.textInputTcNum
        val numTc2: TextInputEditText = binding.textInputTcNum2
        val numCamion: TextInputEditText = binding.textInputCamionNum
        val numChauffeur: TextInputEditText = binding.textInputPhoneChauffeur
        val descTc: TextInputEditText = binding.textInputDesc

        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting

        var ajouterdate: String
        var step_tc: Int
        var num_plomb: String
        var num_plomb_2: String

        var typeTransact = ""
        var typeSousTransact = ""

        var bookingList = mutableListOf<String>()
        var spinner_ajout_tc = binding.spinnerAjoutTc
        var getData = GetDataFromDB()

        //Prendre Les données du conteneur
        val butAjouter: Button = binding.ajouteTcButton


        /********************Début de mécanisme des radio button / Radio Button Mecanism Start*******************/
        radioGrp.setOnCheckedChangeListener { group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when (selectOption) {

                //When i choose Import
                R.id.radioButton_Import_ajout -> {
                    typeTransact = "Import"
                    allFun.clearRadioBtnInAjoutTC(radioGrp_export)
                    allFun.resetFragmentInAjoutTC(
                        radioGrp,
                        radioGrp_export,
                        radioGrp_import,
                        lnEditTextAirImport,
                        lnEditTextAirExport,
                        lnEditTextSeaImport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    radioGrp_import.visibility = View.VISIBLE
                }

                //When i choose Export
                R.id.radioButton_export_ajout -> {
                    typeTransact = "Export"
                    allFun.clearRadioBtnInAjoutTC(radioGrp_import)
                    allFun.resetFragmentInAjoutTC(
                        radioGrp,
                        radioGrp_export,
                        radioGrp_import,
                        lnEditTextAirImport,
                        lnEditTextAirExport,
                        lnEditTextSeaImport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    radioGrp_export.visibility = View.VISIBLE
                }

                //When i choose Road
                R.id.radioButton_road_ajout -> {
                    typeTransact = "Road"
                    allFun.clearRadioBtnInAjoutTC(radioGrp_import)
                    allFun.clearRadioBtnInAjoutTC(radioGrp_export)
                    allFun.resetFragmentInAjoutTC(
                        radioGrp,
                        radioGrp_export,
                        radioGrp_import,
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
        radioGrp_import.setOnCheckedChangeListener { group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when (selectOption) {
                //Je choisie SEA Import
                R.id.radioButton_sea_imp_ajout -> {
                    typeSousTransact = "SEA Import"
                    allFun.resetAllLnInAjoutTC(
                        lnEditTextAirImport,
                        lnEditTextSeaImport,
                        lnEditTextAirExport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    lnEditTextSeaImport.visibility = View.VISIBLE
                }

                //Je choisie AIR Import
                R.id.radioButton_air_imp_ajout -> {
                    typeSousTransact = "AIR Import"
                    allFun.resetAllLnInAjoutTC(
                        lnEditTextAirImport,
                        lnEditTextSeaImport,
                        lnEditTextAirExport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    lnEditTextAirImport.visibility = View.VISIBLE
                }
            }
        }

        /**** Import Radio Butto Mecanism *****/
        radioGrp_export.setOnCheckedChangeListener { group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when (selectOption) {
                //Je choisie SEA Export
                R.id.radioButton_sea_exp_ajout -> {
                    typeSousTransact = "SEA Export"
                    allFun.resetAllLnInAjoutTC(
                        lnEditTextAirImport,
                        lnEditTextSeaImport,
                        lnEditTextAirExport,
                        lnEditTextSeaExport,
                        lnEditTextRoad
                    )
                    lnEditTextSeaExport.visibility = View.VISIBLE
                }
                //Je choisie AIR Export
                R.id.radioButton_air_exp_ajout -> {
                    typeSousTransact = "AIR Export"
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


/*        //Spinner booking
        getData.updateTc {
            bookingList = getData.getListBooking()
            val set: Set<String> = bookingList.toHashSet()
            bookingList.clear()
            bookingList.addAll(set)
            val adapter = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, bookingList)
            spinner_ajout_tc.adapter = adapter
        }
        bookingList.add("")
        spinner_ajout_tc.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = bookingList[position]
                numBookingTc.setText(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        var heureRealStartTc = AllFunctions().miseEnPlaceHeure()
        val dateRealChiffre = AllFunctions().miseEnPlaceDate(true)
        val dateRealDate = AllFunctions().miseEnPlaceDate(false)

        val lesStepHour = listOf(
            HeureStep(dateRealChiffre, dateRealDate, heureRealStartTc)
        )*/


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

/*                butAjouter.text = "Chargement..."
                butAjouter.isEnabled = false
                butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_not_selected))

                //removing all space
                val numBookingRmvSpx = AllFunctions().removeSpaces(numBookingTc.text.toString())
                val numTc1RmvSpx = AllFunctions().removeSpaces(numTCOff.text.toString())
                val numTc2RmvSpx = AllFunctions().removeSpaces(numTCSecondOff.text.toString())
                val numCamionRmvSpx = AllFunctions().removeSpaces(numCamion.text.toString())
                val numPhoneChauffeurRmvSpx = AllFunctions().removeSpaces(phone.text.toString())
                val descContenuRmvSpx = AllFunctions().removeSpaces(descTC.text.toString())

                ajouterdate =
                    "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"
                step_tc = 0
                num_plomb = ""
                num_plomb_2 = ""

                val recup_numCamion = numCamion.text.toString()
                val recup_numTc = numTCOff.text.toString()

                if (typeTransat != "") {
                    if (recup_numCamion != "" || recup_numTc != "") {
                        val registerTc = hashMapOf(
                            "Date" to ajouterdate,
                            "num_Booking" to numBookingRmvSpx,
                            "num_TC" to numTc1RmvSpx,
                            "num_TC_Second" to numTc2RmvSpx,
                            "num_Camion" to numCamionRmvSpx,
                            "step_TC" to step_tc,
                            "desc_TC" to descContenuRmvSpx,
                            "num_plomb_TC" to num_plomb,
                            "num_plomb_TC_2" to num_plomb_2,
                            "phone_chauffeur_TC" to numPhoneChauffeurRmvSpx,
                            "import_export" to typeTransat,
                            "lesStepDateHour" to lesStepHour
                        )
                        db.collection(dataBasePath).document().set(registerTc)
                            .addOnSuccessListener {
                                val snack = Snackbar.make(
                                    binding.root,
                                    "Le conteneur ${numTCOff.text.toString()} a été bien enrégistré ce $ajouterdate",
                                    Snackbar.LENGTH_LONG
                                )
                                snack.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                snack.setBackgroundTint(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.blue
                                    )
                                )
                                snack.show()
                                numBookingTc.text?.clear()
                                numTCOff.text?.clear()
                                numCamion.text?.clear()
                                descTC.text?.clear()
                                phone.text?.clear()
                                numTCSecondOff.text?.clear()
                                butAjouter.text = getText(R.string.but_addtc)
                                butAjouter.isEnabled = true
                                butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                            }
                            .addOnFailureListener {
                                val snack = Snackbar.make(
                                    binding.root,
                                    "Le conteneur ${numTCOff.text.toString()} na pas pu être enrégistré",
                                    Snackbar.LENGTH_LONG
                                )
                                snack.show()
                                butAjouter.text = getText(R.string.but_addtc)
                                butAjouter.isEnabled = true
                                butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                            }

                    } else if (recup_numCamion == "" || recup_numTc == "") {
                        val snack = Snackbar.make(
                            binding.root,
                            "Veuillez renseigner les informations du TC",
                            Snackbar.LENGTH_LONG
                        )
                        snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        snack.setBackgroundTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray2
                            )
                        )
                        snack.show()
                        butAjouter.text = getText(R.string.but_addtc)
                        butAjouter.isEnabled = true
                        butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                    }
                } else if (typeTransat == "") {
                    val snack = Snackbar.make(
                        binding.root,
                        "Veuillez renseigner le type de transaction",
                        Snackbar.LENGTH_LONG
                    )
                    snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.gray2))
                    snack.show()
                    butAjouter.text = getText(R.string.but_addtc)
                    butAjouter.isEnabled = true
                    butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                }
                */

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
                        "phone_chauffeur_tc" to numPhoneChauffeurRmvSpx,
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}