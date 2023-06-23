package com.charmidezassiobo.tcrec.brouillons

import com.charmidezassiobo.tcrec.data.HeureStep


//Le brouillon du Tc data class
data class DataTestTc(

    var numsTc : ArrayList<String>,

    var stepTC : Int,
    var stepHeureDate :  List<HeureStep>

)
