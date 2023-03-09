package com.charmidezassiobo.tcrec.setup

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.Tc
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

class TCAdapter(var items : List<Tc>) : RecyclerView.Adapter<TCAdapter.TCViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TCViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tc, parent, false)

        return TCViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TCViewHolder, position: Int) {
        val tc = items[position]
        holder.dateChangement(tc)
        holder.bindTC(tc)
        holder.step_change()
        holder.clickSuivant(tc)
        holder.itemClick(tc)
    }

    class TCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardV : CardView
         var  numtc : TextView
         var numcamion : TextView
         var datetc : TextView
         var phoneChauffeur : Button
         var numtcsecond : TextView

         var stepvoyage : Int
         var stepView : HorizontalStepView
         var btnSuivant : Button
         var stepsBeanList: MutableList<StepBean> = ArrayList()
         var bayoStepView : HorizontalStepView
         var stepBean0 : StepBean
         var stepBean1 : StepBean
         var stepBean2 : StepBean
         var stepBean3 : StepBean
         var stepBean4 : StepBean
         var etape : Int
         var step_tc : Int
         val txtSizeStep = 8

        var numPlomb_string : String

        var docRef : DocumentReference
        var db : FirebaseFirestore
        var collectionRef : CollectionReference

        init {
            cardV = itemView.findViewById(R.id.itemCardView_item_tc)
            numtc = itemView.findViewById(R.id.textViewTCNum_item)
            numcamion  = itemView.findViewById(R.id.textViewCamionNum_item)
            datetc = itemView.findViewById(R.id.date_item)
            bayoStepView = itemView.findViewById(R.id.step_view_baoya_view_item)
            stepView = itemView.findViewById(R.id.step_view_baoya_view_item)
            btnSuivant = itemView.findViewById(R.id.button_suivant_item)
            phoneChauffeur = itemView.findViewById(R.id.btnNumChauffeur)
            numtcsecond = itemView.findViewById(R.id.textViewSecondTCNum_item)
            stepvoyage = 0
            etape = 0
            stepsBeanList= ArrayList()
            stepBean0 = StepBean("Port", 0)
            stepBean1 = StepBean("Usine", -1)
            stepBean2 = StepBean("Chargement", -1)
            stepBean3 = StepBean("Sortie", -1)
            stepBean4 = StepBean("Arrivée Port", -1)
            step_tc = 0
            numPlomb_string = ""

            db = FirebaseFirestore.getInstance()
            collectionRef = db.collection("Voyage")
            docRef = collectionRef.document()
        }



        fun bindTC(tc : Tc){
            numtc.text = tc.num_TC
            numcamion.text = tc.num_Camion
            datetc.text = tc.date_tc
            etape = tc.step_TC
            phoneChauffeur.text = tc.num_tel_chauffeur
            numtcsecond.text = tc.num_TCSecond

            if (numtcsecond.text == ""){
                numtcsecond.isInvisible = true
            }
            if(phoneChauffeur.text == ""){
                phoneChauffeur.text = "Non Disponible"
                phoneChauffeur.isEnabled = false
                phoneChauffeur.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
            }
        }

        fun clickSuivant(tc : Tc){

            btnSuivant.setOnClickListener {
                val query = collectionRef.whereEqualTo("num_TC", tc.num_TC )
                                                                        .whereEqualTo("num_Camion", tc.num_Camion )
                                                                        .whereEqualTo("Date", tc.date_tc )

                Log.d("query","${query.get()}")
                if (etape<5) {
                    etape = etape + 1

                    query.get().addOnSuccessListener { documents ->
                        for (document in documents) {
                            //docRef = collectionRef.document()
                            docRef.update("step_TC", etape)
                            docRef.update("num_plomb",numPlomb_string)
                        }
                        Log.d("etat","${docRef.id}")
                    }
                    step_change()
                }
            }
        }

        @SuppressLint("ResourceType")
        fun setupStepView(){
            stepsBeanList.add(stepBean0)
            stepsBeanList.add(stepBean1)
            stepsBeanList.add(stepBean2)
            stepsBeanList.add(stepBean3)
            stepsBeanList.add(stepBean4)

            bayoStepView
                .setStepViewTexts(stepsBeanList)
                .setTextSize(txtSizeStep)

                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(itemView.context, R.drawable.ic_cam))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(itemView.context, R.drawable.next_step))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(itemView.context, R.color.blue))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
                .setStepViewComplectedTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(itemView.context, R.color.blue))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(itemView.context, R.drawable.check))

        }

        fun step_change(){
            when(etape){
                0 -> {
                    stepsBeanList= ArrayList()
                    stepBean0 = StepBean("Port", 0)
                    stepBean1 = StepBean("Usine", -1)
                    stepBean2 = StepBean("Chargement", -1)
                    stepBean3 = StepBean("Sortie", -1)
                    stepBean4 = StepBean("Arrivée Port", -1)
                    setupStepView()
                }
                1 -> {
                    stepsBeanList= ArrayList()
                    stepBean0 = StepBean("Port", 1)
                    stepBean1 = StepBean("Usine", 0)
                    stepBean2 = StepBean("Chargement", -1)
                    stepBean3 = StepBean("Sortie", -1)
                    stepBean4 = StepBean("Arrivée Port", -1)
                    setupStepView()

                }
                2 -> {
                    stepsBeanList= ArrayList()
                    stepBean0 = StepBean("Port", 1)
                    stepBean1 = StepBean("Usine", 1)
                    stepBean2 = StepBean("Chargement", 0)
                    stepBean3 = StepBean("Sortie", -1)
                    stepBean4 = StepBean("Arrivée Port", -1)
                    setupStepView()

                }
                3 -> {
                    stepsBeanList= ArrayList()
                    stepBean0 = StepBean("Port", 1)
                    stepBean1 = StepBean("Usine", 1)
                    stepBean2 = StepBean("Chargement", 1)
                    stepBean3 = StepBean("Sortie", 0)
                    stepBean4 = StepBean("Arrivée Port", -1)
                    setupStepView()
                    popUpPlomb()
                }
                4 -> {
                    stepsBeanList= ArrayList()
                    stepBean0 = StepBean("Port", 1)
                    stepBean1 = StepBean("Usine", 1)
                    stepBean2 = StepBean("Chargement", 1)
                    stepBean3 = StepBean("Sortie", 1)
                    stepBean4 = StepBean("Arrivée Port", 0)
                    setupStepView()

                }
                5 -> {
                    stepsBeanList= ArrayList()
                    stepBean0 = StepBean("Port", 1)
                    stepBean1 = StepBean("Usine", 1)
                    stepBean2 = StepBean("Chargement", 1)
                    stepBean3 = StepBean("Sortie", 1)
                    stepBean4 = StepBean("Arrivée Port", 1)
                    setupStepView()
                    btnSuivant.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
                    btnSuivant.setText(R.string.fin_voyage)
                    btnSuivant.isEnabled = false
                    itemView.setBackgroundResource(R.drawable.rounded_cardview_gray)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun dateChangement(tc : Tc){
            val currentDate = LocalDate.now()
            var dateCurrent = "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"
            var dateCurrentHier = "${currentDate.dayOfMonth - 1}/${currentDate.monthValue}/${currentDate.year}"
            var dateCurrentAvantHier = "${currentDate.dayOfMonth - 2}/${currentDate.monthValue}/${currentDate.year}"
            val date = tc.date_tc

            when (date){
                dateCurrent -> { tc.date_tc = "Aujourd'hui" }
                dateCurrentHier -> { tc.date_tc = "Hier"}
                dateCurrentAvantHier -> { tc.date_tc = "Avant Hier"}
            }
        }

        private fun popUpDetails(tc : Tc){
            val v = View.inflate(itemView.context,R.layout.popup_details_voyages, null)
            val  builder = AlertDialog.Builder(itemView.context)
            builder.setView(v)
            var date_etape_tc_popup : TextView = v.findViewById(R.id.textView_etape_et_date)
            var numcamion_popup : TextView = v.findViewById(R.id.textViewCamionNum_item)

            var  numtc_popup : TextView = v.findViewById(R.id.textViewTCNum_item)
            var num_plomb_tc1 : TextView = v.findViewById(R.id.num_plomb_tc_1)
            var numtcsecond_popup : TextView = v.findViewById(R.id.textViewTCNum_item2)
            var num_plomb_tc2 : TextView = v.findViewById(R.id.num_plomb_tc_2)

            var phoneChauffeur_popup : TextView = v.findViewById(R.id.textViewTCnumPhoneChauffeur)


            when(tc.step_TC){
                0 -> date_etape_tc_popup.text = "Tc au port / ${tc.date_tc}"
                1 -> date_etape_tc_popup.text = "Tc à l'usine / ${tc.date_tc}"
                2 -> date_etape_tc_popup.text = "Tc en chargement / ${tc.date_tc}"
                3 -> date_etape_tc_popup.text = "Tc sortie de l'entrepot / ${tc.date_tc}"
                4 -> date_etape_tc_popup.text = "Tc arrivé au Port"
                5 -> date_etape_tc_popup.text = "Tc arrivé à bon port"
            }
            numcamion_popup.text = tc.num_Camion
            numtc_popup.text = tc.num_TC
            numtcsecond_popup.text = tc.num_TCSecond
            num_plomb_tc1.text = tc.num_bind
            phoneChauffeur_popup.text = tc.num_tel_chauffeur



            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        }

        private fun popUpPlomb(){
            val v = View.inflate(itemView.context,R.layout.popup_num_bind, null)
            val  builder = AlertDialog.Builder(itemView.context)
            builder.setView(v)

            val recupEditText_bind = v.findViewById<TextInputEditText>(R.id.textInputBindNum)
            val labelBind = v.findViewById<TextInputLayout>(R.id.textFieldBindNum)
            val btnRecupBind = v.findViewById<Button>(R.id.btn_recup_bind_tc)
            val txtView_popup = v.findViewById<TextView>(R.id.txtView_pop_up)

            btnRecupBind.setOnClickListener {
                val numbind_tc = recupEditText_bind.text
                Log.d("Bind ",numbind_tc.toString())
                numPlomb_string = numbind_tc.toString()
                recupEditText_bind.text?.clear()
                recupEditText_bind.isVisible = false
                labelBind.isVisible = false
                btnRecupBind.isVisible = false
                txtView_popup.setText(R.string.poursuivre_voyage_popup)
                txtView_popup.setTextColor(v.resources.getColor(R.color.autre_vert_sombre))
            }

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        fun itemClick(tc : Tc){
            itemView.setOnClickListener{
                //Log.d("Itemview cliqué","click ${tc.date_tc}")
                popUpDetails(tc)
            }
        }

    }

}


