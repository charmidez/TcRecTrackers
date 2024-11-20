package com.charmidezassiobo.tcrec.setup.functions

import android.view.View
import com.charmidezassiobo.tcrec.R
import com.itextpdf.text.factories.GreekAlphabetFactory.getString

class AllVariables() {
    open var DBPATH = "(default)"
    open var SEA_COLLECTION = "SEA_COLLECTION"
    open var AIR_COLLECTION = "AIR_COLLECTION"
    open var ROAD_COLLECTION = "ROAD_COLLECTION"
    open var REC_USER = "REC_USER_COLLECTION"

    //SEA Export
    open var SEA_EXPORT_ETAPE_0 = "Port"
    open var SEA_EXPORT_ETAPE_1 = "Usine"
    open var SEA_EXPORT_ETAPE_2 = "Chargement"
    open var SEA_EXPORT_ETAPE_3 = "Douane"
    open var SEA_EXPORT_ETAPE_4 = "Sortie"
    open var SEA_EXPORT_ETAPE_5 = "Arrivée"

    //open var SEA_EXPORT_ETAPE_RESULTAT_0 = getString(R.string.result_etape_1_export_sea)!!

    //SEA Import
    open var SEA_IMPORT_ETAPE_0 = "Arrivée Port"
    open var SEA_IMPORT_ETAPE_1 = "Dédouanement"
    open var SEA_IMPORT_ETAPE_2 = "Sortie"
    open var SEA_IMPORT_ETAPE_3 = "Destination Finale"

    //AIR Import
    open var AIR_IMPORT_ETAPE_0 = ""
    open var AIR_IMPORT_ETAPE_1 = ""
    open var AIR_IMPORT_ETAPE_2 = ""
    open var AIR_IMPORT_ETAPE_3 = ""
    open var AIR_IMPORT_ETAPE_4 = ""

    //AIR Export
    open var AIR_EXPORT_ETAPE_0 = ""
    open var AIR_EXPORT_ETAPE_1 = ""
    open var AIR_EXPORT_ETAPE_2 = ""
    open var AIR_EXPORT_ETAPE_3 = ""
    open var AIR_EXPORT_ETAPE_4 = ""
    open var AIR_EXPORT_ETAPE_5 = ""

    //ROAD tracking
    open var ROAD_TRACKING_ETAPE_0 = "Chargement"
    open var ROAD_TRACKING_ETAPE_1 = "Départ"
    open var ROAD_TRACKING_ETAPE_2 = "En Route"
    open var ROAD_TRACKING_ETAPE_3 = "Arrivée"

    open var CALL_REC_NUMBER = 92512666

    /*
    fun getRessource(xml_int : Int){
        var twt = ""
    }

     */

}