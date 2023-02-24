package com.charmidezassiobo.tcrec.setup

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.Tc
import com.google.firebase.firestore.FirebaseFirestore

class TCAdapter(var items : List<Tc>) : RecyclerView.Adapter<TCAdapter.TCViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TCViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tc, parent, false)

        return TCViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TCViewHolder, position: Int) {
        val tc = items[position]
        holder.bindTC(tc)
        holder.step_change()
        holder.clickSuivant(tc)
    }

    class TCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var  numtc : TextView
         var numcamion : TextView
         var datetc : TextView
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

        //val dbInstance = FirebaseFirestore.getInstance()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Voyage")
        //var docRef = dbInstance.collection("Voyage").document().get()




        init {
            numtc = itemView.findViewById(R.id.textViewTCNum_item)
            numcamion  = itemView.findViewById(R.id.textViewCamionNum_item)
            datetc = itemView.findViewById(R.id.date_item)
            bayoStepView = itemView.findViewById(R.id.step_view_baoya_view_item)

            stepView = itemView.findViewById(R.id.step_view_baoya_view_item)
            btnSuivant = itemView.findViewById(R.id.button_suivant_item)

            stepvoyage = 0

            etape = 0

            stepsBeanList= ArrayList()
            stepBean0 = StepBean("Port", 0)
            stepBean1 = StepBean("Usine", -1)
            stepBean2 = StepBean("Chargement", -1)
            stepBean3 = StepBean("Sortie", -1)
            stepBean4 = StepBean("Arrivée Port", -1)
            step_tc = 0

        }

        fun bindTC(tc : Tc){
            numtc.text = tc.num_TC
            numcamion.text = tc.num_Camion
            datetc.text = tc.date_tc
            etape = tc.step_TC
        }

        fun clickSuivant(tc : Tc){
            val documentId = collectionRef
            val nummtc = tc.num_TC
            btnSuivant.setOnClickListener {
                Log.d("Document Id",nummtc)
                val query = collectionRef.whereEqualTo("num_TC", tc.num_TC)
                if (etape<5) {
                    etape = etape + 1
                    query.get().addOnSuccessListener { documents ->
                        for (document in documents) {
                            val documentId = document.id
                            val docRef = collectionRef.document(documentId)
                            docRef.update("step_TC", etape)
                                .addOnSuccessListener {
                                    Log.d(TAG, "Document mis à jour avec succès")
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Erreur lors de la mise à jour", e)
                                }
                        }
                    }
                    step_change()
                }
                /*
                    docRef.get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (document.exists()) {
                                // Mise à jour du document
                                docRef.update("step_TC", etape)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "Document mis à jour avec succès")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Erreur lors de la mise à jour", e)
                                    }
                            } else {
                                Log.d(TAG, "Le document n'existe pas dans la base de données")
                            }
                        } else {
                            Log.w(TAG, "Erreur lors de la recherche du document", task.exception)
                        }
                    }
                }
                */
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
                .setTextSize(8)
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(itemView.context,
                    R.drawable.ic_cam
                ))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(itemView.context,
                    R.drawable.next_step
                ))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(itemView.context,
                    R.color.blue
                ))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(itemView.context,
                    R.color.blue
                ))
                .setStepViewComplectedTextColor(ContextCompat.getColor(itemView.context,
                    R.color.blue
                ))
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(itemView.context,
                    R.color.blue
                ))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(itemView.context,
                    R.drawable.check
                ))
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
                    Toast.makeText(itemView.context!!,"Voyage Terminé", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}

