package com.charmidezassiobo.tcrec.setup

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.dataclass.Sea
import com.charmidezassiobo.tcrec.interfaces.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.setup.Adapter.TCAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

class AllFunctions {

    var stepvoyage: Int = 0
    var typetransact: String = ""

    fun removeSpaces(str: String): String {
        return str.replace(" ", "")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun miseEnPlaceDate(boolean: Boolean): String {

        var localRealDate = LocalDate.now()

        var cRealDay = localRealDate.dayOfWeek.toString()
        var cRealDayChiffre = localRealDate.dayOfMonth.toString()

        var cRealMonth = localRealDate.month.toString()
        var cRealMonthChiffre = localRealDate.monthValue

        var cRealYear = localRealDate.year.toString()

        when (cRealDay) {
            "MONDAY" -> {
                cRealDay = "Lundi"
            }

            "TUESDAY" -> {
                cRealDay = "Mardi"
            }

            "WEDNESDAY" -> {
                cRealDay = "Mercredi"
            }

            "THURSDAY" -> {
                cRealDay = "Jeudi"
            }

            "FRIDAY" -> {
                cRealDay = "Vendredi"
            }

            "SATURDAY" -> {
                cRealDay = "Samedi"
            }

            "SUNDAY" -> {
                cRealDay = "Dimanche"
            }
        }

        when (cRealMonth) {
            "JANUARY" -> {
                cRealMonth = "Janvier"
            }

            "FEBRUARY" -> {
                cRealMonth = "Février"
            }

            "MARCH" -> {
                cRealMonth = "Mars"
            }

            "APRIL" -> {
                cRealMonth = "Avril"
            }

            "MAY" -> {
                cRealMonth = "Mai"
            }

            "JUNE" -> {
                cRealMonth = "Juin"
            }

            "JULY" -> {
                cRealMonth = "Juillet"
            }

            "AUGUST" -> {
                cRealMonth = "Août"
            }

            "SEPTEMBER" -> {
                cRealMonth = "Sptembre"
            }

            "OCTOBER" -> {
                cRealMonth = "Octobre"
            }

            "NOVEMBER" -> {
                cRealMonth = "Novembre"
            }

            "DECEMBER" -> {
                cRealMonth = "Décembre"
            }
        }

        //Date reel chiffre
        var chiffreRealDate = "$cRealDayChiffre/$cRealMonthChiffre/$cRealYear"

        //Date reel lettres
        var lettreRealDate = "$cRealDay , $cRealDayChiffre $cRealMonth $cRealYear"

        //var dateReal = "${localRealDate.dayOfWeek} , ${localRealDate.dayOfMonth}/${localRealDate.month}/${localRealDate.year}"

        if (boolean == true) {
            return chiffreRealDate
        } else return lettreRealDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun miseEnPlaceHeure(): String {
        var hh = LocalTime.now().hour
        var mm = LocalTime.now().minute

        when (hh) {
            0 -> "00"
            1 -> "01"
            2 -> "02"
            3 -> "03"
            4 -> "04"
            5 -> "05"
            6 -> "06"
            7 -> "07"
            8 -> "08"
            9 -> "09"
        }

        when (mm) {
            0 -> "00"
            1 -> "01"
            2 -> "02"
            3 -> "03"
            4 -> "04"
            5 -> "05"
            6 -> "06"
            7 -> "07"
            8 -> "08"
            9 -> "09"
        }

        var localRealHeure = "$hh:$mm"
        return localRealHeure
    }

    fun filterResultSea(query: String?, itemList: MutableList<Sea>): MutableList<Sea> {
        val filteredList = ArrayList<Sea>()
        if (query != null) {
            val queryUpperCase = query.uppercase(Locale.ROOT)
            for (item in itemList) {
                if (removeSpaces(item.numBooking) == queryUpperCase) {
                    filteredList.add(item)
                }
                if (removeSpaces(item.numTc1) == queryUpperCase) {
                    filteredList.add(item)
                }
                if (removeSpaces(item.numTc2) == queryUpperCase) {
                    filteredList.add(item)
                }
            }
        }
        return filteredList
    }

    fun removeRedundance(oldList: ArrayList<String>): ArrayList<String> {
        val set: Set<String> = oldList.toHashSet()
        oldList.clear()
        oldList.addAll(set)
        return oldList
    }

    fun clearRadioBtnInAjoutTC(v: RadioGroup) {
        if (v.checkedRadioButtonId != -1) {
            val rdBtn: RadioButton? = v.findViewById(v.checkedRadioButtonId)
            rdBtn?.isChecked = false
        }
    }

    fun resetFragmentInAjoutTC(
        radioBtn1: RadioGroup,
        radioBtn2: RadioGroup,
        radioBtn3: RadioGroup,
        ln1EditTextView: View,
        ln2EditTextView: View,
        ln3EditTextView: View,
        ln4EditTextView: View,
        ln5EditTextView: View
    ) {
        radioBtn1.visibility = View.VISIBLE
        radioBtn2.visibility = View.GONE
        radioBtn3.visibility = View.GONE
        ln1EditTextView.visibility = View.GONE
        ln2EditTextView.visibility = View.GONE
        ln3EditTextView.visibility = View.GONE
        ln4EditTextView.visibility = View.GONE
        ln5EditTextView.visibility = View.GONE
    }

    fun resetAllLnInAjoutTC(
        ln1EditTextView: View,
        ln2EditTextView: View,
        ln3EditTextView: View,
        ln4EditTextView: View,
        ln5EditTextView: View
    ) {
        ln1EditTextView.visibility = View.GONE
        ln2EditTextView.visibility = View.GONE
        ln3EditTextView.visibility = View.GONE
        ln4EditTextView.visibility = View.GONE
        ln5EditTextView.visibility = View.GONE
    }

    fun filterListWithRecyclerSea(
        listener: RecyclerViewClickItemInterface,
        query: String,
        items_tc: MutableList<Sea>,
        recyclerView_TC: RecyclerView
    ) {

        if (query != null) {
            val filteredList = ArrayList<Sea>()
            for (i in items_tc) {

                if (i.numTc1.lowercase(Locale.ROOT)
                        .contains(query) || i.numTc1.uppercase(Locale.ROOT).contains(query)
                ) {
                    filteredList.add(i)
                }
                if (i.numTc2.lowercase(Locale.ROOT)
                        .contains(query) || i.numTc2.uppercase(Locale.ROOT).contains(query)
                ) {
                    filteredList.add(i)
                }
                if (i.numCamion.lowercase(Locale.ROOT).contains(query) || i.numCamion.uppercase(
                        Locale.ROOT
                    ).contains(query)
                ) {
                    filteredList.add(i)
                }
                if (i.numBooking.lowercase(Locale.ROOT).contains(query) || i.numBooking.uppercase(
                        Locale.ROOT
                    ).contains(query)
                ) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                recyclerView_TC.adapter = TCAdapter(filteredList, listener)

            } else {
                recyclerView_TC.adapter = TCAdapter(filteredList, listener)
            }
        }

    }


    fun showCustomAlertDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)
        builder.setView(dialogView)

        val titleDialog = dialogView.findViewById<TextView>(R.id.txtView_titleAlertDialog)
        val messageDialog = dialogView.findViewById<TextView>(R.id.txtView_messageAlertDialog)

        titleDialog.text = title
        messageDialog.text = message

        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun createPdf(
        context: Context,
        contenuDuPdf: String,
        nomDuPdf: String,
        convertPdfBtn: FloatingActionButton,
        recyclerView: RecyclerView
    ) {
        //Bien réorganiser le Pdf de sorte qu'on puisse obtenir un tableau complet dans lequel se trouve la liste des Tc
        convertPdfBtn.setOnClickListener {
            // Créez un document PDF
            val document = Document()
            val filePath = "/storage/emulated/0/Documents/$nomDuPdf"

            // Initialisez l'objet FileOutputStream pour écrire dans le fichier PDF
            val fileOutputStream = FileOutputStream(filePath)
            PdfWriter.getInstance(document, fileOutputStream)

            // Ouvrez le document PDF et ajoutez du texte dans le document PDF
            document.open()
            val paragraph = Paragraph(contenuDuPdf)
            document.add(paragraph)

            // Fermez le document PDF et le fichier PDF
            document.close()
            fileOutputStream.close()
            //Log.d("item_tc",contenuDuPdf)

            val snack = Snackbar.make(recyclerView, "PDF Créé avec Succès", Snackbar.LENGTH_LONG)
            snack.setTextColor(ContextCompat.getColor(context, R.color.white))
            snack.setBackgroundTint(ContextCompat.getColor(context, R.color.blue))
            snack.show()

        }
    }


    fun toggleItemSelectionSea(
        selectedItems: HashSet<Sea>,
        item: Sea
    ) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }
        //recyclerView.adapter!!.notifyDataSetChanged()
    }


    fun testKeyInDbBeforeGetData(collectionName : String, keyLookingFor : String) : MutableList<String>{
        val db = Firebase.firestore
        val collectionRef = db.collection(collectionName)
        val key = keyLookingFor
        var listValueFromKey = mutableListOf<String>()

        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (documentSnapshot in querySnapshot.documents) {
                    if (documentSnapshot.contains(key)) {
                        // La clé est disponible dans le document actuel
                        val value = documentSnapshot.getString(key).toString()
                        // Faites quelque chose avec la valeur récupérée
                        listValueFromKey.add(value)
                        // Vous pouvez également accéder à d'autres données du document avec les méthodes appropriées
                    } else {
                        // La clé n'existe pas dans le document actuel
                        // Traitez ce cas en conséquence
                        listValueFromKey.add("Aucune donnée disponible")
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Une erreur s'est produite lors de la récupération des documents
                // Traitez cette erreur en conséquence
            }

        return listValueFromKey
    }


}