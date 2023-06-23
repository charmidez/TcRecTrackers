package com.charmidezassiobo.tcrec.setup

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime

class AllFunctions {


    @RequiresApi(Build.VERSION_CODES.O)
    fun miseEnPlaceDate(boolean: Boolean):String{

        //ajouterdate = "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"

        var localRealDate = LocalDate.now()

        var cRealDay = localRealDate.dayOfWeek.toString()
        var cRealDayChiffre = localRealDate.dayOfMonth

        var cRealMonth = localRealDate.month.toString()
        var cRealMonthChiffre = localRealDate.monthValue

        var cRealYear = localRealDate.year.toString()

        when (cRealDay){
            "MONDAY" -> {cRealDay = "Lundi"}
            "TUESDAY" -> {cRealDay = "Mardi"}
            "WEDNESDAY" -> {cRealDay = "Mercredi"}
            "THURSDAY" -> {cRealDay = "Jeudi"}
            "FRIDAY" -> {cRealDay = "Vendredi"}
            "SATURDAY" -> {cRealDay = "Samedi"}
            "SUNDAY" -> {cRealDay = "Dimanche"}
        }

        when (cRealMonth){
            "JANUARY" -> {cRealMonth = "Janvier"}
            "FEBRUARY" -> {cRealMonth = "Février"}
            "MARCH" -> {cRealMonth = "Mars"}
            "APRIL" -> {cRealMonth = "Avril"}
            "MAY" -> {cRealMonth = "Mai"}
            "JUNE" -> {cRealMonth = "Juin"}
            "JULY" -> {cRealMonth = "Juillet"}
            "AUGUST" -> {cRealMonth = "Août"}
            "SEPTEMBER" -> {cRealMonth = "Sptembre"}
            "OCTOBER" -> {cRealMonth = "Octobre"}
            "NOVEMBER" -> {cRealMonth = "Novembre"}
            "DECEMBER" -> {cRealMonth = "Décembre"}
        }


        //Date reel chiffre
        var chiffreRealDate = "$cRealDayChiffre/$cRealMonthChiffre/$cRealYear"

        //Date reel lettres
        var lettreRealDate = "$cRealDay , $cRealDayChiffre $cRealMonth $cRealYear"

        //var dateReal = "${localRealDate.dayOfWeek} , ${localRealDate.dayOfMonth}/${localRealDate.month}/${localRealDate.year}"

        if (boolean == true){
            return chiffreRealDate
        } else return lettreRealDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun miseEnPlaceHeure():String{
        var hh = LocalTime.now().hour.toString()
        var mm = LocalTime.now().minute.toString()
        var localRealHeure = "$hh:$mm"
        return localRealHeure
    }


}