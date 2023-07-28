package com.charmidezassiobo.tcrec.setup.dataclass

data class Sea(
    var numBooking: String = "",
    var numBl: String? = "",
    var numTc1: String = "",
    var numTc2: String = "",
    var numPlomb1: String = "",
    var numPlomb2: String = "",
    var numCamion: String = "",
    var numChauffeur: String = "",
    var descTc: String = "",
    var dateAjoutSea: String = "",
    var typeTransact: String = "",
    var typeSousTransact: String = "",
    var stepTc: Int = 0,
    var dateHourStep: MutableList<HeureStep> = mutableListOf()
)