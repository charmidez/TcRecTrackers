package com.charmidezassiobo.tcrec.data

data class SeaExportDataClass(
    var numTc1 : String,
    var numTc2 : String,
    var numCamion : String,
    var numChauffeur : String,
    var numBooking : String,
    var numPlomb1 : String,
    var dateAjoutTc : String,
    var stepTc : Int,
    var numPlomb2 : String,
    var typeTransact : String,
    var descTc : String,
    var dateHourStep :  MutableList<HeureStep>
    )

data class SeaImport(
    var mawb : String
)
