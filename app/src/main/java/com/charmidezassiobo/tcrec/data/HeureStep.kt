package com.charmidezassiobo.tcrec.data

import java.io.Serializable
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

data class HeureStep(
    // lesStepDateHour
    var stepDateChiffre : String,
    var stepDateLettre : String,
    var stepHeure : String
) : Serializable
