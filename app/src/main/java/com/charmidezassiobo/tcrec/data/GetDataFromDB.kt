package com.charmidezassiobo.tcrec.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.setup.AllVariables
import com.charmidezassiobo.tcrec.interfaces.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.setup.TCAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GetDataFromDB {

    private val dataBasePath = AllVariables().dbPath
    private val db = Firebase.firestore
    private val voyTc = db.collection(dataBasePath)

    //Sea Export Tc
    fun updateTc(callback: () -> Unit) {
        var itemListTc = arrayListOf<SeaExportDataClass>()
        var listBookingInUpdateFun = arrayListOf<String>()
        itemListTc.clear()
        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val idtc_ok = document.getLong("step_TC")?.toInt()
                    val numtc_ok = document.data.get("num_TC").toString()
                    val num_booking_tc = document.data.get("num_Booking").toString()
                    val num_cam_ok = document.data.get("num_Camion").toString()
                    val step_tc_ok = document.getLong("step_TC")?.toInt()
                    val date_ok = document.data.get("date_ajout_tc").toString()
                    val plomb_ok = document.data.get("num_plomb_TC").toString()
                    val num_phone_chauffeur_ok = document.data.get("phone_chauffeur_TC").toString()
                    val numtcsecond_ok = document.data.get("num_TC_Second").toString()
                    val numplombsecond_ok = document.data.get("num_plomb_TC_2").toString()
                    val type_transact = document.data.get("import_export").toString()
                    val desc_TC = document.data.get("desc_TC").toString()

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

                        itemListTc.add(
                            SeaExportDataClass(
                                "${AllFunctions().removeSpaces(numtc_ok)}",
                                "${AllFunctions().removeSpaces(numtcsecond_ok)}",
                                "${AllFunctions().removeSpaces(num_cam_ok)}",
                                "${AllFunctions().removeSpaces(num_phone_chauffeur_ok)}",
                                "${AllFunctions().removeSpaces(num_booking_tc)}",
                                "$plomb_ok",
                                "$date_ok",
                                step_tc_ok,
                                "$numplombsecond_ok",
                                "$type_transact",
                                "${AllFunctions().removeSpaces(desc_TC)}",
                                heureDeChaqueStep!!.toMutableList()
                            )
                        )
                        listBookingInUpdateFun.add(num_booking_tc)
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

    fun getTcAllList(): MutableList<SeaExportDataClass> {
        var itemListTc = arrayListOf<SeaExportDataClass>()
        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val idtc_ok = document.getLong("step_TC")?.toInt()
                    val numtc_ok = document.data.get("num_TC").toString()
                    val num_booking_tc = document.data.get("num_Booking").toString()
                    val num_cam_ok = document.data.get("num_Camion").toString()
                    val step_tc_ok = document.getLong("step_TC")?.toInt()
                    val date_ok = document.data.get("Date").toString()
                    val plomb_ok = document.data.get("num_plomb_TC").toString()
                    val num_phone_chauffeur_ok = document.data.get("phone_chauffeur_TC").toString()
                    val numtcsecond_ok = document.data.get("num_TC_Second").toString()
                    val numplombsecond_ok = document.data.get("num_plomb_TC_2").toString()
                    val type_transact = document.data.get("import_export").toString()
                    val desc_TC = document.data.get("desc_TC").toString()

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

                        itemListTc.add(
                            SeaExportDataClass(
                                "${AllFunctions().removeSpaces(numtc_ok)}",
                                "${AllFunctions().removeSpaces(numtcsecond_ok)}",
                                "${AllFunctions().removeSpaces(num_cam_ok)}",
                                "${AllFunctions().removeSpaces(num_phone_chauffeur_ok)}",
                                "${AllFunctions().removeSpaces(num_booking_tc)}",
                                "$plomb_ok",
                                "$date_ok",
                                step_tc_ok,
                                "$numplombsecond_ok",
                                "$type_transact",
                                "${AllFunctions().removeSpaces(desc_TC)}",
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
        recyclerView_TC: RecyclerView
    ): MutableList<SeaExportDataClass> {
        var itemsTc = mutableListOf<SeaExportDataClass>()
        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val idtc_ok = document.getLong("step_tc")?.toInt()
                    val numtc_ok = document.data.get("num_tc_1").toString()
                    val num_booking_tc = document.data.get("num_booking").toString()
                    val num_cam_ok = document.data.get("num_camion").toString()
                    val step_tc_ok = document.getLong("step_tc")?.toInt()
                    val date_ok = document.data.get("date_ajout_tc").toString()
                    val plomb_ok = document.data.get("num_plomb_tc_1").toString()
                    val num_phone_chauffeur_ok = document.data.get("phone_chauffeur_tc").toString()
                    val numtcsecond_ok = document.data.get("num_tc_2").toString()
                    val numplombsecond_ok = document.data.get("num_plomb_tc_2").toString()
                    val type_transact = document.data.get("type_transact").toString()
                    val desc_TC = document.data.get("desc_TC").toString()

                    val heureDeChaqueStepList =
                        document.get("date_hour_step") as? MutableList<HashMap<String, String>>

                    if (idtc_ok != null && step_tc_ok != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }

                        itemsTc.add(
                            SeaExportDataClass(
                                "${AllFunctions().removeSpaces(numtc_ok)}",
                                "${AllFunctions().removeSpaces(numtcsecond_ok)}",
                                "${AllFunctions().removeSpaces(num_cam_ok)}",
                                "${AllFunctions().removeSpaces(num_phone_chauffeur_ok)}",
                                "${AllFunctions().removeSpaces(num_booking_tc)}",
                                "$plomb_ok",
                                "$date_ok",
                                step_tc_ok,
                                "$numplombsecond_ok",
                                "$type_transact",
                                "${AllFunctions().removeSpaces(desc_TC)}",
                                heureDeChaqueStep.toMutableList()
                            )
                        )
                        recyclerView_TC.adapter = TCAdapter(itemsTc, listener)
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

    fun removedItemInRecyclerView(
        context: Context,
        v: RecyclerView.ViewHolder,
        recyclerView_TC: RecyclerView
    ) {
        // Suppression de l'élément de la RecyclerView
        val itemsTc = mutableListOf<SeaExportDataClass>()
        val position = v.adapterPosition
        val tc = itemsTc[position]
        itemsTc.removeAt(position)
        recyclerView_TC.adapter!!.notifyItemRemoved(position)

        // Suppression de l'élément de Firebase Firestore
        val db = FirebaseFirestore.getInstance()
        val query = db.collection(dataBasePath)
            .whereEqualTo("num_TC", tc.numTc1)
            .whereEqualTo("num_Camion", tc.numCamion)
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val docRef = db.collection(dataBasePath).document(document.id)
                docRef.delete().addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                    // Affichage du message de confirmation
                    val snack = Snackbar.make(recyclerView_TC, "TC Supprimé", Snackbar.LENGTH_LONG)
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

}