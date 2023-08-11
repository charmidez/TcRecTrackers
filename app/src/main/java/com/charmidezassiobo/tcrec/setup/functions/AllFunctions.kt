package com.charmidezassiobo.tcrec.setup.functions

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.data.Sea
import com.charmidezassiobo.tcrec.setup.interfaces.AllFunctionsInterface
import com.charmidezassiobo.tcrec.setup.Adapter.SEAadapter
import com.charmidezassiobo.tcrec.setup.interfaces.RecyclerViewClickItemInterface
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

class AllFunctions : AllFunctionsInterface {

    var stepvoyage: Int = 0
    var typetransact: String = ""

    override fun removeSpaces(str: String): String {
        return str.replace(" ", "")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun miseEnPlaceDate(boolean: Boolean): String {

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
    override fun miseEnPlaceHeure(): String {
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

    override fun filterResultSea(query: String?, itemList: MutableList<Sea>): MutableList<Sea> {
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

    override fun removeRedundance(oldList: ArrayList<String>): ArrayList<String> {
        val set: Set<String> = oldList.toHashSet()
        oldList.clear()
        oldList.addAll(set)
        return oldList
    }

    override fun clearRadioBtnInAjoutTC(v: RadioGroup) {
        if (v.checkedRadioButtonId != -1) {
            val rdBtn: RadioButton? = v.findViewById(v.checkedRadioButtonId)
            rdBtn?.isChecked = false
        }
    }

    override fun resetFragmentInAjoutTC(
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

    override fun resetAllLnInAjoutTC(
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

    override fun filterListWithRecyclerSea(mContext : Context, listener: RecyclerViewClickItemInterface, query: String, items_tc: MutableList<Sea>, recyclerView_TC: RecyclerView) {

        if (query != null) {
            val filteredList = ArrayList<Sea>()
            var query = query.uppercase()
            for (i in items_tc) {
                when(true){
                    i.numTc1.contains(query) || i.numTc2.lowercase(Locale.ROOT).contains(query) -> {
                        filteredList.add(i)
                    }
                    i.numCamion.contains(query) -> {
                        filteredList.add(i)
                    }
                    i.numBooking.contains(query) -> {
                        filteredList.add(i)
                    }
                    else -> {
                        Log.d("Conteneur ","Tc ou Booking non trouvé")
                    }
                }
            }
            if (filteredList.isEmpty()) {
                recyclerView_TC.adapter = SEAadapter(mContext, filteredList, listener)
            } else {
                recyclerView_TC.adapter = SEAadapter(mContext, filteredList, listener)
            }
        }

    }


    override fun showCustomAlertDialog(context: Context, title: String, message: String) {
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

    override fun createPdf(
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


    override fun toggleItemSelectionSea(
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


    override fun testKeyInDbBeforeGetData(
        collectionName : String,
        keyLookingFor : String) : MutableList<String>{
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

    override fun snackBarShowWarning(mContext : Context, rootView : View, message : String){
        val snack = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        snack.setTextColor(ContextCompat.getColor(mContext, R.color.white))
        snack.setBackgroundTint(ContextCompat.getColor(mContext, R.color.gray2))
        snack.show()
    }

    override fun snackBarShowSucces(mContext : Context, rootView : View, message : String){
        val snack = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        snack.setTextColor(ContextCompat.getColor(mContext, R.color.white))
        snack.setBackgroundTint(ContextCompat.getColor(mContext, R.color.blue))
        snack.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateChangement(dateParameter: String, date_etape_tc_popup: TextView) {
        val currentDate = LocalDate.now()
        var dateCurrent = "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"
        var dateCurrentHier =
            "${currentDate.dayOfMonth - 1}/${currentDate.monthValue}/${currentDate.year}"
        var dateCurrentAvantHier =
            "${currentDate.dayOfMonth - 2}/${currentDate.monthValue}/${currentDate.year}"
        var date = dateParameter

        when (date) {
            dateCurrent -> {
                date_etape_tc_popup.text = "Aujourd'hui"
            }

            dateCurrentHier -> {
                date_etape_tc_popup.text = "Hier"
            }

            dateCurrentAvantHier -> {
                date_etape_tc_popup.text = "Avant Hier"
            }
        }
    }



    fun showConfirmationDialog(frag : Fragment, mContext : Context, titleOfDialog : String, messageOfDialog : String, functionOui : Unit ){

        val builder = AlertDialog.Builder(frag.requireContext())

        val dialogView = LayoutInflater.from(frag.context).inflate(R.layout.custom_alert_dialog, null)
        builder.setView(dialogView)
        val titleDialog = dialogView.findViewById<TextView>(R.id.txtView_titleAlertDialog)
        val messageDialog = dialogView.findViewById<TextView>(R.id.txtView_messageAlertDialog)
        val btnOui = dialogView.findViewById<Button>(R.id.buttonOui)
        val btnNon = dialogView.findViewById<Button>(R.id.buttonNon)
        titleDialog.text = "$titleOfDialog"
        messageDialog.text = "$messageOfDialog"
        val alertDialog  = builder.create()
        btnOui.setOnClickListener {
            functionOui
        }
        btnNon.setOnClickListener {
            dialogView.visibility = View.GONE
            alertDialog.dismiss()
        }
        alertDialog.show()
    }


}