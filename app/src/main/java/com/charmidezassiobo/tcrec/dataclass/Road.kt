package com.charmidezassiobo.tcrec.dataclass

data class Road(
    var numCamion : String,
    var numChauffeur : String,
    var descMarchandise : String,
    var dateAjoutTc : String,
    var typeTransact : String,
    var typeSousTransact : String,
    var stepTc : Int,
    var dateHourStep :  MutableList<HeureStep>
)
