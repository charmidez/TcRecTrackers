package com.charmidezassiobo.tcrec.setup.db

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.setup.Adapter.ListUserAdapter
import com.charmidezassiobo.tcrec.setup.data.RecUser
import com.charmidezassiobo.tcrec.setup.functions.AllVariables
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class GetUserData(var mContext: Context?) {
    private val allVar = AllVariables()

    //private val allFun = AllFunctions()
    val db = Firebase.firestore
    val dbUsers = db.collection(allVar.REC_USER)

    fun retrieveUsers(chargement: View, recyclerView: RecyclerView) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot = dbUsers.get().await()
                val itemsListUser = mutableListOf<RecUser>()
                for (recUser in querySnapshot.documents) {
                    if (recUser != null) {
                        val recType = recUser.getBoolean("")!!
                        val recName = recUser.data?.get("").toString()
                        val recPseudo = recUser.data?.get("").toString()
                        val recPassword = recUser.data?.get("").toString()

                        itemsListUser.add(
                            RecUser(recType, recName, recPseudo, recPassword)
                        )
                    }
                }
                withContext(Dispatchers.Main) {
                    when (true) {
                        itemsListUser.isNullOrEmpty() -> {
                            chargement.visibility = View.VISIBLE
                        }

                        else -> {
                            chargement.visibility = View.GONE
                            recyclerView!!.adapter = ListUserAdapter(mContext!!, itemsListUser)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                }
            }
        }

    fun retrieveListUser(): kotlinx.coroutines.Deferred<MutableList<RecUser>> =
        CoroutineScope(Dispatchers.IO).async {
            var itemsListUser = mutableListOf<RecUser>()
            try {
                val querySnapshot = dbUsers.get().await()
                for (recUser in querySnapshot.documents) {
                    if (recUser != null) {
                        val recType = recUser.getBoolean("")!!
                        val recName = recUser.data?.get("").toString()
                        val recPseudo = recUser.data?.get("").toString()
                        val recPassword = recUser.data?.get("").toString()
                        itemsListUser.add(
                            RecUser(recType, recName, recPseudo, recPassword)
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                }
            }


            return@async itemsListUser
        }
}