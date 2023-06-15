package com.charmidezassiobo.tcrec.data

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GetDataFromDB {

        val db = Firebase.firestore
        val voyTc = db.collection("Voyagetest")
        var itemListTc = arrayListOf<Tc>()


        var listBooking = arrayListOf<String>()
        var distincList = arrayListOf<String>()

    fun updateTc(callback: () -> Unit){
        voyTc.get().addOnSuccessListener {documents ->
            itemListTc.clear()
            for (document in documents) {
                if (documents != null){
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
                    val type_transact = document.data.get("import_export")
                    if ( idtc_ok != null){
                        if (step_tc_ok != null ){
                            itemListTc.add(Tc( "$numtc_ok",
                                "$numtcsecond_ok",
                                "$num_cam_ok",
                                "$num_phone_chauffeur_ok",
                                " $num_booking_tc",
                                "$plomb_ok",
                                "$date_ok",
                                step_tc_ok,
                                "$numplombsecond_ok",
                                "$type_transact"))
                            listBooking.add(num_booking_tc)
                        }
                    }
                }
            }
            callback()
        }
            .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents.", exception)
        }
    }

}