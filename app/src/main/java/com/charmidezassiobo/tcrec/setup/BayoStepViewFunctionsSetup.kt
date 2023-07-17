package com.charmidezassiobo.tcrec.setup

import android.content.Context
import androidx.core.content.ContextCompat
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BayoStepViewFunctionsSetup {

    private val allFun = AllFunctions()
    private val allVal = AllVariables()

    private val dataBasePath = allVal.DBPATH
    private val seaCollectionPath = allVal.SEA_COLLECTION
    private val airCollectionPath = allVal.AIR_COLLECTION
    private val roadTrackingCollectionPath = allVal.ROAD_COLLECTION

    //Step SEA Export
    private val seaExport0 = allVal.SEA_EXPORT_ETAPE_0
    private val seaExport1 = allVal.SEA_EXPORT_ETAPE_1
    private val seaExport2 = allVal.SEA_EXPORT_ETAPE_2
    private val seaExport3 = allVal.SEA_EXPORT_ETAPE_3
    private val seaExport4 = allVal.SEA_EXPORT_ETAPE_4
    private val seaExport5 = allVal.SEA_EXPORT_ETAPE_5
    //Step SEA Import
    private val seaImport0 = allVal.SEA_IMPORT_ETAPE_0
    private val seaImport1 = allVal.SEA_IMPORT_ETAPE_1
    private val seaImport2 = allVal.SEA_IMPORT_ETAPE_2
    private val seaImport3 = allVal.SEA_IMPORT_ETAPE_3

    //Step ROAD
    private val roadTracking0 = allVal.ROAD_TRACKING_ETAPE_0
    private val roadTracking1 = allVal.ROAD_TRACKING_ETAPE_1
    private val roadTracking2 = allVal.ROAD_TRACKING_ETAPE_2
    private val roadTracking3 = allVal.ROAD_TRACKING_ETAPE_3

    private val db = Firebase.firestore
    private val voyTc = db.collection(dataBasePath)

    private val dbSea = db.collection(seaCollectionPath)
    private val dbAir = db.collection(airCollectionPath)
    private val dbRoad = db.collection(roadTrackingCollectionPath)

    var stepsBeanList: MutableList<StepBean> = ArrayList()
    // Variable stepBean Export
    lateinit var stepBean0: StepBean
    lateinit var stepBean1: StepBean
    lateinit var stepBean2: StepBean
    lateinit var stepBean3: StepBean
    lateinit var stepBean4: StepBean
    lateinit var stepBean5: StepBean

/*    lateinit var stepBean0_export: StepBean
    lateinit var stepBean1_export: StepBean
    lateinit var stepBean2_export: StepBean
    lateinit var stepBean3_export: StepBean
    lateinit var stepBean4_export: StepBean
    lateinit var stepBean5_export: StepBean
    lateinit var stepBean6_export: StepBean*/

    //****//
/*    lateinit var stepBean0_import: StepBean
    lateinit var stepBean1_import: StepBean
    lateinit var stepBean2_import: StepBean
    lateinit var stepBean3_import: StepBean
    lateinit var stepBean4_import: StepBean

    lateinit var stepBean0_other : StepBean
    lateinit var stepBean1_other : StepBean
    lateinit var stepBean2_other : StepBean
    lateinit var stepBean3_other : StepBean*/


    // Initialise Setupe Step View
    fun setupStepView(
        mContext: Context,
        typetransact: String,
        typeSousTransact : String,
        bayoStepView: HorizontalStepView
    ) {
        var txtSizeStep = 9
        when (typetransact) {
            "AIR" -> {
                when(typeSousTransact) {
                    "EXPORT (AIR)" -> {
                        stepsBeanList.add(stepBean0)
                        stepsBeanList.add(stepBean1)
                        stepsBeanList.add(stepBean2)
                        stepsBeanList.add(stepBean3)

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
                    "IMPORT (AIR)" -> {
                        stepsBeanList.add(stepBean0)
                        stepsBeanList.add(stepBean1)
                        stepsBeanList.add(stepBean2)
                        stepsBeanList.add(stepBean3)
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
            "SEA" -> {
                when(typeSousTransact){
                    "EXPORT (SEA)" -> {
                        stepsBeanList.add(stepBean0)
                        stepsBeanList.add(stepBean1)
                        stepsBeanList.add(stepBean2)
                        stepsBeanList.add(stepBean3)
                        stepsBeanList.add(stepBean4)
                        stepsBeanList.add(stepBean5)
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
                    "IMPORT (AIR)" -> {
                        stepsBeanList.add(stepBean0)
                        stepsBeanList.add(stepBean1)
                        stepsBeanList.add(stepBean2)
                        stepsBeanList.add(stepBean3)
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
            "ROAD" -> {
                stepsBeanList.add(stepBean0)
                stepsBeanList.add(stepBean1)
                stepsBeanList.add(stepBean2)
                stepsBeanList.add(stepBean3)
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
        typeSousTransact : String,
        bayoStepView: HorizontalStepView,
    ) {
        when (typetransact) {
            "AIR" -> {
                when(typeSousTransact){
                    "EXPORT (AIR)" -> {}
                    "IMPORT (AIR)" -> {}
                }
            }
            "SEA" -> {
                when(typeSousTransact){
                    "EXPORT (SEA)" -> {
                        when (etape) {
                            0 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaExport0, 0)
                                stepBean1 = StepBean(seaExport1, -1)
                                stepBean2 = StepBean(seaExport2, -1)
                                stepBean3 = StepBean(seaExport3, -1)
                                stepBean4 = StepBean(seaExport4, -1)
                                stepBean5 = StepBean(seaExport5, -1)
                                setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
                            }

                            1 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaExport0, 1)
                                stepBean1 = StepBean(seaExport1, 0)
                                stepBean2 = StepBean(seaExport2, -1)
                                stepBean3 = StepBean(seaExport3, -1)
                                stepBean4 = StepBean(seaExport4, -1)
                                stepBean5 = StepBean(seaExport5, -1)
                                setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
                            }

                            2 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaExport0, 1)
                                stepBean1 = StepBean(seaExport1, 1)
                                stepBean2 = StepBean(seaExport2, 0)
                                stepBean3 = StepBean(seaExport3, -1)
                                stepBean4 = StepBean(seaExport4, -1)
                                stepBean5 = StepBean(seaExport5, -1)
                                setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )

                            }

                            3 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaExport0, 1)
                                stepBean1 = StepBean(seaExport1, 1)
                                stepBean2 = StepBean(seaExport2, 1)
                                stepBean3 = StepBean(seaExport3, 0)
                                stepBean4 = StepBean(seaExport4, -1)
                                stepBean5 = StepBean(seaExport5, -1)
                                setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
                            }

                            4 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaExport0, 1)
                                stepBean1 = StepBean(seaExport1, 1)
                                stepBean2 = StepBean(seaExport2, 1)
                                stepBean3 = StepBean(seaExport3, 1)
                                stepBean4 = StepBean(seaExport4, 0)
                                stepBean5 = StepBean(seaExport5, -1)
                                setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
                            }

                            5 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaExport0, 1)
                                stepBean1 = StepBean(seaExport1, 1)
                                stepBean2 = StepBean(seaExport2, 1)
                                stepBean3 = StepBean(seaExport3, 1)
                                stepBean4 = StepBean(seaExport4, 1)
                                stepBean5 = StepBean(seaExport5, 0)
                                setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
                            }

                            6 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaExport0, 1)
                                stepBean1 = StepBean(seaExport1, 1)
                                stepBean2 = StepBean(seaExport2, 1)
                                stepBean3 = StepBean(seaExport3, 1)
                                stepBean4 = StepBean(seaExport4, 1)
                                stepBean5 = StepBean(seaExport5, 1)
                                setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
                            }
                        }
                    }
                    "IMORT (SEA)" -> {
                        when (etape) {
                            0 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaImport0, 0)
                                stepBean1 = StepBean(seaImport1, -1)
                                stepBean2 = StepBean(seaImport2, -1)
                                stepBean3 = StepBean(seaImport3, -1)
                                setupStepView(mContext, typetransact, typeSousTransact, bayoStepView )
                            }

                            1 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaImport0, 1)
                                stepBean1 = StepBean(seaImport1, 0)
                                stepBean2 = StepBean(seaImport2, -1)
                                stepBean3 = StepBean(seaImport3, -1)
                                setupStepView(mContext, typetransact, typeSousTransact, bayoStepView )
                            }

                            2 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaImport0, 1)
                                stepBean1 = StepBean(seaImport1, 1)
                                stepBean2 = StepBean(seaImport2, 0)
                                stepBean3 = StepBean(seaImport3, -1)
                                setupStepView(mContext, typetransact, typeSousTransact, bayoStepView )
                            }

                            3 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaImport0, 1)
                                stepBean1 = StepBean(seaImport1, 1)
                                stepBean2 = StepBean(seaImport2, 1)
                                stepBean3 = StepBean(seaImport3, 0)
                                setupStepView(mContext, typetransact, typeSousTransact, bayoStepView )
                            }

                            4 -> {
                                stepsBeanList = ArrayList()
                                stepBean0 = StepBean(seaImport0, 1)
                                stepBean1 = StepBean(seaImport1, 1)
                                stepBean2 = StepBean(seaImport2, 1)
                                stepBean3 = StepBean(seaImport3, 1)
                                setupStepView(mContext, typetransact, typeSousTransact, bayoStepView )
                            }
                        }
                    }
                }
            }
            "ROAD" -> {
                when (etape) {
                    0 -> {
                        stepsBeanList = ArrayList()
                        stepBean0 = StepBean(roadTracking0, 0)
                        stepBean1 = StepBean(roadTracking1, -1)
                        stepBean2 = StepBean(roadTracking2, -1)
                        stepBean3 = StepBean(roadTracking3, -1)
                        setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
                    }

                    1 -> {
                        stepsBeanList = ArrayList()
                        stepBean0 = StepBean(roadTracking0, 1)
                        stepBean1 = StepBean(roadTracking1, 0)
                        stepBean2 = StepBean(roadTracking2, -1)
                        stepBean3 = StepBean(roadTracking3, -1)
                        setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
                    }

                    2 -> {
                        stepsBeanList = ArrayList()
                        stepBean0 = StepBean(roadTracking0, 1)
                        stepBean1 = StepBean(roadTracking1, 1)
                        stepBean2 = StepBean(roadTracking2, 0)
                        stepBean3 = StepBean(roadTracking3, -1)
                        setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )

                    }

                    3 -> {
                        stepsBeanList = ArrayList()
                        stepBean0 = StepBean(roadTracking0, 1)
                        stepBean1 = StepBean(roadTracking1, 1)
                        stepBean2 = StepBean(roadTracking2, 1)
                        stepBean3 = StepBean(roadTracking3, 0)
                        setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
                    }

                    4 -> {
                        stepsBeanList = ArrayList()
                        stepBean0 = StepBean(roadTracking0, 1)
                        stepBean1 = StepBean(roadTracking1, 1)
                        stepBean2 = StepBean(roadTracking2, 1)
                        stepBean3 = StepBean(roadTracking3, 1)
                        setupStepView(mContext, typetransact, typeSousTransact,  bayoStepView )
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