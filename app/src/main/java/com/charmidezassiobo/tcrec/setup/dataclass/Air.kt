package com.charmidezassiobo.tcrec.setup.dataclass

data class Air(
    var numMawb : String,
    var numHawb : String,
    var dateAjoutTc : String,
    var typeTransact : String,
    var typeSousTransact : String,
    var stepTc : Int,
    var dateHourStep :  MutableList<HeureStep>
)
