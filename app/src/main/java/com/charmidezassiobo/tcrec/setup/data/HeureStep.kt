package com.charmidezassiobo.tcrec.setup.data

import java.io.Serializable

data class HeureStep(
    // lesStepDateHour
    var stepDateChiffre : String = "",
    var stepDateLettre : String = "",
    var stepHeure : String = ""
) : Serializable
