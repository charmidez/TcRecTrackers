package com.charmidezassiobo.tcrec.setup.db

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.setup.data.Air
import com.charmidezassiobo.tcrec.setup.functions.AllVariables
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GetAirData(var air : Air, var recyclerView : RecyclerView){

    private val allVar = AllVariables()
    val db = Firebase.firestore
    private val dbAir = db.collection(allVar.AIR_COLLECTION)

    fun saveAir() = CoroutineScope(Dispatchers.IO).launch {
        try {
            dbAir.add(air).await()
            withContext(Dispatchers.Main){
                Log.d("INPUT_AIR", "isOk")
            }
        } catch (e : Exception) {
            withContext(Dispatchers.Main){
                Log.d("INPUT_AIR_ERROR", e.message.toString())
            }
        }
    }

    fun retrieveAir() = CoroutineScope(Dispatchers.IO).launch  {
        try {
            val querySnapshot = dbAir.get().await()
            val sb = StringBuilder()
            for (document in querySnapshot.documents){
                val air = document.toObject<Air>()
                sb.append("$air\n")
            }
            withContext(Dispatchers.Main){
                Log.d("AIR_COLLECTION", sb.toString())
            }
        } catch (e : Exception ) {
            withContext(Dispatchers.Main){
                Log.d("AIR_ERROR", e.toString())
            }
        }
    }

    fun realTimeRetrieveAir(){
        dbAir.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Log.d("EXCEPTION_AIR", it.message.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let{
                val sb = StringBuilder()
                for (document in it){
                    val air = document.toObject<Air>()
                    sb.append("$air\n")
                }
                Log.d("AIR_COLLECTION", sb.toString())
            }
        }
    }

}