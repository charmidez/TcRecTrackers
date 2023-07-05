package com.charmidezassiobo.tcrec.setup

import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.Tc
import com.google.android.material.snackbar.Snackbar
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

    var items_tc : MutableList<Tc> = ArrayList()


    @RequiresApi(Build.VERSION_CODES.O)
    fun miseEnPlaceDate(boolean: Boolean):String{

        //ajouterdate = "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"

        var localRealDate = LocalDate.now()

        var cRealDay = localRealDate.dayOfWeek.toString()
        var cRealDayChiffre = localRealDate.dayOfMonth.toString()

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

        when(hh){
            "0" -> "00"
            "1" -> "01"
            "2" -> "02"
            "3" -> "03"
            "4" -> "04"
            "5" -> "05"
            "6" -> "06"
            "7" -> "07"
            "8" -> "08"
            "9" -> "09"
        }

        when(mm){
            "0" -> "00"
            "1" -> "01"
            "2" -> "02"
            "3" -> "03"
            "4" -> "04"
            "5" -> "05"
            "6" -> "06"
            "7" -> "07"
            "8" -> "08"
            "9" -> "09"
        }

        var localRealHeure = "$hh:$mm"
        return localRealHeure
    }

    fun setupStepView(typetransact: String, bayoStepView: HorizontalStepView, mContext : Context) {

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

    fun stepChange(etape: Int, typetransact: String, bayoStepView: HorizontalStepView, mContext: Context) {
        when (typetransact) {
            "Import" -> {
                when (etape) {
                    0 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 0)
                        stepBean1_import = StepBean("Dédouanement", -1)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(typetransact, bayoStepView, mContext)
                    }

                    1 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 0)
                        stepBean2_import = StepBean("Sortie", -1)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(typetransact, bayoStepView, mContext)
                    }

                    2 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 0)
                        stepBean3_import = StepBean("Destination Finale", -1)
                        setupStepView(typetransact, bayoStepView, mContext)
                    }

                    3 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 0)
                        setupStepView(typetransact, bayoStepView, mContext)
                    }

                    4 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_import = StepBean("Arrivée Port", 1)
                        stepBean1_import = StepBean("Dédouanement", 1)
                        stepBean2_import = StepBean("Sortie", 1)
                        stepBean3_import = StepBean("Destination Finale", 1)
                        setupStepView(typetransact, bayoStepView, mContext)
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
                        setupStepView(typetransact, bayoStepView, mContext)
                    }

                    1 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 0)
                        stepBean2_export = StepBean("Chargement", -1)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView, mContext)

                    }

                    2 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 0)
                        stepBean3_export = StepBean("Douane", -1)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView, mContext)

                    }

                    3 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 0)
                        stepBean4_export = StepBean("Sortie", -1)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView, mContext)

                    }

                    4 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 0)
                        stepBean5_export = StepBean("Arrivée Port", -1)
                        setupStepView(typetransact, bayoStepView, mContext)

                    }

                    5 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 1)
                        stepBean5_export = StepBean("Arrivée Port", 0)
                        setupStepView(typetransact, bayoStepView, mContext)
                    }

                    6 -> {
                        stepsBeanList = ArrayList()
                        stepBean0_export = StepBean("Port", 1)
                        stepBean1_export = StepBean("Usine", 1)
                        stepBean2_export = StepBean("Chargement", 1)
                        stepBean3_export = StepBean("Douane", 1)
                        stepBean4_export = StepBean("Sortie", 1)
                        stepBean5_export = StepBean("Arrivée Port", 1)
                        setupStepView(typetransact, bayoStepView, mContext)
                    }
                }
            }
        }
    }

    fun filterResult(query: String?, itemList : MutableList<Tc> ) : Boolean{
        val filteredList = ArrayList<Tc>()
/*        if (query != null){
            for (i in itemList){
                if(i.num_booking.lowercase(Locale.ROOT).contains(query) || i.num_booking.uppercase(Locale.ROOT).contains(query) ){
                    filteredList.add(i)
                }
                if(i.num_TC.lowercase(Locale.ROOT).contains(query) || i.num_TC.uppercase(Locale.ROOT).contains(query) ){
                    filteredList.add(i)
                }
                if(i.num_TCSecond.lowercase(Locale.ROOT).contains(query) || i.num_TCSecond.uppercase(Locale.ROOT).contains(query) ){
                    filteredList.add(i)
                }
            }
        }*/

        if (query != null){
            for (i in itemList){
                if(i.num_booking.lowercase() == query || i.num_booking.uppercase() == query ){
                    filteredList.add(i)
                }
                if(i.num_TC.lowercase() == query || i.num_TC.uppercase() == query ){
                    filteredList.add(i)
                }
                if(i.num_TCSecond.lowercase() == query || i.num_TCSecond.uppercase() == query ){
                    filteredList.add(i)
                }
            }
        }

        if (filteredList.isNotEmpty()){
            return true
        } else  {
            return false
        }
    }

    fun filterListClientResult(context: Context, query : String?, recyclerView_TC : RecyclerView){
        var mView : View = View(context)

        if (query  != null ){
            val filteredList = ArrayList<Tc>()
            for (i in items_tc){
                if(i.num_booking.lowercase(Locale.ROOT).contains(query) || i.num_booking.uppercase(
                        Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
                if(i.num_TC.lowercase(Locale.ROOT).contains(query) || i.num_TC.uppercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
                if(i.num_TCSecond.lowercase(Locale.ROOT).contains(query) || i.num_TCSecond.uppercase(
                        Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                //recyclerView_TC.adapter = TCResultAdapter(this@ClientActivity, items_tc)
                recyclerView_TC.visibility = View.GONE
                val snack = Snackbar.make(mView,"Conteneur ou Booking introuvable", Snackbar.LENGTH_LONG)
                snack.setTextColor(ContextCompat.getColor(context, R.color.white))
                snack.setBackgroundTint(ContextCompat.getColor(context, R.color.gray2))
                snack.show()
            } else {
                recyclerView_TC.adapter = TCResultAdapter( context, filteredList)
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                //recyclerView_TC.visibility = View.VISIBLE
            }
        }
    }


    /*
    fun showDate(step : Int, holder : TCResultViewHolder, tc : Tc){
        if(step != null){
            var list = tc.lesStepDateHour
            when(step){
                0 -> {
                    holder.txtViewDateHeure0.text = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    holder.lnEtape0.visibility = View.VISIBLE
                }
                1 -> {
                    holder.txtViewDateHeure0.text  = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    holder.txtViewDateHeure1.text  = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"

                    holder.lnEtape0.visibility = View.VISIBLE
                    holder.lnEtape1.visibility = View.VISIBLE
                }
                2 -> {
                    holder.txtViewDateHeure0.text  = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    holder.txtViewDateHeure1.text  = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    holder.txtViewDateHeure2.text  = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"


                    holder.lnEtape0.visibility = View.VISIBLE
                    holder.lnEtape1.visibility = View.VISIBLE
                    holder.lnEtape2.visibility = View.VISIBLE
                }
                3 -> {
                    holder.txtViewDateHeure0.text  = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    holder.txtViewDateHeure1.text  = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    holder.txtViewDateHeure2.text  = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"
                    holder.txtViewDateHeure3.text  = "${list!![3].stepDateLettre} à ${list!![3].stepHeure}"


                    holder.lnEtape0.visibility = View.VISIBLE
                    holder.lnEtape1.visibility = View.VISIBLE
                    holder.lnEtape2.visibility = View.VISIBLE
                    holder.lnEtape3.visibility = View.VISIBLE
                }
                4 -> {
                    holder.txtViewDateHeure0.text  = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    holder.txtViewDateHeure1.text  = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    holder.txtViewDateHeure2.text  = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"
                    holder.txtViewDateHeure3.text  = "${list!![3].stepDateLettre} à ${list!![3].stepHeure}"
                    holder.txtViewDateHeure4.text  = "${list!![4].stepDateLettre} à ${list!![4].stepHeure}"


                    holder.lnEtape0.visibility = View.VISIBLE
                    holder.lnEtape1.visibility = View.VISIBLE
                    holder.lnEtape2.visibility = View.VISIBLE
                    holder.lnEtape3.visibility = View.VISIBLE
                    holder.lnEtape4.visibility = View.VISIBLE
                }
                5 -> {
                    holder.txtViewDateHeure0.text  = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    holder.txtViewDateHeure1.text  = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    holder.txtViewDateHeure2.text  = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"
                    holder.txtViewDateHeure3.text  = "${list!![3].stepDateLettre} à ${list!![3].stepHeure}"
                    holder.txtViewDateHeure4.text  = "${list!![4].stepDateLettre} à ${list!![4].stepHeure}"
                    holder.txtViewDateHeure5.text  = "${list!![5].stepDateLettre} à ${list!![5].stepHeure}"

                    holder.lnEtape0.visibility = View.VISIBLE
                    holder.lnEtape1.visibility = View.VISIBLE
                    holder.lnEtape2.visibility = View.VISIBLE
                    holder.lnEtape3.visibility = View.VISIBLE
                    holder.lnEtape4.visibility = View.VISIBLE
                    holder.lnEtape5.visibility = View.VISIBLE
                }
                6 -> {
                    holder.txtViewDateHeure0.text  = "${list!![0].stepDateLettre} à ${list!![0].stepHeure}"
                    holder.txtViewDateHeure1.text  = "${list!![1].stepDateLettre} à ${list!![1].stepHeure}"
                    holder.txtViewDateHeure2.text  = "${list!![2].stepDateLettre} à ${list!![2].stepHeure}"
                    holder.txtViewDateHeure3.text  = "${list!![3].stepDateLettre} à ${list!![3].stepHeure}"
                    holder.txtViewDateHeure4.text  = "${list!![4].stepDateLettre} à ${list!![4].stepHeure}"
                    holder.txtViewDateHeure5.text  = "${list!![5].stepDateLettre} à ${list!![5].stepHeure}"

                    holder.lnEtape0.visibility = View.VISIBLE
                    holder.lnEtape1.visibility = View.VISIBLE
                    holder.lnEtape2.visibility = View.VISIBLE
                    holder.lnEtape3.visibility = View.VISIBLE
                    holder.lnEtape4.visibility = View.VISIBLE
                    holder.lnEtape5.visibility = View.VISIBLE
                }
            }
        }
    }
*/

}