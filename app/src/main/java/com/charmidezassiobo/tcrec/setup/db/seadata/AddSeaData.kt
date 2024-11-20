package com.charmidezassiobo.tcrec.setup.db.seadata

import com.charmidezassiobo.tcrec.setup.functions.AllFunctions
import com.charmidezassiobo.tcrec.setup.functions.AllVariables
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddSeaData {
    private val allVar = AllVariables()
    private val allFun = AllFunctions()
    val db = Firebase.firestore
    val dbSea = db.collection(allVar.SEA_COLLECTION)
}