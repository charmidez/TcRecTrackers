package com.charmidezassiobo.tcrec.dataclass

data class Sea (
    var numBooking : String,
    var numBl: String,
    var numTc1 : String,
    var numTc2 : String,
    var numPlomb1 : String,
    var numPlomb2 : String,
    var numCamion : String,
    var numChauffeur : String,
    var descTc : String,
    var dateAjoutTc : String,
    var typeTransact : String,
    var typeSousTransact : String,
    var stepTc : Int,
    var dateHourStep :  MutableList<HeureStep>
)