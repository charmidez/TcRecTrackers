package com.charmidezassiobo.tcrec.data

import android.content.ContentValues
import android.util.Log
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.setup.AllVariables
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GetDataFromDB {

    private val dataBasePath = AllVariables().dbPath
    private val db = Firebase.firestore
    private val voyTc = db.collection(dataBasePath)

    fun updateTc(callback: () -> Unit) {
        var itemListTc = arrayListOf<Tc>()
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
                    val date_ok = document.data.get("Date").toString()
                    val plomb_ok = document.data.get("num_plomb_TC").toString()
                    val num_phone_chauffeur_ok = document.data.get("phone_chauffeur_TC").toString()
                    val numtcsecond_ok = document.data.get("num_TC_Second").toString()
                    val numplombsecond_ok = document.data.get("num_plomb_TC_2").toString()
                    val type_transact = document.data.get("import_export").toString()
                    val desc_TC = document.data.get("desc_TC").toString()

                    val heureDeChaqueStepList = document.get("lesStepDateHour") as? List<HashMap<String, String>>

                    if (idtc_ok != null && step_tc_ok != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }

                        itemListTc.add(
                            Tc(
                                "${AllFunctions().removeSpaces(numtc_ok)}",
                                "${AllFunctions().removeSpaces(numtcsecond_ok)}",
                                "${AllFunctions().removeSpaces(num_cam_ok)}",
                                "${AllFunctions().removeSpaces(num_phone_chauffeur_ok)}",
                                "${AllFunctions().removeSpaces(num_booking_tc )}",
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

    fun getListBooking() : ArrayList<String>{
        var listOfficielBooking = arrayListOf<String>()

        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val idtc_ok = document.getLong("step_TC")?.toInt()
                    val num_booking_tc = document.data.get("num_Booking").toString()
                    val step_tc_ok = document.getLong("step_TC")?.toInt()

                    val heureDeChaqueStepList = document.get("lesStepDateHour") as? List<HashMap<String, String>>
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

    fun getListBookingWithTitle() : ArrayList<String>{
        var listOfficielBooking = arrayListOf<String>()

        listOfficielBooking.add("Tous les bookings")
        voyTc.get().addOnSuccessListener { documents ->
            for (document in documents) {
                if (document != null) {
                    val idtc_ok = document.getLong("step_TC")?.toInt()
                    val num_booking_tc = document.data.get("num_Booking").toString()
                    val step_tc_ok = document.getLong("step_TC")?.toInt()

                    val heureDeChaqueStepList = document.get("lesStepDateHour") as? List<HashMap<String, String>>
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

    fun getTcAllList() : ArrayList<Tc> {
        var itemListTc = arrayListOf<Tc>()
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

                    val heureDeChaqueStepList = document.get("lesStepDateHour") as? List<HashMap<String, String>>

                    if (idtc_ok != null && step_tc_ok != null && heureDeChaqueStepList != null) {
                        val heureDeChaqueStep = heureDeChaqueStepList.map {
                            HeureStep(
                                it["stepDateChiffre"] ?: "",
                                it["stepDateLettre"] ?: "",
                                it["stepHeure"] ?: ""
                            )
                        }

                        itemListTc.add(
                            Tc(
                                "${AllFunctions().removeSpaces(numtc_ok)}",
                                "${AllFunctions().removeSpaces(numtcsecond_ok)}",
                                "${AllFunctions().removeSpaces(num_cam_ok)}",
                                "${AllFunctions().removeSpaces(num_phone_chauffeur_ok)}",
                                "${AllFunctions().removeSpaces(num_booking_tc )}",
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

}