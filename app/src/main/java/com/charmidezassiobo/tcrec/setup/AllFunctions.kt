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
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.SeaExportDataClass
import com.charmidezassiobo.tcrec.interfaces.RecyclerViewClickItemInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
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
    var stepsBeanList: MutableList<StepBean> = ArrayList()
    lateinit var stepBean0_export: StepBean
    lateinit var stepBean1_export: StepBean
    lateinit var stepBean2_export: StepBean
    lateinit var stepBean3_export: StepBean
    lateinit var stepBean4_export: StepBean
    lateinit var stepBean5_export: StepBean

    //****//
    lateinit var stepBean0_import: StepBean
    lateinit var stepBean1_import: StepBean
    lateinit var stepBean2_import: StepBean
    lateinit var stepBean3_import: StepBean


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

    fun setupStepView(
        mContext: Context,
        typetransact: String,
        bayoStepView: HorizontalStepView
        ) {

        var txtSizeStep = 9

        when (typetransact) {
            "Import" -> {
                stepsBeanList.add(stepBean0_import)
                stepsBeanList.add(stepBean1_import)
                stepsBeanList.add(stepBean2_import)
                stepsBeanList.add(stepBean3_import)

                bayoStepView
                    .setStepViewTexts(stepsBeanList)
                    .setTextSize(txtSizeStep)
                    .setStepsViewIndicatorAttentionIcon(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.ic_cam
                        )
                    )
                    .setStepsViewIndicatorDefaultIcon(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.next_step
                        )
                    )
                    .setStepsViewIndicatorUnCompletedLineColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepViewUnComplectedTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepViewComplectedTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompletedLineColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompleteIcon(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.check
                        )
                    )

            }

            "Export" -> {
                stepsBeanList.add(stepBean0_export)
                stepsBeanList.add(stepBean1_export)
                stepsBeanList.add(stepBean2_export)
                stepsBeanList.add(stepBean3_export)
                stepsBeanList.add(stepBean4_export)
                stepsBeanList.add(stepBean5_export)

                bayoStepView
                    .setStepViewTexts(stepsBeanList)
                    .setTextSize(txtSizeStep)

                    .setStepsViewIndicatorAttentionIcon(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.ic_cam
                        )
                    )
                    .setStepsViewIndicatorDefaultIcon(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.next_step
                        )
                    )
                    .setStepsViewIndicatorUnCompletedLineColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepViewUnComplectedTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepViewComplectedTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompletedLineColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.blue
                        )
                    )
                    .setStepsViewIndicatorCompleteIcon(
                        ContextCompat.getDrawable(
                            mContext,
                            R.drawable.check
                        )
                    )

            }
        }

    }

    fun stepChange(
        etape: Int,
        typetransact: String,
        bayoStepView: HorizontalStepView,
        mContext: Context
    ) {
        when (typetransact) {
            "Import" -> {
                when (etape) {
                    0 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 0)
                        stepBean1_import = StepBean("Dédouanement", -1)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    1 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 0)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    2 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 0)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    3 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 0)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    4 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }
                }
            }

            "Export" -> {
                when (etape) {
                    0 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 0)
                        stepBean1_export = StepBean("Usine", -1)
                        stepBean2_export = StepBean("Chargement", -1)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    1 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 0)
                        stepBean2_export = StepBean("Chargement", -1)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(mContext, typetransact, bayoStepView )

                    }

                    2 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 0)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(mContext, typetransact, bayoStepView )

                    }

                    3 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 0)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(mContext, typetransact, bayoStepView )

                    }

                    4 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 0)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(mContext, typetransact, bayoStepView )

                    }

                    5 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 1)
                        stepBean5_export = StepBean("Arrivée Port", 0)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }

                    6 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 1)
                        stepBean5_export = StepBean("Arrivée Port", 1)
                        setupStepView(mContext, typetransact, bayoStepView )
                    }
                }
            }
        }
    }

    fun filterResult(query: String?, itemList: MutableList<SeaExportDataClass>): MutableList<SeaExportDataClass> {
        val filteredList = ArrayList<SeaExportDataClass>()
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

    fun filterListWithRecycler(
        listener: RecyclerViewClickItemInterface,
        query: String,
        items_tc: MutableList<SeaExportDataClass>,
        recyclerView_TC: RecyclerView
    ) {

        if (query != null) {
            val filteredList = ArrayList<SeaExportDataClass>()
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

        //val typeFace = Typeface.createFromAsset(context.assets, "font/source_sans_pro_semi_bold.ttf")

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


    fun toggleItemSelection(
        selectedItems: HashSet<SeaExportDataClass>,
        item: SeaExportDataClass
    ) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }
        //recyclerView.adapter!!.notifyDataSetChanged()
    }


}