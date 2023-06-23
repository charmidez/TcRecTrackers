package com.charmidezassiobo.tcrec.data

data class Tc(
    var num_TC : String,
    var num_TCSecond : String,
    var num_Camion : String,
    var num_tel_chauffeur : String,
    var num_booking : String,
    var num_plomb : String,
    var date_tc : String,
    var step_TC : Int,
    var num_plomb_second : String,
    var type_transat : String,
    var lesStepDateHour :  List<HeureStep>
    )
