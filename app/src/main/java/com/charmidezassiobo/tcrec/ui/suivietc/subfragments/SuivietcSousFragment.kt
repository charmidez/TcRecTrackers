package com.charmidezassiobo.tcrec.ui.suivietc.subfragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import com.baoyachi.stepview.HorizontalStepView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.dataclass.HeureStep
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcSousBinding
import com.charmidezassiobo.tcrec.setup.functions.AllFunctions
import com.charmidezassiobo.tcrec.setup.functions.BayoStepViewFunctionsSetup
import com.charmidezassiobo.tcrec.setup.dataclass.Sea
import com.charmidezassiobo.tcrec.setup.db.GetSeaData
import com.charmidezassiobo.tcrec.ui.suivietc.bottomfragments.ShowStepDateBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable
import java.lang.Exception

class SuivietcSousFragment : Fragment() {

    private var _binding: FragmentSuivietcSousBinding? = null
    private val binding get() = _binding!!

    val stepBottomFragment : BottomSheetDialogFragment = ShowStepDateBottomSheetFragment()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuivietcSousBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val navController = findNavController()

        val mContext = binding.root.context
        val allFun = AllFunctions()
        var bayoSetup : BayoStepViewFunctionsSetup
        var getSeaData : GetSeaData

        // All View Variable
        var bayoStepView: HorizontalStepView = binding.stepViewBaoyaVerticalViewItem
        var dateEtapeTcSub: TextView = binding.textViewEtapeEtDate
        var numImmatriculationCamionSub: EditText = binding.textViewCamionNumItem
        var phoneChauffeurSub: EditText = binding.textViewTCnumPhoneChauffeur
        var dateSaveTcSub: TextView = binding.textViewDateEnregistement
        var numBookingSub: EditText = binding.txtViewNumBooking
        var numTc1Sub: EditText = binding.textViewTCNumItem
        var numPlombTc1Sub: EditText = binding.numPlombTc1
        var numTc2Sub: EditText = binding.textViewTCNumItem2
        var numPlombTc2Sub: EditText = binding.numPlombTc2
        var descTc = binding.textViewTCNumItemDescription

        //All button var
        var btnBackToPreviousFragment = binding.btnBackToPreviousFragment
        var btnBackToLeft = binding.btnBackLeft
        var btnNextToRight = binding.btnNextRight
        var btnDateStep = binding.imgViewDateStep
        var btnMettreAJour: Button = binding.btnPopupMaj

        //Color
        var gray = R.drawable.btn_drawable_not_selected

        var viewList = listOf(numImmatriculationCamionSub, phoneChauffeurSub, numBookingSub, numTc1Sub, numPlombTc1Sub,
            numTc2Sub, numPlombTc2Sub,btnBackToLeft,btnNextToRight)

        bayoSetup = BayoStepViewFunctionsSetup(bayoStepView)

        btnBackToPreviousFragment.setOnClickListener {
            navController.popBackStack(R.id.navigation_suivietc, false)
        }

        /***RÉCUP ARGUMENTS  DATA ***/
        val data = arguments
        var numBooking = data?.getString("inputBooking")!!
        var typetransact = data?.getString("inputTypeTransact")!!.toString()
        var sousTypeTransact = data?.getString("inputSousTypeTransact")!!.toString()
        var stepvoyage = data?.getInt("inputPositionVoyages")!!.toInt()
        var dateEtapeTc = data?.getString("inputDate")!!
        var numTc1 = data?.getString("inputTc")!!
        var numTc2 = data?.getString("inputTcSecond")!!
        var numPlomb1 = data?.getString("inputPlomb")!!
        var numPlomb2 = data?.getString("inputPlombSecond")!!
        var phoneChauffeur = data?.getString("inputTelChauffeur")!!
        var immatricualtionCamion = data?.getString("inputCamion")!!
        var contenu = data?.getString("inputDesc")!!
        var stepDateHeureReal = data?.getSerializable("inputStepDate")!! as? MutableList<HeureStep>

        var listStepDescription : MutableList<String> = mutableListOf()

        bayoSetup.stepChange(mContext, stepvoyage, typetransact, sousTypeTransact)
        dateEtapeTcSub.text = dateEtapeTc
        numBookingSub.setText(numBooking)
        numImmatriculationCamionSub.setText(immatricualtionCamion)
        numTc1Sub.setText(numTc1)
        numPlombTc1Sub.setText(numPlomb1)
        numTc2Sub.setText(numTc2)
        numPlombTc2Sub.setText(numPlomb2)
        phoneChauffeurSub.setText(phoneChauffeur)
        descTc.setText("Contenu : $contenu")

        allFun.dateChangement(dateEtapeTc.toString(), dateEtapeTcSub)
        dateSaveTcSub.text = "TC enrégistré le $dateEtapeTc"

        var sea = Sea(numBooking,
            null,
            numTc1,
            numTc2,
            numPlomb1,
            numPlomb2,
            immatricualtionCamion,
            phoneChauffeur,
            contenu,
            dateEtapeTc,
            typetransact,
            sousTypeTransact,
            stepvoyage,
            stepDateHeureReal!!
        )

        getSeaData = GetSeaData(mContext,null, sea, null)

            when (typetransact) {
                "AIR" -> {
                    statusAirTransaction(dateEtapeTcSub, sousTypeTransact, stepvoyage, stepDateHeureReal!!)
                }
                "SEA" -> {
                    listStepDescription = statusSeaTransaction(dateEtapeTcSub, sousTypeTransact, stepvoyage, stepDateHeureReal!!)
                }
                "ROAD" -> {
                    statusRoadTransaction()
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
        if (numTc2Sub.text.isNullOrEmpty()) {
            numTc2Sub.hint = "Non disponible"
            numTc2Sub.setBackground(AppCompatResources.getDrawable(mContext, gray))
        }

        //Checker si le second plomb est dispo
        if (numPlombTc2Sub.text.isNullOrEmpty()) {
            numPlombTc2Sub.hint = "Non disponible"
            numPlombTc2Sub.setBackground(AppCompatResources.getDrawable(mContext, gray))
        }

        //Checker si le premier plomb est dispo
        if (numPlombTc1Sub.text.isNullOrEmpty()) {
            numPlombTc1Sub.hint = "Non disponible"
            numPlombTc1Sub.setBackground(AppCompatResources.getDrawable(mContext, gray))
        }

        btnBackToLeft.setOnClickListener {
            if(stepvoyage >0){
                stepDateHeureReal!!.removeAt(stepvoyage)
                stepvoyage = stepvoyage - 1
                bayoSetup.stepChange(mContext, stepvoyage, typetransact, sousTypeTransact)
                getSeaData.updateRemoveSeaStep(sea, stepvoyage)
            }
        }

        // Appuie suivant prochaine étape
        btnNextToRight.setOnClickListener {
            if (stepvoyage < 6){
                stepvoyage = stepvoyage + 1
                bayoSetup.stepChange(mContext, stepvoyage, typetransact, sousTypeTransact)
                getSeaData.updateSeaStep(sea, stepvoyage)
            }
        }

        //Appuie du button Mettre à jour
        btnMettreAJour.setOnClickListener {
            val recupSea = Sea()
            recupSea.numCamion = numImmatriculationCamionSub.text.toString()
            recupSea.numChauffeur = phoneChauffeurSub.text.toString()

            //les Tc et les plombs
            recupSea.numBooking = numBookingSub.text.toString()
            recupSea.numTc1 = numTc1Sub.text.toString()
            recupSea.numTc2 = numTc2Sub.text.toString()
            recupSea.numPlomb1 = numPlombTc1Sub.text.toString()
            recupSea.numPlomb2 = numPlombTc2Sub.text.toString()

            recupSea.stepTc = stepvoyage

            //recupSea.dateHourStep = stepDateHeureReal!!

            btnMettreAJour.setBackground(AppCompatResources.getDrawable(mContext, gray))
            btnMettreAJour.text = "Mis à jour éffectuée"

            for (allView in viewList){
                allView.isEnabled = false
            }
            getSeaData.updateSeaDB(sea, recupSea)
            btnMettreAJour.isEnabled = false

        }

        btnDateStep.setOnClickListener {
            if (stepvoyage != null) {
                val inputListStepDesc : ArrayList<String> = listStepDescription as ArrayList
                val showStepDateBottomSheetFragment = ShowStepDateBottomSheetFragment.newInstance(inputListStepDesc)
                showStepDateBottomSheetFragment.show(parentFragmentManager, showStepDateBottomSheetFragment.tag)
            }
        }

        return root
    }


    fun statusSeaTransaction(dateEtapeTcSub : TextView, sousTypeTransact : String,  stepvoyage : Int,  stepDateHeureReal : MutableList<HeureStep>? ) : MutableList<String>{
        var statusSeaTransactionList = mutableListOf<String>()
        when(sousTypeTransact){
            "EXPORT (SEA)" -> {
                when (stepvoyage) {
                    0 -> {
                        val textEtape = "Tc dans au port : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure} "
                        dateEtapeTcSub.text = textEtape

                        statusSeaTransactionList.add(textEtape)
                    }
                    1 -> {
                        val textEtape = "Tc à l'usine le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                        dateEtapeTcSub.text = textEtape

                        statusSeaTransactionList.add("Tc dans au port : ${stepDateHeureReal!![0].stepDateChiffre} à ${stepDateHeureReal!![0].stepHeure} ")

                        statusSeaTransactionList.add(textEtape)
                    }

                    2 -> {
                        val textEtape = "Tc en chargement le: ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                        dateEtapeTcSub.text = textEtape

                        statusSeaTransactionList.add("Tc dans au port : ${stepDateHeureReal!![0].stepDateChiffre} à ${stepDateHeureReal!![0].stepHeure} ")
                        statusSeaTransactionList.add("Tc à l'usine le : ${stepDateHeureReal!![1].stepDateChiffre} à ${stepDateHeureReal!![1].stepHeure}")

                        statusSeaTransactionList.add(textEtape)
                    }

                    3 -> {
                        val textEtape =  "Tc à la douane le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                        dateEtapeTcSub.text = textEtape

                        statusSeaTransactionList.add("Tc dans au port : ${stepDateHeureReal!![0].stepDateChiffre} à ${stepDateHeureReal!![0].stepHeure} ")
                        statusSeaTransactionList.add("Tc à l'usine le : ${stepDateHeureReal!![1].stepDateChiffre} à ${stepDateHeureReal!![1].stepHeure}")
                        statusSeaTransactionList.add("Tc en chargement le: ${stepDateHeureReal!![2].stepDateChiffre} à ${stepDateHeureReal!![2].stepHeure}")

                        statusSeaTransactionList.add(textEtape)
                    }

                    4 -> {
                        val textEtape = "Tc sortie de l'entrepot le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                        dateEtapeTcSub.text = textEtape

                        statusSeaTransactionList.add("Tc dans au port : ${stepDateHeureReal!![0].stepDateChiffre} à ${stepDateHeureReal!![0].stepHeure} ")
                        statusSeaTransactionList.add("Tc à l'usine le : ${stepDateHeureReal!![1].stepDateChiffre} à ${stepDateHeureReal!![1].stepHeure}")
                        statusSeaTransactionList.add("Tc en chargement le: ${stepDateHeureReal!![2].stepDateChiffre} à ${stepDateHeureReal!![2].stepHeure}")
                        statusSeaTransactionList.add("Tc à la douane le : ${stepDateHeureReal!![3].stepDateChiffre} à ${stepDateHeureReal!![3].stepHeure}")

                        statusSeaTransactionList.add(textEtape)
                    }

                    5 -> {
                        val textEtape = "Tc plein et arrivé au port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}"
                        dateEtapeTcSub.text = textEtape

                        statusSeaTransactionList.add("Tc dans au port : ${stepDateHeureReal!![0].stepDateChiffre} à ${stepDateHeureReal!![0].stepHeure} ")
                        statusSeaTransactionList.add("Tc à l'usine le : ${stepDateHeureReal!![1].stepDateChiffre} à ${stepDateHeureReal!![1].stepHeure}")
                        statusSeaTransactionList.add("Tc en chargement le: ${stepDateHeureReal!![2].stepDateChiffre} à ${stepDateHeureReal!![2].stepHeure}")
                        statusSeaTransactionList.add("Tc à la douane le : ${stepDateHeureReal!![3].stepDateChiffre} à ${stepDateHeureReal!![3].stepHeure}")
                        statusSeaTransactionList.add("Tc sortie de l'entrepot le : ${stepDateHeureReal!![4].stepDateChiffre} à ${stepDateHeureReal!![5].stepHeure}")

                        statusSeaTransactionList.add(textEtape)
                    }
                }

            }
            "IMPORT (SEA)" -> {
                when (stepvoyage) {
                    0 -> {
                        dateEtapeTcSub.text =
                            "Tc arrivé au Port le : ${stepDateHeureReal!![stepvoyage].stepDateChiffre} à ${stepDateHeureReal!![stepvoyage].stepHeure}  "
                    }

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
        }
        return statusSeaTransactionList
    }

    fun statusAirTransaction(dateEtapeTcSub : TextView, sousTypeTransact : String,  stepvoyage : Int,  stepDateHeureReal : MutableList<HeureStep>?){
        when(sousTypeTransact){
            "AIR (EXPORT)" -> {

            }
            "AIR (IMPORT)" -> {

            }
        }
    }

    fun statusRoadTransaction(){}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}