package com.charmidezassiobo.tcrec

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.data.Tc
import com.shuhart.stepview.StepView

class TCAdapter(var items : List<Tc>) : RecyclerView.Adapter<TCAdapter.TCViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TCViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tc, parent, false)

        return TCViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    var n = 0
    override fun onBindViewHolder(holder: TCViewHolder, position: Int) {
        val tc = items[position]
        holder.bindTC(tc)
        holder.clickSuivant()
        holder.setupStepView()
    }

    class TCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var  numtc : TextView
         var numcamion : TextView
         var datetc : TextView
         var stepView : StepView
         var btnSuivant : Button
        var step : Int

        init {
            numtc = itemView.findViewById(R.id.textViewTCNum_item)
            numcamion  = itemView.findViewById(R.id.textViewCamionNum_item)
            datetc = itemView.findViewById(R.id.date_item)

            stepView = itemView.findViewById(R.id.step_view_item)
            btnSuivant = itemView.findViewById(R.id.button_suivant_item)

            step = 0

            //stepView_etape = stepView.go()
        }

        fun bindTC(tc : Tc){
            numtc.text = tc.num_TC
            numcamion.text = tc.num_Camion
            datetc.text = tc.date_tc
            //stepView.go(tc.step_voyage!!,false)
            //step = tc.step_voyage!!.toInt()


        }


        fun clickSuivant(){
            btnSuivant.setOnClickListener {
                if (step<4){
                    step=step+1
                    stepView.go(step,false)

                } else if (step == 4){
                    stepView.done(true)
                    Toast.makeText(itemView.getContext(), "Voyage terminé sur ${stepView.currentStep}", Toast.LENGTH_SHORT).show()
                }
            }
        }


        @SuppressLint("ResourceType")
        fun setupStepView(){
            stepView.state
                .stepsNumber(5)
                .animationDuration(android.R.integer.config_longAnimTime)
                .commit()
        }

    }

}
