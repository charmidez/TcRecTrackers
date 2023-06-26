package com.charmidezassiobo.tcrec.ui.suivietc

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.HeureStep
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcSousBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

class SuivietcSousFragment : Fragment() {

    private var _binding: FragmentSuivietcSousBinding? = null
    private val binding get() = _binding!!

    var stepvoyage: Int = 0
    var typetransact: String = ""
    var stepsBeanList: MutableList<StepBean> = ArrayList()
    lateinit var stepBean0_export: StepBean
    lateinit var stepBean1_export: StepBean
    lateinit var stepBean2_export: StepBean
    lateinit var stepBean3_export: StepBean
    lateinit var stepBean4_export: StepBean
    lateinit var stepBean5_export: StepBean

    //***//
    lateinit var stepBean0_import: StepBean
    lateinit var stepBean1_import: StepBean
    lateinit var stepBean2_import: StepBean
    lateinit var stepBean3_import: StepBean
    //***//

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuivietcSousBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val navController = findNavController()

        var bayoStepView: HorizontalStepView = binding.stepViewBaoyaVerticalViewItem

        var dateEtapeTcSub: TextView = binding.textViewEtapeEtDate
        var numImmatriculationCamionSub: EditText = binding.textViewCamionNumItem
        var phoneChauffeurSub: EditText = binding.textViewTCnumPhoneChauffeur
        var dateSaveTcSub: TextView = binding.textViewDateEnregistement

        var numBookingSub: EditText = binding.txtViewNumBooking

        var numTc1Sub: EditText = binding.textViewTCNumItem
        var numPlombTc1Sub: EditText = binding.numPlombTc1
        var numtc2Sub: EditText = binding.textViewTCNumItem2
        var numPlombTc2Sub: EditText = binding.numPlombTc2

        var btnBackToLeft = binding.btnBackLeft
        var btnNextToRight = binding.btnNextRight

        var viewList = listOf(numImmatriculationCamionSub, phoneChauffeurSub, numBookingSub, numTc1Sub, numPlombTc1Sub,
            numtc2Sub, numPlombTc2Sub,btnBackToLeft,btnNextToRight)

        var tableauSateHeureStep : MutableList<HeureStep>


        var btnMettreAJour: Button = binding.btnPopupMaj

        binding.btnBackToPreviousFragment.setOnClickListener {
            navController.popBackStack(R.id.navigation_suivietc, false)
        }

        val data = arguments
        typetransact = data?.getString("inputTypeTransact").toString()
        stepvoyage = data?.getInt("inputPositionVoyages")!!.toInt()
        dateEtapeTcSub.text = data?.getString("inputDate")
        numBookingSub.setText(data?.getString("inputBooking"))
        numImmatriculationCamionSub.setText(data?.getString("inputCamion"))
        numTc1Sub.setText(data?.getString("inputTc"))
        numPlombTc1Sub.setText(data?.getString("inputPlomb"))
        numtc2Sub.setText(data?.getString("inputTcSecond"))
        numPlombTc2Sub.setText(data?.getString("inputPlombSecond"))
        phoneChauffeurSub.setText(data?.getString("inputTelChauffeur"))

        var stepDateHeureReal = data?.getSerializable("inputStepDate") as? List<HeureStep>
        //val dateChiffres = stepDateHeureReal!![0].stepDateChiffre
        //val dateChiffre = tableauHeure?.stepDateLettre
        tableauSateHeureStep = stepDateHeureReal as MutableList<HeureStep>

        val date = data?.getString("inputDate")
        val num_TC = data?.getString("inputTc")
        val num_Camion = data?.getString("inputCamion")
        stepChange(stepvoyage, typetransact, bayoStepView)
        dateChangement(date.toString(), dateEtapeTcSub)
        dateSaveTcSub.text = "TC enrégistré le $date"

        when (typetransact) {
            "Import" -> {
                when (stepvoyage) {
                    0 -> dateEtapeTcSub.text =
                        "Tc arrivé au Port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}  "

                    1 -> dateEtapeTcSub.text =
                        "Tc en Dédouanement le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "

                    2 -> dateEtapeTcSub.text =
                        "Tc sorti du Port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "

                    3 -> dateEtapeTcSub.text =
                        "Tc arrivé à Destination le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "

                    4 -> dateEtapeTcSub.text =
                        "Transaction Terminé le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "
                }
            }

            "Export" -> {
                when (stepvoyage) {
                    0 -> dateEtapeTcSub.text =
                        "Tc au port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "

                    1 -> dateEtapeTcSub.text =
                        "Tc à l'usine le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"

                    2 -> dateEtapeTcSub.text =
                        "Tc en chargement le: ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"

                    3 -> dateEtapeTcSub.text =
                        "Tc à la douane le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"

                    4 -> dateEtapeTcSub.text =
                        "Tc sortie de l'entrepot le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"

                    5 -> dateEtapeTcSub.text =
                        "Tc plein et arrivé au port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"

                    6 -> dateEtapeTcSub.text =
                        "Transaction Terminé le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                }
            }
        }

        // Checker si le phone est disponible
        if (phoneChauffeurSub.text.toString() == "null" || phoneChauffeurSub == null || phoneChauffeurSub.text.toString() == "") {
            phoneChauffeurSub.setText("Non Disponible")
            phoneChauffeurSub.isEnabled = false
            phoneChauffeurSub.setBackground(
                AppCompatResources.getDrawable(
                    binding.root.context,
                    R.drawable.btn_drawable_not_selected
                )
            )
        }
        // Checker si le second tc est disponible
        if (numtc2Sub.text.isNullOrEmpty()) {
            //numtc2Sub.setText( "Non disponible")
            numtc2Sub.hint = "Non disponible"
            numtc2Sub.setBackground(
                AppCompatResources.getDrawable(
                    binding.root.context,
                    R.drawable.btn_drawable_not_selected
                )
            )
        }
        //Checker si le second plomb est dispo
        if (numPlombTc2Sub.text.isNullOrEmpty()) {
            numPlombTc2Sub.hint = "Non disponible"
            numPlombTc2Sub.setBackground(
                AppCompatResources.getDrawable(
                    binding.root.context,
                    R.drawable.btn_drawable_not_selected
                )
            )
        }
        //Checker si le premier plomb est dispo
        if (numPlombTc1Sub.text.isNullOrEmpty()) {
            numPlombTc1Sub.hint = "Non disponible"
            numPlombTc1Sub.setBackground(
                AppCompatResources.getDrawable(
                    binding.root.context,
                    R.drawable.btn_drawable_not_selected
                )
            )
        }

        btnBackToLeft.setOnClickListener {
            if(stepvoyage >0){
                tableauSateHeureStep.removeAt(stepvoyage)
                stepvoyage -= 1

                stepChange(stepvoyage, typetransact, bayoStepView)
            }
        }
        btnNextToRight.setOnClickListener {
            if (stepvoyage < 6){
                stepvoyage +=1
                var allFunctions = AllFunctions()
                var dateRealChiffre = allFunctions.miseEnPlaceDate(true)
                var dateRealLettre = allFunctions.miseEnPlaceDate(false)
                var heureRealChiffre = allFunctions.miseEnPlaceHeure()
                tableauSateHeureStep.add(HeureStep(dateRealChiffre,dateRealLettre,heureRealChiffre))
                stepChange(stepvoyage, typetransact, bayoStepView)
            }
        }

        //Appuie du button
        var iddoc = ""
        btnMettreAJour.setOnClickListener {
            val nouveauNumImmatriculation = numImmatriculationCamionSub.text.toString()
            val nouveauNumChauffeur = phoneChauffeurSub.text.toString()

            //les Tc et les plombs
            val nouveauNumBooking = numBookingSub.text.toString()
            val nouveauNumTc1 = numTc1Sub.text.toString()
            val nouveauNumTc2 = numtc2Sub.text.toString()
            val nouveauNumPlomb1 = numPlombTc1Sub.text.toString()
            val nouveauNumPlomb2 = numPlombTc2Sub.text.toString()

            val nouvelEtapeVoyages = stepvoyage

            val nouvelleHeureDateStep = tableauSateHeureStep

            Log.d("btn rouge", "Appuyé $nouveauNumImmatriculation")

            val db = FirebaseFirestore.getInstance()
            val query = db.collection("Voyage")
            //val query = db.collection("Voyagetest")
                .whereEqualTo("num_TC", num_TC)
                .whereEqualTo("num_Camion", num_Camion)
            query.get().addOnSuccessListener { documents ->
                for (document in documents) {
                    var docId = document.id
                    iddoc = docId
                    //val docRef = db.collection("Voyage").document(docId)

                    //val docRef = db.collection("Voyagetest").document(docId)
                    val docRef = db.collection("Voyage").document(docId)
                    docRef.update("num_Camion", nouveauNumImmatriculation)
                    docRef.update("phone_chauffeur_TC", nouveauNumChauffeur)

                    docRef.update("num_TC", nouveauNumTc1)
                    docRef.update("num_TC_Second", nouveauNumTc2)
                    docRef.update("num_plomb_TC", nouveauNumPlomb1)
                    docRef.update("num_plomb_TC_2", nouveauNumPlomb2)
                    docRef.update("num_Booking", nouveauNumBooking)

                    docRef.update("step_TC",nouvelEtapeVoyages)

                    docRef.update("lesStepDateHour",nouvelleHeureDateStep )

                }
            }
            btnMettreAJour.setBackground(
                AppCompatResources.getDrawable(
                    binding.root.context,
                    R.drawable.btn_drawable_not_selected
                )
            )
            btnMettreAJour.text = "Mis à jour éffectuée"
            for (allView in viewList){
                allView.isEnabled = false
            }
            btnMettreAJour.isEnabled = false

        }

        binding.imgViewDateStep.setOnClickListener {
            //navController.navigate(R.id.action_suivietcSousFragment_to_suivietcStepdateSousFragment)
            if (stepvoyage != null) {
                Log.d("stepvoyage", stepvoyage.toString())
                popUpShowDate(stepDateHeureReal, stepvoyage)
            }
        }

        /*        dateEtapeTcSub.setOnClickListener {
            //popUpShowDate(stepDateHeureReal, stepvoyage)
            if(stepvoyage != null){
                Log.d("stepvoyage",stepvoyage.toString())
                popUpShowDate(stepDateHeureReal, stepvoyage)
            }
        }*/

        return root
    }

    fun setupStepView(typetransact: String, bayoStepView: HorizontalStepView) {

        var txtSizeStep = 9

        when (typetransact) {
            "Import" -> {
                stepsBeanList.add(stepBean0_import)
                stepsBeanList.add(stepBean1_import)
                stepsBeanList.add(stepBean2_import)
                stepsBeanList.add(stepBean3_import)

                bayoStepView
                    .setStepViewTexts(stepsBeanList)
                    .setTextSize(txtSizeStep)
                    .setStepsViewIndicatorAttentionIcon(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_cam
                        )
                    )
                    .setStepsViewIndicatorDefaultIcon(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.next_step
                        )
                    )
                    .setStepsViewIndicatorUnCompletedLineColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .setStepViewUnComplectedTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .setStepViewComplectedTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompletedLineColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompleteIcon(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.check
                        )
                    )

            }

            "Export" -> {
                stepsBeanList.add(stepBean0_export)
                stepsBeanList.add(stepBean1_export)
                stepsBeanList.add(stepBean2_export)
                stepsBeanList.add(stepBean3_export)
                stepsBeanList.add(stepBean4_export)
                stepsBeanList.add(stepBean5_export)

                bayoStepView
                    .setStepViewTexts(stepsBeanList)
                    .setTextSize(txtSizeStep)

                    .setStepsViewIndicatorAttentionIcon(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_cam
                        )
                    )
                    .setStepsViewIndicatorDefaultIcon(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.next_step
                        )
                    )
                    .setStepsViewIndicatorUnCompletedLineColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .setStepViewUnComplectedTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .setStepViewComplectedTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompletedLineColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompleteIcon(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.check
                        )
                    )

            }
        }

    }

    fun stepChange(etape: Int, typetransact: String, bayoStepView: HorizontalStepView) {
        when (typetransact) {
            "Import" -> {
                when (etape) {
                    0 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 0)
                        stepBean1_import = StepBean("Dédouanement", -1)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(typetransact, bayoStepView)
                    }

                    1 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 0)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(typetransact, bayoStepView)
                    }

                    2 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 0)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(typetransact, bayoStepView)
                    }

                    3 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 0)
                        setupStepView(typetransact, bayoStepView)
                    }

                    4 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 1)
                        setupStepView(typetransact, bayoStepView)
                    }
                }
            }

            "Export" -> {
                when (etape) {
                    0 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 0)
                        stepBean1_export = StepBean("Usine", -1)
                        stepBean2_export = StepBean("Chargement", -1)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)
                    }

                    1 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 0)
                        stepBean2_export = StepBean("Chargement", -1)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)

                    }

                    2 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 0)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)

                    }

                    3 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 0)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)

                    }

                    4 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 0)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)

                    }

                    5 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 1)
                        stepBean5_export = StepBean("Arrivée Port", 0)
                        setupStepView(typetransact, bayoStepView)
                    }

                    6 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 1)
                        stepBean5_export = StepBean("Arrivée Port", 1)
                        setupStepView(typetransact, bayoStepView)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateChangement(dateParameter: String, date_etape_tc_popup: TextView) {
        val currentDate = LocalDate.now()
        var dateCurrent = "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"
        var dateCurrentHier =
            "${currentDate.dayOfMonth - 1}/${currentDate.monthValue}/${currentDate.year}"
        var dateCurrentAvantHier =
            "${currentDate.dayOfMonth - 2}/${currentDate.monthValue}/${currentDate.year}"
        //val date = tc.date_tc
        var date = dateParameter

        when (date) {
            dateCurrent -> {
                date_etape_tc_popup.text = "Aujourd'hui"
            }

            dateCurrentHier -> {
                date_etape_tc_popup.text = "Hier"
            }

            dateCurrentAvantHier -> {
                date_etape_tc_popup.text = "Avant Hier"
            }
        }
    }

    fun popUpShowDate(list : List<HeureStep>,step : Int){

        val v = View.inflate(binding.root.context, R.layout.popup_show_date_step, null)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(v)

        var textViewDateHeure_0 = v.findViewById<TextView>(R.id.textView_date_et_heure_0)
        var textViewDateHeure_1 = v.findViewById<TextView>(R.id.textView_date_et_heure_1)
        var textViewDateHeure_2 = v.findViewById<TextView>(R.id.textView_date_et_heure_2)
        var textViewDateHeure_3 = v.findViewById<TextView>(R.id.textView_date_et_heure_3)
        var textViewDateHeure_4 = v.findViewById<TextView>(R.id.textView_date_et_heure_4)
        var textViewDateHeure_5 = v.findViewById<TextView>(R.id.textView_date_et_heure_5)

        var linearLayout0 = v.findViewById<LinearLayout>(R.id.lineairLaout_etap_0)
        var linearLayout1 = v.findViewById<LinearLayout>(R.id.lineairLaout_etap_1)
        var linearLayout2 = v.findViewById<LinearLayout>(R.id.lineairLaout_etap_2)
        var linearLayout3 = v.findViewById<LinearLayout>(R.id.lineairLaout_etap_3)
        var linearLayout4 = v.findViewById<LinearLayout>(R.id.lineairLaout_etap_4)
        var linearLayout5 = v.findViewById<LinearLayout>(R.id.lineairLaout_etap_5)

        if(step != null){
            when(step){
                0 -> {
                    textViewDateHeure_0.text = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    linearLayout0.visibility = View.VISIBLE
                }
                1 -> {
                    textViewDateHeure_0.text = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    textViewDateHeure_1.text = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"

                    linearLayout0.visibility = View.VISIBLE
                    linearLayout1.visibility = View.VISIBLE
                }
                2 -> {
                    textViewDateHeure_0.text = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    textViewDateHeure_1.text = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    textViewDateHeure_2.text = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"

                    linearLayout0.visibility = View.VISIBLE
                    linearLayout1.visibility = View.VISIBLE
                    linearLayout2.visibility = View.VISIBLE
                }
                3 -> {
                    textViewDateHeure_0.text = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    textViewDateHeure_1.text = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    textViewDateHeure_2.text = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"
                    textViewDateHeure_3.text = "${list!![3].stepDateLettre} à ${list!![3].stepHeure}"

                    linearLayout0.visibility = View.VISIBLE
                    linearLayout1.visibility = View.VISIBLE
                    linearLayout2.visibility = View.VISIBLE
                    linearLayout3.visibility = View.VISIBLE
                }
                4 -> {
                    textViewDateHeure_0.text = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    textViewDateHeure_1.text = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    textViewDateHeure_2.text = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"
                    textViewDateHeure_3.text = "${list!![3].stepDateLettre} à ${list!![3].stepHeure}"
                    textViewDateHeure_4.text = "${list!![4].stepDateLettre} à ${list!![4].stepHeure}"

                    linearLayout0.visibility = View.VISIBLE
                    linearLayout1.visibility = View.VISIBLE
                    linearLayout2.visibility = View.VISIBLE
                    linearLayout3.visibility = View.VISIBLE
                    linearLayout4.visibility = View.VISIBLE
                }
                5 -> {
                    textViewDateHeure_0.text = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    textViewDateHeure_1.text = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    textViewDateHeure_2.text = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"
                    textViewDateHeure_3.text = "${list!![3].stepDateLettre} à ${list!![3].stepHeure}"
                    textViewDateHeure_4.text = "${list!![4].stepDateLettre} à ${list!![4].stepHeure}"
                    textViewDateHeure_5.text = "${list!![5].stepDateLettre} à ${list!![5].stepHeure}"

                    linearLayout0.visibility = View.VISIBLE
                    linearLayout1.visibility = View.VISIBLE
                    linearLayout2.visibility = View.VISIBLE
                    linearLayout3.visibility = View.VISIBLE
                    linearLayout4.visibility = View.VISIBLE
                    linearLayout5.visibility = View.VISIBLE
                }
                6 -> {
                    textViewDateHeure_0.text = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    textViewDateHeure_1.text = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    textViewDateHeure_2.text = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"
                    textViewDateHeure_3.text = "${list!![3].stepDateLettre} à ${list!![3].stepHeure}"
                    textViewDateHeure_4.text = "${list!![4].stepDateLettre} à ${list!![4].stepHeure}"
                    textViewDateHeure_5.text = "${list!![5].stepDateLettre} à ${list!![5].stepHeure}"

                    linearLayout0.visibility = View.VISIBLE
                    linearLayout1.visibility = View.VISIBLE
                    linearLayout2.visibility = View.VISIBLE
                    linearLayout3.visibility = View.VISIBLE
                    linearLayout4.visibility = View.VISIBLE
                    linearLayout5.visibility = View.VISIBLE
                }
            }
        }

/*        textViewDateHeure_0.text = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
        textViewDateHeure_1.text = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
        textViewDateHeure_2.text = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"
        textViewDateHeure_3.text = "${list!![3].stepDateLettre} à ${list!![3].stepHeure}"
        textViewDateHeure_4.text = "${list!![4].stepDateLettre} à ${list!![4].stepHeure}"
        textViewDateHeure_5.text = "${list!![5].stepDateLettre} à ${list!![5].stepHeure}"*/


        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}