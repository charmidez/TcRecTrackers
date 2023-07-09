package com.charmidezassiobo.tcrec.setup

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.HeureStep
import com.charmidezassiobo.tcrec.data.Tc
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore

class TCAdapter(var items : List<Tc>, val listener : RecyclerViewClickItemInterface) : RecyclerView.Adapter<TCAdapter.TCViewHolder>() {

    val dataBasePath = AllVariables().dbPath

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TCViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tc, parent, false)
        return TCViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TCViewHolder, position: Int) {
        val tc = items[position]

        holder.bindTC(tc)
        holder.import_export_text(tc.type_transat)
        holder.step_change(tc)
        holder.clickSuivant(tc)
        holder.callButton()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    inner class TCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardV : FrameLayout
         var  numtc : TextView
         var numcamion : TextView
         var datetc : TextView
         var phoneChauffeur : TextView
         var numtcsecond : TextView
         var txtV_import_export : TextView

         var stepvoyage : Int
         var stepView : HorizontalStepView
         var btnSuivant : Button
         var stepsBeanList: MutableList<StepBean> = ArrayList()
         var bayoStepView : HorizontalStepView
         var stepBean0_export : StepBean
         var stepBean1_export : StepBean
         var stepBean2_export : StepBean
         var stepBean3_export : StepBean
         var stepBean4_export : StepBean
         var stepBean5_export : StepBean

        var stepBean0_import : StepBean
        var stepBean1_import : StepBean
        var stepBean2_import : StepBean
        var stepBean3_import : StepBean

         var etape : Int
         var step_tc : Int
         //var dateActuelStep : HeureStep
         //var allFunctions = AllFunctions()

        /*var dateRealChiffre : String
        var dateRealLettre : String
        var heureRealChiffre : String
        */
         var tableauSateHeureStep : MutableList<HeureStep>
        //var dbOldDateStep : MutableList<HeureStep>
        val txtSizeStep = 8

        var numPlomb_string : String
        var numPlomb_second_string : String

        //Pour  tester la connection
        val connectivityManager = itemView.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting

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
            txtV_import_export = itemView.findViewById(R.id.textView_import_export)
            stepvoyage = 0
            etape = 0
            stepsBeanList= ArrayList()
            stepBean0_export = StepBean("Port", 0)
            stepBean1_export = StepBean("Usine", -1)
            stepBean2_export = StepBean("Chargement", -1)
            stepBean3_export = StepBean("Douane", -1)
            stepBean4_export = StepBean("Sortie", -1)
            stepBean5_export = StepBean("Arrivée Port", -1)

            stepBean0_import = StepBean("Arrivée Port",0)
            stepBean1_import = StepBean("Dédouanement",-1)
            stepBean2_import = StepBean("Sortie",-1)
            stepBean3_import = StepBean("Destination Finale",-1)

            step_tc = 0
            numPlomb_string = ""
            numPlomb_second_string = ""

            /*dateRealChiffre = allFunctions.miseEnPlaceDate(true)
            dateRealLettre = allFunctions.miseEnPlaceDate(false)
            heureRealChiffre = allFunctions.miseEnPlaceHeure()

            dateActuelStep = HeureStep(dateRealChiffre,dateRealLettre,heureRealChiffre)*/
            tableauSateHeureStep = mutableListOf()
            //dbOldDateStep = mutableListOf()

            itemView.setOnClickListener {
                val position = adapterPosition
                listener.onItemClick(position)
                true
            }
        }

        fun bindTC(tc : Tc){
            numtc.text = tc.num_TC
            numcamion.text = tc.num_Camion
            datetc.text = tc.date_tc
            etape = tc.step_TC
            phoneChauffeur.text = tc.num_tel_chauffeur
            numtcsecond.text = tc.num_TCSecond
            tableauSateHeureStep = tc.lesStepDateHour

            if(phoneChauffeur.text == "null" || phoneChauffeur == null || phoneChauffeur.text == ""){
                phoneChauffeur.text = "Indisponible"
                phoneChauffeur.isEnabled = false
                phoneChauffeur.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
            }
            if (numtcsecond.text == "null" || numtcsecond == null || numtcsecond.text == "" ){
                numtcsecond.isInvisible = true
            }
        }

        fun clickSuivant(tc : Tc){

            if (isConnected) {
                // Connexion Internet disponible
                when(tc.type_transat){
                    "Import" -> {
                        var iddoc  =""
                        btnSuivant.setOnClickListener{
                            if (etape < 4){
                                etape = etape + 1
                                step_change(tc)
                                val db = FirebaseFirestore.getInstance()
                                val query = db.collection(dataBasePath)
                                    .whereEqualTo("num_TC", tc.num_TC)
                                    .whereEqualTo("num_Camion", tc.num_Camion)
                                query.get().addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        var docId = document.id
                                        iddoc = docId
                                        val docRef = db.collection(dataBasePath).document(docId)
                                        docRef.update("step_TC", etape)
                                    }
                                    Log.d("Doc Id",iddoc)
                                }
                            }
                        }
                    }
                    "Export" -> {
                        var iddoc  =""
                        btnSuivant.setOnClickListener{
                            if (etape < 6){
                                etape = etape + 1
                                step_change(tc)
                                val db = FirebaseFirestore.getInstance()
                                val query = db.collection(dataBasePath)
                                    .whereEqualTo("num_TC", tc.num_TC)
                                    .whereEqualTo("num_Camion", tc.num_Camion)
                                query.get().addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        var docId = document.id
                                        iddoc = docId
                                        val docRef = db.collection(dataBasePath).document(docId)
                                        docRef.update("step_TC", etape)
                                        //Mettre à jour la date à chaque suivant

                                        var allFunctions = AllFunctions()
                                        var dateRealChiffre = allFunctions.miseEnPlaceDate(true)
                                        var dateRealLettre = allFunctions.miseEnPlaceDate(false)
                                        var heureRealChiffre = allFunctions.miseEnPlaceHeure()

                                        //var dateActuelStep = HeureStep(dateRealChiffre,dateRealLettre,heureRealChiffre)
                                        //tableauSateHeureStep = dbOldDateStep

                                        tableauSateHeureStep.add(HeureStep(dateRealChiffre,dateRealLettre,heureRealChiffre))
                                        docRef.update("lesStepDateHour",tableauSateHeureStep )
                                    }
                                    Log.d("Doc Id",iddoc)
                                }
                            }
                        }

                    }
                }
            }

        }

        @SuppressLint("ResourceType")
        fun setupStepView(typetransact : String){

            when(typetransact){
                "Import" -> {
                    stepsBeanList.add(stepBean0_import)
                    stepsBeanList.add(stepBean1_import)
                    stepsBeanList.add(stepBean2_import)
                    stepsBeanList.add(stepBean3_import)

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

                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(itemView.context, R.drawable.ic_cam))
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(itemView.context, R.drawable.next_step))
                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(itemView.context, R.color.blue))
                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
                        .setStepViewComplectedTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(itemView.context, R.color.blue))
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(itemView.context, R.drawable.check))

                }
            }

        }

        fun step_change(tc : Tc){
            when(tc.type_transat){
                "Import" -> {
                    when(etape){
                        0 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_import = StepBean("Arrivée Port", 0)
                            stepBean1_import = StepBean("Dédouanement", -1)
                            stepBean2_import = StepBean("Sortie", -1)
                            stepBean3_import = StepBean("Destination Finale", -1)
                            setupStepView(tc.type_transat)
                        }
                        1 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_import = StepBean("Arrivée Port", 1)
                            stepBean1_import = StepBean("Dédouanement", 0)
                            stepBean2_import = StepBean("Sortie", -1)
                            stepBean3_import = StepBean("Destination Finale", -1)
                            setupStepView(tc.type_transat)
                        }
                        2 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_import = StepBean("Arrivée Port", 1)
                            stepBean1_import = StepBean("Dédouanement", 1)
                            stepBean2_import = StepBean("Sortie", 0)
                            stepBean3_import = StepBean("Destination Finale", -1)
                            setupStepView(tc.type_transat)
                        }
                        3 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_import = StepBean("Arrivée Port", 1)
                            stepBean1_import = StepBean("Dédouanement", 1)
                            stepBean2_import = StepBean("Sortie", 1)
                            stepBean3_import = StepBean("Destination Finale", 0)
                            setupStepView(tc.type_transat)
                        }
                        4 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_import = StepBean("Arrivée Port", 1)
                            stepBean1_import = StepBean("Dédouanement", 1)
                            stepBean2_import = StepBean("Sortie", 1)
                            stepBean3_import = StepBean("Destination Finale", 1)
                            setupStepView(tc.type_transat)
                            btnSuivant.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
                            numcamion.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
                            numtc.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
                            numtcsecond.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
                            phoneChauffeur.setBackground(getDrawable(itemView.context,R.drawable.btn_call_not_selected))
                            btnSuivant.setText(R.string.fin_voyage)
                            btnSuivant.isEnabled = false
                            itemView.setBackgroundResource(R.drawable.rounded_cardview_gray)
                        }
                    }
                }
                "Export" -> {
                    when(etape){
                        0 -> {
                            //var step1 = tc.lesStepDateHour[0].stepDateChiffre.toString()
                            //Log.d("step1", step1)
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port ", 0)
                            stepBean1_export = StepBean("Usine", -1)
                            stepBean2_export = StepBean("Chargement", -1)
                            stepBean3_export = StepBean("Douane", -1)
                            stepBean4_export = StepBean("Sortie", -1)
                            stepBean5_export = StepBean("Arrivée Port", -1)
                            setupStepView(tc.type_transat)
                        }
                        1 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 0)
                            stepBean2_export = StepBean("Chargement", -1)
                            stepBean3_export = StepBean("Douane", -1)
                            stepBean4_export = StepBean("Sortie", -1)
                            stepBean5_export = StepBean("Arrivée Port", -1)
                            setupStepView(tc.type_transat)

                        }
                        2 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 1)
                            stepBean2_export = StepBean("Chargement", 0)
                            stepBean3_export = StepBean("Douane", -1)
                            stepBean4_export = StepBean("Sortie", -1)
                            stepBean5_export = StepBean("Arrivée Port", -1)
                            setupStepView(tc.type_transat)

                        }
                        3 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 1)
                            stepBean2_export = StepBean("Chargement", 1)
                            stepBean3_export = StepBean("Douane", 0)
                            stepBean4_export = StepBean("Sortie", -1)
                            stepBean5_export = StepBean("Arrivée Port", -1)
                            setupStepView(tc.type_transat)

                            if (tc.num_TCSecond == "null" || tc.num_TCSecond == null || tc.num_TCSecond == "" ){
                                popUpPlomb(tc)
                            } else {
                                popUpPlomb_second(tc)
                            }

                        }
                        4 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 1)
                            stepBean2_export = StepBean("Chargement", 1)
                            stepBean3_export = StepBean("Douane", 1)
                            stepBean4_export = StepBean("Sortie", 0)
                            stepBean5_export = StepBean("Arrivée Port", -1)
                            setupStepView(tc.type_transat)

                        }
                        5 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 1)
                            stepBean2_export = StepBean("Chargement", 1)
                            stepBean3_export = StepBean("Douane", 1)
                            stepBean4_export = StepBean("Sortie", 1)
                            stepBean5_export = StepBean("Arrivée Port", 0)
                            setupStepView(tc.type_transat)
                        }
                        6 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 1)
                            stepBean2_export = StepBean("Chargement", 1)
                            stepBean3_export = StepBean("Douane", 1)
                            stepBean4_export = StepBean("Sortie", 1)
                            stepBean5_export = StepBean("Arrivée Port", 1)
                            setupStepView(tc.type_transat)
                            btnSuivant.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
                            btnSuivant.setText(R.string.fin_voyage)
                            btnSuivant.isEnabled = false
                        }
                    }
                }

            }
        }

        private fun popUpPlomb(tc : Tc){
            val v = View.inflate(itemView.context,R.layout.popup_num_bind, null)
            val  builder = AlertDialog.Builder(itemView.context)
            builder.setView(v)

            val recupEditText_bind = v.findViewById<TextInputEditText>(R.id.textInputBindNum)
            val labelBind = v.findViewById<TextInputLayout>(R.id.textFieldBindNum)
            val btnRecupBind = v.findViewById<Button>(R.id.btn_recup_bind_tc)
            val txtView_popup = v.findViewById<TextView>(R.id.txtView_pop_up)

            txtView_popup.text = "Veuiller renseigner le numéro Plomb du TC : ${tc.num_TC} "

            var iddoc  =""
            btnRecupBind.setOnClickListener {
                val numbind_tc = recupEditText_bind.text
                Log.d("Bind ",numbind_tc.toString())
                numPlomb_string = numbind_tc.toString()
                val db = FirebaseFirestore.getInstance()
                val query = db.collection(dataBasePath)
                    .whereEqualTo("num_TC", tc.num_TC)
                    .whereEqualTo("num_Camion", tc.num_Camion)
                query.get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        var docId = document.id
                        iddoc = docId
                        val docRef = db.collection(dataBasePath).document(docId)
                        docRef.update("num_plomb_TC",numPlomb_string)
                    }
                    Log.d("Doc Id",iddoc)
                }
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

        private fun popUpPlomb_second(tc : Tc){
            val v = View.inflate(itemView.context,R.layout.popup_num_bind_second, null)
            val  builder = AlertDialog.Builder(itemView.context)
            builder.setView(v)

            //plomb 1
            val labelBind = v.findViewById<TextInputLayout>(R.id.textFieldBindNum)
            val recupEditText_bind = v.findViewById<TextInputEditText>(R.id.textInputBindNum)
            //plomb 2
            val labelBind_second = v.findViewById<TextInputLayout>(R.id.textFieldBindNum_second)
            val recupEditText_bind_second = v.findViewById<TextInputEditText>(R.id.textInputBindNum_second)

            val txtView_popup = v.findViewById<TextView>(R.id.txtView_pop_up)
            val btnRecupBind = v.findViewById<Button>(R.id.btn_recup_bind_tc)

            txtView_popup.text = "Veuiller renseigner le numéro Plomb du TC1 : ${tc.num_TC} et TC2 : ${tc.num_TCSecond}  "

            var iddoc  =""
            btnRecupBind.setOnClickListener {
                val numbind_tc = recupEditText_bind.text
                val numbind_tc_second = recupEditText_bind_second.text
                numPlomb_string = numbind_tc.toString()
                numPlomb_second_string = numbind_tc_second.toString()
                val db = FirebaseFirestore.getInstance()
                val query = db.collection(dataBasePath)
                    .whereEqualTo("num_TC", tc.num_TC)
                    .whereEqualTo("num_Camion", tc.num_Camion)
                query.get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        var docId = document.id
                        iddoc = docId
                        val docRef = db.collection(dataBasePath).document(docId)
                        docRef.update("num_plomb_TC",numPlomb_string)
                        docRef.update("num_plomb_TC_2",numPlomb_second_string)
                    }
                    Log.d("Doc Id",iddoc)
                }
                recupEditText_bind.text?.clear()
                recupEditText_bind_second.text?.clear()
                recupEditText_bind.isVisible = false
                recupEditText_bind_second.isVisible = false
                labelBind.isVisible = false
                labelBind_second.isVisible = false
                btnRecupBind.isVisible = false
                txtView_popup.setText(R.string.poursuivre_voyage_popup)
                txtView_popup.setTextColor(v.resources.getColor(R.color.autre_vert_sombre))
            }

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        fun callButton(){
            phoneChauffeur.setOnClickListener {
                val phoneNumber = phoneChauffeur.text.toString()
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:$phoneNumber")
                itemView.context.startActivity(intent)
            }
        }

        fun import_export_text(typeTransact : String){
            txtV_import_export.text = typeTransact
        }

    }
}
