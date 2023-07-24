package com.charmidezassiobo.tcrec.setup.dataclass

data class Road(
    var numCamion : String,
    var numChauffeur : String,
    var descMarchandise : String,
    var depart : String,
    var arrivee : String,
    var dateAjoutTc : String,
    var typeTransact : String,
    var typeSousTransact : String,
    var stepTc : Int,
    var dateHourStep :  MutableList<HeureStep>
)
