package com.charmidezassiobo.tcrec.brouillons

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Basevariables {
    val db = Firebase.firestore
    val voyRef = db.collection("Voyagetest")

    val documentOfficiel = db.collection("Voyagetest")
}