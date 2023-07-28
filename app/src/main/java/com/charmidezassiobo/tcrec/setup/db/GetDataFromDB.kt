package com.charmidezassiobo.tcrec.setup.db

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.dataclass.HeureStep
import com.charmidezassiobo.tcrec.setup.dataclass.Sea
import com.charmidezassiobo.tcrec.setup.functions.AllFunctions
import com.charmidezassiobo.tcrec.setup.Adapter.SEAadapter
import com.charmidezassiobo.tcrec.setup.functions.AllVariables
import com.charmidezassiobo.tcrec.setup.interfaces.RecyclerViewClickItemInterface
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GetDataFromDB() {

    private val allFun = AllFunctions()
    private val allVar = AllVariables()

     val db = Firebase.firestore

    private val dbSea = db.collection(allVar.SEA_COLLECTION)
    private val dbAir = db.collection(allVar.AIR_COLLECTION)
    private val dbRoad = db.collection(allVar.ROAD_COLLECTION)

    //*******SEA ******//
    fun seaCallBack(callback: () -> Unit) {
        var itemListSeaTransact = arrayListOf<Sea>()
        dbSea.get().addOnSuccessListener { documents ->
            for (document in documents.documents) {
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

                        itemListSeaTransact.add(
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
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
                //allFun.snackBarShowWarning(mContext, root, exception.toString())
            }
    }

    //Get SEA Data
    fun getSEAdataFromdb() : MutableList<Sea> {
        var itemListSeaTransact = mutableListOf<Sea>()
        dbSea.get().addOnSuccessListener { documents ->
            for (document in documents.documents) {
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

                        itemListSeaTransact.add(
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
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
        return itemListSeaTransact
    }

    //Get SEA Booking Number
    fun getSeaBookingdataFromdb() : ArrayList<String>{
        var listBooking = arrayListOf<String>()
        dbSea.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val numBooking = document.data.get("num_booking").toString()
                    listBooking.add(numBooking)
                }
            }
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
        return listBooking
    }

    //Get SEA Booking With Title "Liste des numéros Booking"
    fun getSeaBookingWithTitledataFromdb() : ArrayList<String>{
        var listBooking = arrayListOf<String>()
        listBooking.add("Tous les bookings")
        dbSea.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val numBooking = document.data.get("num_booking").toString()
                    listBooking.add(numBooking)
                }
            }
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
        return listBooking
    }

    //Input Data In Sea Recycler View
    fun inputItemInSeaRecyclerView(mContext : Context,
                                   listener: RecyclerViewClickItemInterface,
                                   chargement: View,
                                   recyclerViewTc: RecyclerView
    ): MutableList<Sea>{
        var itemsTc : MutableList<Sea> = mutableListOf()
            itemsTc = getSEAdataFromdb()
            if (itemsTc.isNotEmpty()){
                recyclerViewTc.adapter = SEAadapter(mContext,itemsTc, listener)
                chargement.visibility = View.GONE
            } else {
                chargement.visibility = View.VISIBLE
            }

        return itemsTc
    }

    //Remove Data In Sea RecyclerView
    fun removedItemInSeaRecyclerView(
        context: Context,
        recyclerView: RecyclerView,
        v: RecyclerView.ViewHolder
    ){
        // Suppression de l'élément de la RecyclerView
        val itemsSea = mutableListOf<Sea>()
        val position = v.adapterPosition
        val itemVoyage = itemsSea[position]
        itemsSea.removeAt(position)
        recyclerView.adapter!!.notifyItemRemoved(position)

        //récupérer le document correspondant à la position
        val query = dbSea
            .whereEqualTo("num_tc_1", itemVoyage.numTc1)
            .whereEqualTo("num_camion", itemVoyage.numCamion)

        //récupérer l'id du document correspondant
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val docRef = dbSea.document(document.id)
                docRef.delete().addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                    // Affichage du message de confirmation
                    val snack = Snackbar.make(recyclerView, "TC Supprimé", Snackbar.LENGTH_LONG)
                    snack.setTextColor(ContextCompat.getColor(context, R.color.white))
                    snack.setBackgroundTint(ContextCompat.getColor(context, R.color.gray2))
                    snack.show()
                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error deleting document", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error getting documents", e)
        }
    }


    fun saveSea(sea : Sea) = CoroutineScope(Dispatchers.IO).launch {
        try {
        dbSea.add(sea).await()
            withContext(Dispatchers.Main){
                Log.d("INPUT_SEA", "isOk")
            }
        } catch (e : Exception) {
            withContext(Dispatchers.Main){
                Log.d("INPUT_SEA_ERROR", e.message.toString())
            }
        }
    }

    fun retrieveSea() = CoroutineScope(Dispatchers.IO).launch  {
        try {
            val querySnapshot = dbSea.get().await()
            val sb = StringBuilder()
            for (document in querySnapshot.documents){
                val sea = document.toObject<Sea>()
                sb.append("$sea\n")
            }
            withContext(Dispatchers.Main){
                Log.d("SEA_COLLECTION", sb.toString())
            }
        } catch (e : Exception ) {
            withContext(Dispatchers.Main){
                Log.d("SEA_ERROR", e.toString())
            }
        }
    }

    fun realTimeRetrieveSea(){
        dbSea.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Log.d("EXCEPTION_SEA", it.message.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let{
                val sb = StringBuilder()
                for (document in it){
                    val sea = document.toObject<Sea>()
                    sb.append("$sea\n")
                }
                Log.d("SEA_COLLECTION", sb.toString())
            }
        }
    }






    //*******AIR ******//
    fun getAIRdataFromdb(){}

    //*******ROAD ******//
    fun getROADdataFromdb(){}


/*    //Sea Tc
    fun updateTc(callback: () -> Unit) {
        var itemListTc = arrayListOf<Sea>()
        var listBookingInUpdateFun = arrayListOf<String>()
        itemListTc.clear()
        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val numTc1 = document.data.get("num_tc_1").toString()
                    val numBooking = document.data.get("num_booking").toString()
                    val numCamion = document.data.get("num_camion").toString()
                    val stepTc = document.getLong("step_tc")?.toInt()
                    val dateAjoutTc = document.data.get("date_ajout_tc").toString()
                    val numPlombTc1 = document.data.get("num_plomb_tc_1").toString()
                    val numPhoneChauffeur = document.data.get("num_phone_chauffeur").toString()
                    val numTc2 = document.data.get("num_tc_2").toString()
                    val numPlombTc2 = document.data.get("num_plomb_tc_2").toString()
                    val typeTransact = document.data.get("type_transact").toString()
                    val descTc = document.data.get("desc_tc").toString()
                    val typeSousTransact = document.data.get("type_sous_transact").toString()
                    val heureDeChaqueStepList = document.get("date_hour_step") as? MutableList<HashMap<String, String>>

                    if (stepTc != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }

                        itemListTc.add(
                            Sea(
                                "${allFun.removeSpaces(numBooking)}",
                                "",
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
                        listBookingInUpdateFun.add(numBooking)
                    }
                }
            }
            callback()
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun getListBooking(): ArrayList<String> {
        var listOfficielBooking = arrayListOf<String>()
        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val idtc_ok = document.getLong("step_TC")?.toInt()
                    val num_booking_tc = document.data.get("num_Booking").toString()
                    val step_tc_ok = document.getLong("step_TC")?.toInt()

                    val heureDeChaqueStepList =
                        document.get("lesStepDateHour") as? List<HashMap<String, String>>
                    if (idtc_ok != null && step_tc_ok != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }
                        listOfficielBooking.add(num_booking_tc)
                    }
                }
            }
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        return listOfficielBooking
    }

    fun getListBookingWithTitle(): ArrayList<String> {
        var listOfficielBooking = arrayListOf<String>()

        listOfficielBooking.add("Tous les bookings")
        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val idtc_ok = document.getLong("step_TC")?.toInt()
                    val num_booking_tc = document.data.get("num_Booking").toString()
                    val step_tc_ok = document.getLong("step_TC")?.toInt()

                    val heureDeChaqueStepList =
                        document.get("lesStepDateHour") as? List<HashMap<String, String>>
                    if (idtc_ok != null && step_tc_ok != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }
                        listOfficielBooking.add(num_booking_tc)
                    }
                }
            }
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        return listOfficielBooking
    }

    fun getTcAllList(): MutableList<Sea> {
        var itemListTc = arrayListOf<Sea>()
        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val numTc1 = document.data.get("num_tc_1").toString()
                    val numBooking = document.data.get("num_booking").toString()
                    val numCamion = document.data.get("num_camion").toString()
                    val stepTc = document.getLong("step_tc")?.toInt()
                    val dateAjoutTc = document.data.get("date_ajout_tc").toString()
                    val numPlombTc1 = document.data.get("num_plomb_tc_1").toString()
                    val numPhoneChauffeur = document.data.get("num_phone_chauffeur").toString()
                    val numTc2 = document.data.get("num_tc_2").toString()
                    val numPlombTc2 = document.data.get("num_plomb_tc_2").toString()
                    val typeTransact = document.data.get("type_transact").toString()
                    val descTc = document.data.get("desc_tc").toString()
                    val typeSousTransact = document.data.get("type_sous_transact").toString()
                    val heureDeChaqueStepList = document.get("date_hour_step") as? MutableList<HashMap<String, String>>

                    if (stepTc != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }

                        itemListTc.add(
                            Sea(
                                "${allFun.removeSpaces(numBooking)}",
                                "",
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
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
        return itemListTc
    }

    fun inputItemInRecyclerView(
        listener: RecyclerViewClickItemInterface,
        chargement: View,
        recyclerViewTc: RecyclerView
    ): MutableList<Sea> {
        var itemsTc = mutableListOf<Sea>()
        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val numTc1 = document.data.get("num_tc_1").toString()
                    val numBooking = document.data.get("num_booking").toString()
                    val numCamion = document.data.get("num_camion").toString()
                    val stepTc = document.getLong("step_tc")?.toInt()
                    val dateAjoutTc = document.data.get("date_ajout_tc").toString()
                    val numPlombTc1 = document.data.get("num_plomb_tc_1").toString()
                    val numPhoneChauffeur = document.data.get("num_phone_chauffeur").toString()
                    val numTc2 = document.data.get("num_tc_2").toString()
                    val numPlombTc2 = document.data.get("num_plomb_tc_2").toString()
                    val typeTransact = document.data.get("type_transact").toString()
                    val descTc = document.data.get("desc_tc").toString()
                    val typeSousTransact = document.data.get("type_sous_transact").toString()
                    val heureDeChaqueStepList = document.get("date_hour_step") as? MutableList<HashMap<String, String>>

                    if (stepTc != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }

                        itemsTc.add(
                            Sea(
                                "${allFun.removeSpaces(numBooking)}",
                                "",
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
                        recyclerViewTc.adapter = TCAdapter(itemsTc, listener)
                        chargement.visibility = View.GONE
                        itemsTc.sortWith(compareBy({ it.stepTc }))
                    }
                }
            }
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
        return itemsTc
    }
*/

}