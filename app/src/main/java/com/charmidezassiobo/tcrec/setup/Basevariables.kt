package com.charmidezassiobo.tcrec.setup

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Basevariables {
    val db = Firebase.firestore
    val voyRef = db.collection("Voyagetest")
}