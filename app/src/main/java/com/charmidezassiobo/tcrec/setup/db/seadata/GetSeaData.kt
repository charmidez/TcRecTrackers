package com.charmidezassiobo.tcrec.setup.db.seadata

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.setup.data.HeureStep
import com.charmidezassiobo.tcrec.setup.data.Sea
import com.charmidezassiobo.tcrec.setup.functions.AllFunctions
import com.charmidezassiobo.tcrec.setup.functions.AllVariables
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*

class GetSeaData( var sea: Sea)  {

    private val allVar = AllVariables()
    private val allFun = AllFunctions()
    val db = Firebase.firestore
    val dbSea = db.collection(allVar.SEA_COLLECTION)




    fun getItemList(): kotlinx.coroutines.Deferred<MutableList<Sea>> =
        CoroutineScope(Dispatchers.IO).async {
            val itemsListSea = mutableListOf<Sea>()
            try {
                val querySnapshot = dbSea.get().await()
                for (document in querySnapshot.documents) {
                    if (document != null) {
                        val numTc1 = document.data?.get("num_tc_1").toString()
                        val numBooking = document.data?.get("num_booking").toString()
                        val numBl = document.data?.get("num_bl").toString()
                        val numCamion = document.data?.get("num_camion").toString()
                        val stepTc = document.getLong("step_tc")?.toInt()
                        val dateAjoutTc = document.data?.get("date_ajout_tc").toString()
                        val numPlombTc1 = document.data?.get("num_plomb_tc_1").toString()
                        val numPhoneChauffeur = document.data?.get("num_phone_chauffeur").toString()
                        val numTc2 = document.data?.get("num_tc_2").toString()
                        val numPlombTc2 = document.data?.get("num_plomb_tc_2").toString()
                        val typeTransact = document.data?.get("type_transact").toString()
                        val descTc = document.data?.get("desc_tc").toString()
                        val typeSousTransact = document.data?.get("type_sous_transact").toString()
                        val heureDeChaqueStepList =
                            document.data?.get("date_hour_step") as? MutableList<HashMap<String, String>>
                        if (stepTc != null && heureDeChaqueStepList != null) {
                            val heureDeChaqueStep = heureDeChaqueStepList.map {
                                HeureStep(
                                    it["stepDateChiffre"] ?: "",
                                    it["stepDateLettre"] ?: "",
                                    it["stepHeure"] ?: ""
                                )
                            }

                            itemsListSea.add(
                                Sea(
                                    "${allFun.removeSpaces(numBooking)}",
                                    "${allFun.removeSpaces(numBl)}",
                                    "${allFun.removeSpaces(numTc1)}",
                                    "${allFun.removeSpaces(numTc2)}",
                                    "${allFun.removeSpaces(numPlombTc1)}",
                                    "${allFun.removeSpaces(numPlombTc2)}",
                                    "${allFun.removeSpaces(numCamion)}",
                                    "${allFun.removeSpaces(numPhoneChauffeur)}",
                                    "${allFun.removeSpaces(descTc)}",
                                    "$dateAjoutTc",
                                    "$typeTransact",
                                    "$typeSousTransact",
                                    stepTc,
                                    heureDeChaqueStep!!.toMutableList()
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle the exception here if necessary.
            }

            return@async itemsListSea
        }

    fun retrieveSea() : MutableList<Sea>{
        return runBlocking {
            getItemList().await()
        }
    }

    fun saveSea(sea:Sea) {
        try {
            dbSea.add(sea!!)
            Log.d("INPUT_SEA", "isOk")
        } catch (e: Exception) {
            Log.d("INPUT_SEA_ERROR", e.message.toString())
        }
    }


    /*
fun retrieveSea(  ) : MutableList<Sea> {
    var itemResultSea = mutableListOf<Sea>()
    dbSea.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val itemsListSea = mutableListOf<Sea>()
                val querySnapshot = task.result
                for (document in querySnapshot?.documents ?: emptyList()) {
                    val numTc1 = document.data?.get("num_tc_1").toString()
                    val numBooking = document.data?.get("num_booking").toString()
                    val numBl = document.data?.get("num_bl").toString()
                    val numCamion = document.data?.get("num_camion").toString()
                    val stepTc = document.getLong("step_tc")?.toInt()
                    val dateAjoutTc = document.data?.get("date_ajout_tc").toString()
                    val numPlombTc1 = document.data?.get("num_plomb_tc_1").toString()
                    val numPhoneChauffeur = document.data?.get("num_phone_chauffeur").toString()
                    val numTc2 = document.data?.get("num_tc_2").toString()
                    val numPlombTc2 = document.data?.get("num_plomb_tc_2").toString()
                    val typeTransact = document.data?.get("type_transact").toString()
                    val typeSousTransact = document.data?.get("type_sous_transact").toString()
                    val descTc = document.data?.get("desc_tc").toString()
                    val heureDeChaqueStepList =  document.data?.get("date_hour_step") as? MutableList<HashMap<String, String>>
                    if (stepTc != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }

                        itemsListSea.add(
                            Sea(
                                allFun.removeSpaces(numBooking),
                                allFun.removeSpaces(numBl),
                                allFun.removeSpaces(numTc1),
                                allFun.removeSpaces(numTc2),
                                allFun.removeSpaces(numPlombTc1),
                                allFun.removeSpaces(numPlombTc2),
                                allFun.removeSpaces(numCamion),
                                allFun.removeSpaces(numPhoneChauffeur),
                                allFun.removeSpaces(descTc),
                                dateAjoutTc,
                                typeTransact,
                                typeSousTransact,
                                stepTc,
                                heureDeChaqueStep.toMutableList()
                            )
                        )
                    }
                }

                if (itemsListSea.isNullOrEmpty()) {
                    Log.d("SEA_COLLECTION", "empty")
                        itemResultSea = mutableListOf(
                            Sea("",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                0,
                                mutableListOf(HeureStep("","","",))
                            )
                        )
                } else {
                    itemResultSea = itemsListSea
                }
            } else {
                Log.d("SEA_ERROR", task.exception.toString())
            }
        }
    return itemResultSea
}

fun retrieveSea(chargement: View)  {
    try {
        val querySnapshot = dbSea.get()
        //val sb = StringBuilder()
        val itemsListSea = mutableListOf<Sea>()
        for (document in querySnapshot.documents) {
            if (document != null) {
                val numTc1 = document.data?.get("num_tc_1").toString()
                val numBooking = document.data?.get("num_booking").toString()
                val numBl = document.data?.get("num_bl").toString()
                val numCamion = document.data?.get("num_camion").toString()
                val stepTc = document.getLong("step_tc")?.toInt()
                val dateAjoutTc = document.data?.get("date_ajout_tc").toString()
                val numPlombTc1 = document.data?.get("num_plomb_tc_1").toString()
                val numPhoneChauffeur = document.data?.get("num_phone_chauffeur").toString()
                val numTc2 = document.data?.get("num_tc_2").toString()
                val numPlombTc2 = document.data?.get("num_plomb_tc_2").toString()
                val typeTransact = document.data?.get("type_transact").toString()
                val descTc = document.data?.get("desc_tc").toString()
                val typeSousTransact = document.data?.get("type_sous_transact").toString()
                val heureDeChaqueStepList =
                    document.data?.get("date_hour_step") as? MutableList<HashMap<String, String>>
                if (stepTc != null && heureDeChaqueStepList != null) {
                    val heureDeChaqueStep = heureDeChaqueStepList.map {
                        HeureStep(
                            it["stepDateChiffre"] ?: "",
                            it["stepDateLettre"] ?: "",
                            it["stepHeure"] ?: ""
                        )
                    }

                    itemsListSea.add(
                        Sea(
                            "${allFun.removeSpaces(numBooking)}",
                            "${allFun.removeSpaces(numBl)}",
                            "${allFun.removeSpaces(numTc1)}",
                            "${allFun.removeSpaces(numTc2)}",
                            "${allFun.removeSpaces(numPlombTc1)}",
                            "${allFun.removeSpaces(numPlombTc2)}",
                            "${allFun.removeSpaces(numCamion)}",
                            "${allFun.removeSpaces(numPhoneChauffeur)}",
                            "${allFun.removeSpaces(descTc)}",
                            "$dateAjoutTc",
                            "$typeTransact",
                            "$typeSousTransact",
                            stepTc,
                            heureDeChaqueStep!!.toMutableList()
                        )
                    )
                }
            }
        }
        withContext(Dispatchers.Main) {
            //Log.d("SEA_COLLECTION",)
            /*
            when (true) {
                itemsListSea.isNullOrEmpty() -> {
                    chargement.visibility = View.VISIBLE
                }

                else -> {
                    chargement.visibility = View.GONE
                    recyclerView!!.adapter = SEAadapter(mContext!!, itemsListSea, listener!!)
                }
            }
            */
            if (itemsListSea.isNullOrEmpty()) {
                chargement.visibility = View.VISIBLE
                Log.d("SEA_COLLECTION","plein")
            } else {
                chargement.visibility = View.GONE
                recyclerView!!.adapter = SEAadapter(mContext!!, itemsListSea, listener!!).apply {
                    Log.d("SEA_COLLECTION","plein")
                }
            }
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Log.d("SEA_ERROR", e.toString())
        }
    }
}



fun retrieveSea(chargement: View) {
    dbSea.get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val querySnapshot = task.result
                val itemsListSea = mutableListOf<Sea>()

                for (document in querySnapshot?.documents ?: emptyList()) {
                    val numTc1 = document.data?.get("num_tc_1").toString()
                    val numBooking = document.data?.get("num_booking").toString()
                    val numBl = document.data?.get("num_bl").toString()
                    val numCamion = document.data?.get("num_camion").toString()
                    val stepTc = document.getLong("step_tc")?.toInt()
                    val dateAjoutTc = document.data?.get("date_ajout_tc").toString()
                    val numPlombTc1 = document.data?.get("num_plomb_tc_1").toString()
                    val numPhoneChauffeur = document.data?.get("num_phone_chauffeur").toString()
                    val numTc2 = document.data?.get("num_tc_2").toString()
                    val numPlombTc2 = document.data?.get("num_plomb_tc_2").toString()
                    val typeTransact = document.data?.get("type_transact").toString()
                    val typeSousTransact = document.data?.get("type_sous_transact").toString()
                    val descTc = document.data?.get("desc_tc").toString()
                    val heureDeChaqueStepList =
                        document.data?.get("date_hour_step") as? MutableList<HashMap<String, String>>

                    if (stepTc != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }

                        itemsListSea.add(
                            Sea(
                                allFun.removeSpaces(numBooking),
                                allFun.removeSpaces(numBl),
                                allFun.removeSpaces(numTc1),
                                allFun.removeSpaces(numTc2),
                                allFun.removeSpaces(numPlombTc1),
                                allFun.removeSpaces(numPlombTc2),
                                allFun.removeSpaces(numCamion),
                                allFun.removeSpaces(numPhoneChauffeur),
                                allFun.removeSpaces(descTc),
                                dateAjoutTc,
                                typeTransact,
                                typeSousTransact,
                                stepTc,
                                heureDeChaqueStep.toMutableList()
                            )
                        )
                    }
                }

                if (itemsListSea.isNullOrEmpty()) {
                    chargement.visibility = View.VISIBLE
                    Log.d("SEA_COLLECTION", "empty")
                } else {
                    chargement.visibility = View.GONE
                    recyclerView!!.adapter = SEAadapter(mContext!!, itemsListSea, listener!!).apply {
                        Log.d("SEA_COLLECTION", "filled")
                    }
                }
            } else {
                Log.d("SEA_ERROR", task.exception.toString())
            }
        }
}
 */

    fun retrieveSeaBookingList(): ArrayList<String> {
        val numBookingList = arrayListOf<String>()

        val seaList = retrieveSea()
        for (sea in seaList) {
            val numBooking = sea.numBooking
            numBookingList.add(numBooking)
        }

        return numBookingList
    }


    fun retrieveTcNum(tcNum: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = dbSea
                .whereEqualTo("num_plomb_tc_1", tcNum)
                .whereEqualTo("num_plomb_tc_2", tcNum)
                .orderBy("num_booking")
                .get()
                .await()
            val sb = StringBuilder()
            for (document in querySnapshot.documents) {
                val sea = document.toObject<Sea>()
                sb.append("$sea\n")
            }
            withContext(Dispatchers.Main) {
                Log.d("SEA_COLLECTION", sb.toString())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.d("SEA_ERROR", e.toString())
            }
        }
    }


    /*
    fun realTimeRetrieveSea(chargement: View) {
        dbSea.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Log.d("EXCEPTION_SEA", it.message.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val itemsListSea = mutableListOf<Sea>()
                for (document in it) {
                    try {
                        if (document != null) {
                            val numTc1 = document.data?.get("num_tc_1").toString()
                            val numBooking = document.data?.get("num_booking").toString()
                            val numBl = document.data?.get("num_bl").toString()
                            val numCamion = document.data?.get("num_camion").toString()
                            val stepTc = document.getLong("step_tc")?.toInt()
                            val dateAjoutTc = document.data?.get("date_ajout_tc").toString()
                            val numPlombTc1 = document.data?.get("num_plomb_tc_1").toString()
                            val numPhoneChauffeur =
                                document.data?.get("num_phone_chauffeur").toString()
                            val numTc2 = document.data?.get("num_tc_2").toString()
                            val numPlombTc2 = document.data?.get("num_plomb_tc_2").toString()
                            val typeTransact = document.data?.get("type_transact").toString()
                            val descTc = document.data?.get("desc_tc").toString()
                            val typeSousTransact =
                                document.data?.get("type_sous_transact").toString()
                            val heureDeChaqueStepList =
                                document.data?.get("date_hour_step") as? MutableList<HashMap<String, String>>
                            if (stepTc != null && heureDeChaqueStepList != null) {
                                val heureDeChaqueStep = heureDeChaqueStepList.map {
                                    HeureStep(
                                        it["stepDateChiffre"] ?: "",
                                        it["stepDateLettre"] ?: "",
                                        it["stepHeure"] ?: ""
                                    )
                                }

                                itemsListSea.add(
                                    Sea(
                                        "${allFun.removeSpaces(numBooking)}",
                                        "${allFun.removeSpaces(numBl)}",
                                        "${allFun.removeSpaces(numTc1)}",
                                        "${allFun.removeSpaces(numTc2)}",
                                        "${allFun.removeSpaces(numPlombTc1)}",
                                        "${allFun.removeSpaces(numPlombTc2)}",
                                        "${allFun.removeSpaces(numCamion)}",
                                        "${allFun.removeSpaces(numPhoneChauffeur)}",
                                        "${allFun.removeSpaces(descTc)}",
                                        "$dateAjoutTc",
                                        "$typeTransact",
                                        "$typeSousTransact",
                                        stepTc,
                                        heureDeChaqueStep!!.toMutableList()
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("EXCEPTION_ERROR", e.message.toString())
                    }
                    itemsListSea.add(sea!!)
                }
                when (true) {
                    itemsListSea.isNullOrEmpty() -> {
                        chargement.visibility = View.VISIBLE
                    }

                    else -> {
                        chargement.visibility = View.GONE
                        recyclerView!!.adapter = SEAadapter(mContext!!, itemsListSea, listener!!)
                    }
                }
            }
        }
    }
    */



    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSeaStep(sea: Sea, etape: Int) {

        val query = dbSea
            .whereEqualTo("num_tc_1", sea.numTc1)
            .whereEqualTo("num_camion", sea.numCamion)
            .whereEqualTo("num_booking", sea.numBooking)
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var docId = document.id
                var dateRealChiffre = allFun.miseEnPlaceDate(true)
                var dateRealLettre = allFun.miseEnPlaceDate(false)
                var heureRealChiffre = allFun.miseEnPlaceHeure()
                val tableauSateHeureStep: MutableList<HeureStep>? =
                    document.data?.get("date_hour_step") as? MutableList<HeureStep>

                val docRef = dbSea.document(docId)
                docRef.update("step_tc", etape)
                tableauSateHeureStep!!.add(
                    HeureStep(
                        dateRealChiffre,
                        dateRealLettre,
                        heureRealChiffre
                    )
                )
                docRef.update("date_hour_step", tableauSateHeureStep)
            }
        }
    }

    fun updateRemoveSeaStep(sea: Sea, etape: Int) {

        val query = dbSea
            .whereEqualTo("num_tc_1", sea.numTc1)
            .whereEqualTo("num_camion", sea.numCamion)
            .whereEqualTo("num_booking", sea.numBooking)
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var docId = document.id
                val tableauSateHeureStep: MutableList<HeureStep>? =
                    document.data?.get("date_hour_step") as? MutableList<HeureStep>

                val docRef = dbSea.document(docId)
                docRef.update("step_tc", etape)
                //tableauSateHeureStep!!.add(HeureStep(dateRealChiffre,dateRealLettre,heureRealChiffre))
                //tableauSateHeureStep!!.remove(sea.dateHourStep)
                tableauSateHeureStep!!.removeAt(sea.stepTc)
                docRef.update("date_hour_step", tableauSateHeureStep)
            }
        }
    }

    fun updateSeaDB(sea: Sea, newSea: Sea) {
        val query = dbSea
            .whereEqualTo("num_tc_1", sea.numTc1)
            .whereEqualTo("num_camion", sea.numCamion)
            .whereEqualTo("num_booking", sea.numBooking)
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var docId = document.id

                val docRef = dbSea.document(docId)
                docRef.update("num_camion", newSea.numCamion)
                docRef.update("num_phone_chauffeur", newSea.numChauffeur)

                docRef.update("num_tc_1", newSea.numTc1)
                docRef.update("num_tc_2", newSea.numTc2)
                docRef.update("num_plomb_tc_1", newSea.numPlomb1)
                docRef.update("num_plomb_tc_2", newSea.numPlomb2)
                docRef.update("num_booking", newSea.numBooking)

                docRef.update("step_tc", newSea.stepTc)

                //docRef.update("date_hour_step", newSea.dateHourStep)

            }
        }
    }

    fun addPlombNum(
        numBooking: String,
        numTc1: String,
        numTc2: String?,
        newNumPlomb1: String,
        newNumPlomb2: String,
    ) {
        Log.d("PLOMB","BTN_PLOMB")
        val query = dbSea
            .whereEqualTo("num_booking", numBooking)
            .whereEqualTo("num_tc_1", numTc1)
            .whereEqualTo("num_tc_2", numTc2)
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var docId = document.id
                val docRef = dbSea.document(docId)
                docRef.update("num_plomb_tc_1", newNumPlomb1)
                docRef.update("num_plomb_tc_2", newNumPlomb2)
                Log.d("PLOMB1","$numTc1")
                Log.d("PLOMB2","$numTc2")
            }
        }
    }


    fun removeSea(recyclerView: RecyclerView, position : Int){

        val itemsSea = mutableListOf<Sea>()
        //val position = v.adapterPosition
        val itemVoyage = itemsSea[position]
        itemsSea.removeAt(position)
        recyclerView.adapter!!.notifyItemRemoved(position)

        val query = dbSea
            .whereEqualTo("num_tc_1", itemVoyage.numTc1)
            .whereEqualTo("num_camion", itemVoyage.numCamion)

        //récupérer l'id du document correspondant
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val docRef = dbSea.document(document.id)
                docRef.delete().addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                    // Affichage du message de confirmation du Snack Bar
                    //allFun.snackBarShowSucces("Transaction supprimé avec succès")
                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error deleting document", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error getting documents", e)
        }


    }





}