package com.charmidezassiobo.tcrec.setup

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.dataclass.HeureStep
import com.charmidezassiobo.tcrec.dataclass.Sea
import com.charmidezassiobo.tcrec.dataclass.Air
import com.charmidezassiobo.tcrec.dataclass.Road
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BayoStepViewFunctionsSetup {

    private val allFun = AllFunctions()
    private val allVal = AllVariables()

    private val dataBasePath = allVal.dbPath
    private val seaCollectionPath = allVal.seaCollection
    private val airCollectionPath = allVal.airCollection
    private val roadTrackingCollectionPath = allVal.roadCollection

    private val db = Firebase.firestore
    private val voyTc = db.collection(dataBasePath)

    private val dbSea = db.collection(seaCollectionPath)
    private val dbAir = db.collection(airCollectionPath)
    private val dbRoad = db.collection(roadTrackingCollectionPath)

    var stepsBeanList: MutableList<StepBean> = ArrayList()
    // Variable stepBean Export
    lateinit var stepBean0_export: StepBean
    lateinit var stepBean1_export: StepBean
    lateinit var stepBean2_export: StepBean
    lateinit var stepBean3_export: StepBean
    lateinit var stepBean4_export: StepBean
    lateinit var stepBean5_export: StepBean
    lateinit var stepBean6_export: StepBean

    //****//
    lateinit var stepBean0_import: StepBean
    lateinit var stepBean1_import: StepBean
    lateinit var stepBean2_import: StepBean
    lateinit var stepBean3_import: StepBean
    lateinit var stepBean4_import: StepBean


    fun setupStepView(
        mContext: Context,
        typetransact: String,
        bayoStepView: HorizontalStepView
    ) {

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
                            mContext,
                            R.drawable.ic_cam
                        )
                    )
                    .setStepsViewIndicatorDefaultIcon(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.next_step
                        )
                    )
                    .setStepsViewIndicatorUnCompletedLineColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepViewUnComplectedTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepViewComplectedTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompletedLineColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompleteIcon(
                        ContextCompat.getDrawable(
                            mContext,
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
                            mContext,
                            R.drawable.ic_cam
                        )
                    )
                    .setStepsViewIndicatorDefaultIcon(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.next_step
                        )
                    )
                    .setStepsViewIndicatorUnCompletedLineColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepViewUnComplectedTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepViewComplectedTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompletedLineColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompleteIcon(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.check
                        )
                    )

            }
        }

    }

    fun stepChange(
        mContext: Context,
        etape: Int,
        typetransact: String,
        bayoStepView: HorizontalStepView,
    ) {
        when (typetransact) {
            "Import" -> {
                when (etape) {
                    0 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 0)
                        stepBean1_import = StepBean("Dédouanement", -1)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    1 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 0)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    2 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 0)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    3 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 0)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    4 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 1)
                        setupStepView(mContext, typetransact, bayoStepView )
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
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    1 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 0)
                        stepBean2_export = StepBean("Chargement", -1)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(mContext, typetransact, bayoStepView )

                    }

                    2 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 0)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(mContext, typetransact, bayoStepView )

                    }

                    3 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 0)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(mContext, typetransact, bayoStepView )

                    }

                    4 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 0)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(mContext, typetransact, bayoStepView )

                    }

                    5 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 1)
                        stepBean5_export = StepBean("Arrivée Port", 0)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    6 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 1)
                        stepBean5_export = StepBean("Arrivée Port", 1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }
                }
            }
        }
    }

/*    fun clickSuivant(
        mContext: Context,
        typeTransact: String,
        btnSuivant : Button,
        bayoStep : HorizontalStepView){
        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        var etape = 0
        if (isConnected) {
            // Connexion Internet disponible
            when(typeTransact){
                "Import" -> {
                    var iddoc  =""
                    btnSuivant.setOnClickListener{
                        if (etape < 4){
                            etape = etape + 1
                            stepChange(mContext, etape, typeTransact, bayoStep)
                            val db = FirebaseFirestore.getInstance()
                            val query = db.collection(dataBasePath)
                                .whereEqualTo("num_TC", tc.numTc1)
                                .whereEqualTo("num_Camion", tc.numCamion)
                            query.get().addOnSuccessListener { documents ->
                                for (document in documents) {
                                    var docId = document.id
                                    iddoc = docId
                                    val docRef = db.collection(dataBasePath).document(docId)
                                    docRef.update("step_TC", etape)
                                }
                                Log.d("Doc Id",iddoc)
                            }
                        }
                    }
                }
                "Export" -> {
                    var iddoc  =""
                    btnSuivant.setOnClickListener{
                        if (etape < 6){
                            etape = etape + 1
                            stepChange(mContext, etape, tc.typeTransact, bayoStep)
                            val db = FirebaseFirestore.getInstance()
                            val query = db.collection(dataBasePath)
                                .whereEqualTo("num_TC", tc.numTc1)
                                .whereEqualTo("num_Camion", tc.numCamion)
                            query.get().addOnSuccessListener { documents ->
                                for (document in documents) {
                                    var docId = document.id
                                    iddoc = docId
                                    val docRef = db.collection(dataBasePath).document(docId)
                                    docRef.update("step_TC", etape)
                                    //Mettre à jour la date à chaque suivant

                                    var allFunctions = AllFunctions()
                                    var dateRealChiffre = allFunctions.miseEnPlaceDate(true)
                                    var dateRealLettre = allFunctions.miseEnPlaceDate(false)
                                    var heureRealChiffre = allFunctions.miseEnPlaceHeure()

                                    //var dateActuelStep = HeureStep(dateRealChiffre,dateRealLettre,heureRealChiffre)
                                    //tableauSateHeureStep = dbOldDateStep

                                    tableauSateHeureStep.add(HeureStep(dateRealChiffre,dateRealLettre,heureRealChiffre))
                                    docRef.update("lesStepDateHour",tableauSateHeureStep )
                                }
                                Log.d("Doc Id",iddoc)
                            }
                        }
                    }
                }
            }
        }
    }*/
}