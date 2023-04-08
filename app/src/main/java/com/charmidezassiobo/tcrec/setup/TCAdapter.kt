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
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.ui.suivietc.SuivietcSousFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

class TCAdapter(var items : List<Tc>, val listener : RecyclerViewClickItemInterface) : RecyclerView.Adapter<TCAdapter.TCViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TCViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tc, parent, false)

        return TCViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TCViewHolder, position: Int) {
        val tc = items[position]

        //holder.dateChangement(tc)
        holder.bindTC(tc)
        holder.import_export_text(tc.type_transat)

        holder.step_change(tc)
        holder.clickSuivant(tc)

        //holder.itemClick(tc, items)
        //holder.itemView.setOnClickListener { if ( onClickListener != null){ onClickListener!!.onClick(position, tc ) } }

        holder.callButton()
    }

    inner class TCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardV : CardView
         var  numtc : TextView
         var numcamion : TextView
         var datetc : TextView
         var phoneChauffeur : Button
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

        var stepBean0_import : StepBean
        var stepBean1_import : StepBean
        var stepBean2_import : StepBean
        var stepBean3_import : StepBean

         var etape : Int
         var step_tc : Int
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
            stepBean3_export = StepBean("Sortie", -1)
            stepBean4_export = StepBean("Arrivée Port", -1)

            stepBean0_import = StepBean("Arrivée Port",0)
            stepBean1_import = StepBean("Dédouanement",-1)
            stepBean2_import = StepBean("Sortie",-1)
            stepBean3_import = StepBean("Destination Finale",-1)

            step_tc = 0
            numPlomb_string = ""
            numPlomb_second_string = ""

            itemView.setOnLongClickListener {
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


            if(phoneChauffeur.text == "null" || phoneChauffeur == null || phoneChauffeur.text == ""){
                phoneChauffeur.text = "Non Disponible"
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
                                val query = db.collection("Voyage")
                                    .whereEqualTo("num_TC", tc.num_TC)
                                    .whereEqualTo("num_Camion", tc.num_Camion)
                                query.get().addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        var docId = document.id
                                        iddoc = docId
                                        val docRef = db.collection("Voyage").document(docId)
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
                            if (etape < 5){
                                etape = etape + 1
                                step_change(tc)
                                val db = FirebaseFirestore.getInstance()
                                val query = db.collection("Voyage")
                                    .whereEqualTo("num_TC", tc.num_TC)
                                    .whereEqualTo("num_Camion", tc.num_Camion)
                                query.get().addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        var docId = document.id
                                        iddoc = docId
                                        val docRef = db.collection("Voyage").document(docId)
                                        docRef.update("step_TC", etape)
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
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 0)
                            stepBean1_export = StepBean("Usine", -1)
                            stepBean2_export = StepBean("Chargement", -1)
                            stepBean3_export = StepBean("Sortie", -1)
                            stepBean4_export = StepBean("Arrivée Port", -1)
                            setupStepView(tc.type_transat)
                        }
                        1 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 0)
                            stepBean2_export = StepBean("Chargement", -1)
                            stepBean3_export = StepBean("Sortie", -1)
                            stepBean4_export = StepBean("Arrivée Port", -1)
                            setupStepView(tc.type_transat)

                        }
                        2 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 1)
                            stepBean2_export = StepBean("Chargement", 0)
                            stepBean3_export = StepBean("Sortie", -1)
                            stepBean4_export = StepBean("Arrivée Port", -1)
                            setupStepView(tc.type_transat)

                        }
                        3 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 1)
                            stepBean2_export = StepBean("Chargement", 1)
                            stepBean3_export = StepBean("Sortie", 0)
                            stepBean4_export = StepBean("Arrivée Port", -1)
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
                            stepBean3_export = StepBean("Sortie", 1)
                            stepBean4_export = StepBean("Arrivée Port", 0)
                            setupStepView(tc.type_transat)

                        }
                        5 -> {
                            stepsBeanList= ArrayList()
                            stepBean0_export = StepBean("Port", 1)
                            stepBean1_export = StepBean("Usine", 1)
                            stepBean2_export = StepBean("Chargement", 1)
                            stepBean3_export = StepBean("Sortie", 1)
                            stepBean4_export = StepBean("Arrivée Port", 1)
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
                val query = db.collection("Voyage")
                    .whereEqualTo("num_TC", tc.num_TC)
                    .whereEqualTo("num_Camion", tc.num_Camion)
                query.get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        var docId = document.id
                        iddoc = docId
                        val docRef = db.collection("Voyage").document(docId)
                        //docRef.update("step_TC", etape)
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
                val query = db.collection("Voyage")
                    .whereEqualTo("num_TC", tc.num_TC)
                    .whereEqualTo("num_Camion", tc.num_Camion)
                query.get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        var docId = document.id
                        iddoc = docId
                        val docRef = db.collection("Voyage").document(docId)
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
                //val phoneNumber = "92 87 65    65"
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

    fun setFilteredList(items : List<Tc>){
        this.items = items
        notifyDataSetChanged()
    }

}


/*
   // A function to bind the onclickListener.
   fun setOnClickListener(onClickListener: OnClickListener) {
       this.onClickListener = onClickListener
   }

   // onClickListener Interface
   interface OnClickListener {
       fun onClick(position: Int, model: Tc)
   }
   */

/*
in init
            itemView.setOnClickListener {
                val position = adapterPosition
                listener.onItemClick(position)
            }
            */

/*
in inner class ViewHolder
        //private var listener : (() -> Unit)? = null

        private fun popUpDetails(tc : Tc){
            val v = View.inflate(itemView.context,R.layout.popup_details_voyages, null)
            val  builder = AlertDialog.Builder(itemView.context)
            builder.setView(v)
            var date_etape_tc_popup : TextView = v.findViewById(R.id.textView_etape_et_date)
            var numcamion_popup : EditText = v.findViewById(R.id.textViewCamionNum_item)

            var  numtc_popup : TextView = v.findViewById(R.id.textViewTCNum_item)
            var num_plomb_tc1 : TextView = v.findViewById(R.id.num_plomb_tc_1)

            var numtcsecond_popup : TextView = v.findViewById(R.id.textViewTCNum_item2)
            var num_plomb_tc2 : TextView = v.findViewById(R.id.num_plomb_tc_2)

            var phoneChauffeur_popup : EditText = v.findViewById(R.id.textViewTCnumPhoneChauffeur)
            var btn_maj_popup : Button = v.findViewById(R.id.btn_popup_maj)

                when(tc.type_transat){
                    "Import" -> {
                        when (tc.step_TC) {
                            0 -> date_etape_tc_popup.text = "Tc arrivé au Port / ${tc.date_tc}"
                            1 -> date_etape_tc_popup.text = "Tc en Dédouanement / ${tc.date_tc}"
                            2 -> date_etape_tc_popup.text = "Tc sorti du Port / ${tc.date_tc}"
                            3 -> date_etape_tc_popup.text = "Tc arrivé à Destination / ${tc.date_tc}"
                            4 -> date_etape_tc_popup.text = "Transaction Terminé / ${tc.date_tc}"
                        }
                    }
                    "Export" -> {
                        when(tc.step_TC){
                            0 -> date_etape_tc_popup.text = "Tc au port / ${tc.date_tc}"
                            1 -> date_etape_tc_popup.text = "Tc à l'usine / ${tc.date_tc}"
                            2 -> date_etape_tc_popup.text = "Tc en chargement / ${tc.date_tc}"
                            3 -> date_etape_tc_popup.text = "Tc sortie de l'entrepot / ${tc.date_tc}"
                            4 -> date_etape_tc_popup.text = "Tc arrivé au Port / ${tc.date_tc}"
                            5 -> date_etape_tc_popup.text = "Transaction Terminé / ${tc.date_tc}"
                        }
                    }
                }

            numcamion_popup.setText(tc.num_Camion)
            numtc_popup.text = tc.num_TC
            numtcsecond_popup.text = tc.num_TCSecond
            num_plomb_tc1.text = tc.num_plomb
            num_plomb_tc2.text = tc.num_plomb_second
            phoneChauffeur_popup.setText(tc.num_tel_chauffeur)

            // Checker si le phone est disponible
            if(phoneChauffeur_popup.text.toString() == "null" || phoneChauffeur_popup == null || phoneChauffeur_popup.text.toString() == ""){
                phoneChauffeur_popup.setText("Non Disponible")
                phoneChauffeur_popup.isEnabled = false
                phoneChauffeur_popup.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
            }
            // Checker si le second tc est disponible
            if (tc.num_TCSecond == "null" || tc.num_TCSecond == null || tc.num_TCSecond == "" ){
                numtcsecond_popup.text = "Non disponible"
                numtcsecond_popup.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
            }
            //Checker si le second plomb est dispo
            if (tc.num_plomb_second == "null" || tc.num_plomb_second == null || tc.num_plomb_second == "" ){
                num_plomb_tc2.isInvisible = true
            }
            //Checker si le premier plomb est dispo
            if (tc.num_plomb == "null" || tc.num_plomb == null || tc.num_plomb == "" ){
                num_plomb_tc1.isInvisible = true
            }
            var iddoc  =""
            btn_maj_popup.setOnClickListener {
                val nouveau_num_immatriculation = numcamion_popup.text.toString()
                val nouveau_num_chauffeur = phoneChauffeur_popup.text.toString()
                Log.d("btn rouge", "Appuyé $nouveau_num_immatriculation")

                val db = FirebaseFirestore.getInstance()
                val query = db.collection("Voyage")
                    .whereEqualTo("num_TC", tc.num_TC)
                    .whereEqualTo("num_Camion", tc.num_Camion)
                query.get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        var docId = document.id
                        iddoc = docId
                        val docRef = db.collection("Voyage").document(docId)
                        docRef.update("num_Camion", nouveau_num_immatriculation)
                        docRef.update("phone_chauffeur_TC",nouveau_num_chauffeur)
                    }
                }
                btn_maj_popup.setBackground(getDrawable(itemView.context,R.drawable.btn_drawable_not_selected))
                btn_maj_popup.text = "Mis à jour éffectué"
            }


            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }


        fun itemClick(tc : Tc, items : List<Tc>){
            itemView.setOnClickListener{
                //popUpDetails(tc)
            }
        }
        */


