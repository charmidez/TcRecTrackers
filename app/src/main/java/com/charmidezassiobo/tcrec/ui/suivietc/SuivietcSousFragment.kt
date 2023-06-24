package com.charmidezassiobo.tcrec.ui.suivietc

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.navigation.fragment.findNavController
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.VerticalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.HeureStep
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcSousBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.shuhart.stepview.StepView
import java.time.LocalDate

class SuivietcSousFragment : Fragment() {

    private var _binding: FragmentSuivietcSousBinding? = null
    private val binding get() = _binding!!

    var stepvoyage : Int = 0
    var typetransact : String = ""
    var stepsBeanList: MutableList<StepBean> = ArrayList()
    lateinit var stepBean0_export : StepBean
    lateinit var stepBean1_export : StepBean
    lateinit var stepBean2_export : StepBean
    lateinit var stepBean3_export : StepBean
    lateinit var stepBean4_export : StepBean
    lateinit var stepBean5_export : StepBean
    //***//
    lateinit var stepBean0_import : StepBean
    lateinit var stepBean1_import : StepBean
    lateinit var stepBean2_import : StepBean
    lateinit var stepBean3_import : StepBean
    //***//

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSuivietcSousBinding.inflate(inflater,container, false)
        val root: View = binding.root

        val navController = findNavController()

        var bayoStepView : HorizontalStepView = binding.stepViewBaoyaVerticalViewItem

        var date_etape_tc_popup : TextView = binding.textViewEtapeEtDate
        var numBooking : TextView = binding.txtViewNumBooking
        var numcamion_popup : EditText = binding.textViewCamionNumItem
        var  numtc_popup : TextView = binding.textViewTCNumItem
        var num_plomb_tc1 : TextView = binding.numPlombTc1
        var numtcsecond_popup : TextView = binding.textViewTCNumItem2
        var num_plomb_tc2 : TextView = binding.numPlombTc2
        var phoneChauffeur_popup : EditText = binding.textViewTCnumPhoneChauffeur
        var dateSaveTc : TextView = binding.textViewDateEnregistement
        var imgBtnDate : ImageView = binding.imgViewDateStep

        //var tableauHeure : MutableList<HeureStep>


        var btn_maj_popup : Button = binding.btnPopupMaj

        val data = arguments
        typetransact = data?.getString("inputTypeTransact").toString()
        stepvoyage =  data?.getInt("inputPositionVoyages")!!.toInt()
        date_etape_tc_popup.text = data?.getString("inputDate")
        numBooking.text = data?.getString("inputBooking")
        numcamion_popup.setText(data?.getString("inputCamion"))
        numtc_popup.text = data?.getString("inputTc")
        num_plomb_tc1.text = data?.getString("inputPlomb")
        numtcsecond_popup.text = data?.getString("inputTcSecond")
        num_plomb_tc2.text = data?.getString("inputPlombSecond")
        phoneChauffeur_popup.setText(data?.getString("inputTelChauffeur"))

        var stepDateHeureReal = data?.getSerializable("inputStepDate") as? List<HeureStep>
        val dateChiffres = stepDateHeureReal!![0].stepDateChiffre
        //val dateChiffre = tableauHeure?.stepDateLettre

        val date = data?.getString("inputDate")
        val num_TC = data?.getString("inputTc")
        val num_Camion = data?.getString("inputCamion")
        stepChange(stepvoyage, typetransact, bayoStepView)
        dateChangement(date.toString(), date_etape_tc_popup)
        dateSaveTc.text = "TC enrégistré le ${stepDateHeureReal!![0].stepDateLettre}"

        when(typetransact){
            "Import" -> {
                when (stepvoyage) {
                    0 -> date_etape_tc_popup.text = "Tc arrivé au Port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}  "
                    1 -> date_etape_tc_popup.text = "Tc en Dédouanement le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "
                    2 -> date_etape_tc_popup.text = "Tc sorti du Port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "
                    3 -> date_etape_tc_popup.text = "Tc arrivé à Destination le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "
                    4 -> date_etape_tc_popup.text = "Transaction Terminé le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "
                }
            }
            "Export" -> {
                when(stepvoyage){
                    0 -> date_etape_tc_popup.text = "Tc au port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "
                    1 -> date_etape_tc_popup.text = "Tc à l'usine le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                    2 -> date_etape_tc_popup.text = "Tc en chargement le: ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                    3 -> date_etape_tc_popup.text = "Tc à la douane le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                    4 -> date_etape_tc_popup.text = "Tc arrivé sortie de l'entrepot le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                    5 -> date_etape_tc_popup.text = "Tc plein arrivé au port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                    6 -> date_etape_tc_popup.text = "Transaction Terminé le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                }
            }
        }

        // Checker si le phone est disponible
        if(phoneChauffeur_popup.text.toString() == "null" || phoneChauffeur_popup == null || phoneChauffeur_popup.text.toString() == ""){
            phoneChauffeur_popup.setText("Non Disponible")
            phoneChauffeur_popup.isEnabled = false
            phoneChauffeur_popup.setBackground(AppCompatResources.getDrawable(binding.root.context, R.drawable.btn_drawable_not_selected))
        }
        // Checker si le second tc est disponible
        if (numtcsecond_popup.text  == "null" || numtcsecond_popup.text  == null || numtcsecond_popup.text  == "" ){
            numtcsecond_popup.text = "Non disponible"
            numtcsecond_popup.setBackground(AppCompatResources.getDrawable(binding.root.context, R.drawable.btn_drawable_not_selected))
        }
        //Checker si le second plomb est dispo
        if (num_plomb_tc2.text == "null" || num_plomb_tc2.text == null || num_plomb_tc2.text == "" ){
            num_plomb_tc2.isInvisible = true
        }
        //Checker si le premier plomb est dispo
        if (num_plomb_tc1.text == "null" || num_plomb_tc1.text == null || num_plomb_tc1.text == "" ){
            num_plomb_tc1.isInvisible = true
        }

        var iddoc  =""
        btn_maj_popup.setOnClickListener {
            val nouveau_num_immatriculation = numcamion_popup.text.toString()
            val nouveau_num_chauffeur = phoneChauffeur_popup.text.toString()
            Log.d("btn rouge", "Appuyé $nouveau_num_immatriculation")

            val db = FirebaseFirestore.getInstance()
            //val query = db.collection("Voyage")
            val query = db.collection("Voyagetest")
                .whereEqualTo("num_TC", num_TC)
                .whereEqualTo("num_Camion", num_Camion)
            query.get().addOnSuccessListener { documents ->
                for (document in documents) {
                    var docId = document.id
                    iddoc = docId
                    //val docRef = db.collection("Voyage").document(docId)
                    val docRef = db.collection("Voyagetest").document(docId)
                    docRef.update("num_Camion", nouveau_num_immatriculation)
                    docRef.update("phone_chauffeur_TC",nouveau_num_chauffeur)
                }
            }
            btn_maj_popup.setBackground(AppCompatResources.getDrawable(binding.root.context, R.drawable.btn_drawable_not_selected))
            btn_maj_popup.text = "Mis à jour éffectué"
        }

        binding.imgViewDateStep.setOnClickListener {
            navController.navigate(R.id.action_suivietcSousFragment_to_suivietcStepdateSousFragment)
        }

        return root
    }

   fun setupStepView(typetransact : String, bayoStepView : HorizontalStepView){

       var txtSizeStep  = 9

       when(typetransact){
           "Import" -> {
               stepsBeanList.add(stepBean0_import)
               stepsBeanList.add(stepBean1_import)
               stepsBeanList.add(stepBean2_import)
               stepsBeanList.add(stepBean3_import)

               bayoStepView
                   .setStepViewTexts(stepsBeanList)
                   .setTextSize(txtSizeStep)
                   .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_cam))
                   .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(requireContext(), R.drawable.next_step))
                   .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(requireContext(), R.color.blue))
                   .setStepViewUnComplectedTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                   .setStepViewComplectedTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                   .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(requireContext(), R.color.blue))
                   .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(requireContext(), R.drawable.check))

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

                   .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_cam))
                   .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(requireContext(), R.drawable.next_step))
                   .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(requireContext(), R.color.blue))
                   .setStepViewUnComplectedTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                   .setStepViewComplectedTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                   .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(requireContext(), R.color.blue))
                   .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(requireContext(), R.drawable.check))

           }
       }

   }

    fun stepChange(etape : Int, typetransact: String, bayoStepView : HorizontalStepView){
        when(typetransact){
            "Import" -> {
                when(etape){
                    0 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 0)
                        stepBean1_import = StepBean("Dédouanement", -1)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(typetransact, bayoStepView)
                    }
                    1 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 0)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(typetransact, bayoStepView)
                    }
                    2 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 0)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(typetransact, bayoStepView)
                    }
                    3 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 0)
                        setupStepView(typetransact, bayoStepView)
                    }
                    4 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 1)
                        setupStepView(typetransact, bayoStepView)
                    }
                }
            }
            "Export" -> {
                when(etape){
                    0 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_export = StepBean("Port", 0)
                        stepBean1_export = StepBean("Usine", -1)
                        stepBean2_export = StepBean("Chargement", -1)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)
                    }
                    1 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 0)
                        stepBean2_export = StepBean("Chargement", -1)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)

                    }
                    2 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 0)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)

                    }
                    3 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 0)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)

                    }
                    4 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 0)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView)

                    }
                    5 -> {
                        stepsBeanList= ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 1)
                        stepBean5_export = StepBean("Arrivée Port", 0)
                        setupStepView(typetransact, bayoStepView)
                    }
                    6 -> {
                        stepsBeanList= ArrayList()
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
    fun dateChangement(dateParameter :  String, date_etape_tc_popup : TextView){
        val currentDate = LocalDate.now()
        var dateCurrent = "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"
        var dateCurrentHier = "${currentDate.dayOfMonth - 1}/${currentDate.monthValue}/${currentDate.year}"
        var dateCurrentAvantHier = "${currentDate.dayOfMonth - 2}/${currentDate.monthValue}/${currentDate.year}"
        //val date = tc.date_tc
        var date = dateParameter

        when (date){
            dateCurrent -> { date_etape_tc_popup.text = "Aujourd'hui" }
            dateCurrentHier -> { date_etape_tc_popup.text = "Hier"}
            dateCurrentAvantHier -> { date_etape_tc_popup.text = "Avant Hier"}
        }
    }

    fun popUpShowDate(list : MutableList<HeureStep>, step : Int){

        val v = View.inflate(binding.root.context, R.layout.popup_show_date_step, null)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(v)

        /*when(step){
            0 -> { "Tc arrivée au port le : ${list!![stepvoyage].stepDateChiffre} à ${list!![stepvoyage].stepHeure} " }
        }*/
        val container = v.findViewById<LinearLayout>(R.id.lineair_container)

        list.forEach { element ->
            var textView = v.findViewById<TextView>(R.id.textView_date_et_heure_1)
            textView.text = "${list!![stepvoyage].stepDateChiffre} à ${list!![stepvoyage].stepHeure}"

        }

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}