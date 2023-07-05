package com.charmidezassiobo.tcrec.setup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baoyachi.stepview.HorizontalStepView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.ui.clientloginadmin.ClientActivity

class TCResultAdapter(var context : Context, var items : List<Tc>) : RecyclerView.Adapter<TCResultAdapter.TCResultViewHolder>() {



    class TCResultViewHolder(itemView : View)  : RecyclerView.ViewHolder(itemView) {

        //var etapeActuelDuTc : TextView
        var dateCreationTc : TextView
        var numBooking : TextView

        var numTc : TextView
        var numTc2 : TextView
        var numPlomb : TextView
        var numPlomb2 : TextView

        var lnTc1 : LinearLayout
        var lnTc2 : LinearLayout

        var txtViewStepExact : TextView

        var typetransact: String

        var bayoStepView : HorizontalStepView

        var theAllFunction : AllFunctions

        init {

            dateCreationTc = itemView.findViewById(R.id.txtView_creation_tc)

            numBooking = itemView.findViewById(R.id.txtView_numBooking_result)

            numTc = itemView.findViewById(R.id.textViewTCNum_result)
            numTc2 = itemView.findViewById(R.id.textViewTC2Num_result)

            numPlomb = itemView.findViewById(R.id.num_plomb_result)
            numPlomb2 = itemView.findViewById(R.id.num_plomb2_result)

            txtViewStepExact = itemView.findViewById(R.id.textView_etap_exact)

            lnTc1 = itemView.findViewById(R.id.lineairLayoutTc1)
            lnTc2 = itemView.findViewById(R.id.lineairLayoutTc2)

            typetransact = ""

            bayoStepView = itemView.findViewById(R.id.step_view_baoya_view_result)

            theAllFunction = AllFunctions()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TCResultViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_result,parent,false)
        return  TCResultViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TCResultViewHolder, position: Int) {

        val tc = items[position]

        holder.numBooking.text = tc.num_booking

        holder.numTc.text = tc.num_TC
        holder.numPlomb.text = tc.num_plomb

        holder.numTc2.text = tc.num_TCSecond
        holder.numPlomb2.text = tc.num_plomb_second

        holder.typetransact = tc.type_transat


        if (holder.numTc2.text.isNullOrEmpty()){
            holder.lnTc2.visibility = View.GONE
        }

        if (holder.numPlomb.text.isNullOrEmpty()){
            holder.numPlomb.visibility = View.GONE
        }
        if (holder.numPlomb2.text.isNullOrEmpty()){
            holder.numPlomb2.visibility = View.GONE
        }

        when(tc.step_TC){
            0 -> {
                holder.txtViewStepExact.text = "Conteneur au port  : ${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            1 -> {
                holder.txtViewStepExact.text = "Conteneur à l'usine  : ${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            2 -> {
                holder.txtViewStepExact.text = "Conteneur en chargement  : ${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            3 -> {
                holder.txtViewStepExact.text = "Conteneur à la douane  : ${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            4 -> {
                holder.txtViewStepExact.text = "Conteneur sortie de l'entrepot  : ${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            5 -> {
                holder.txtViewStepExact.text = "Conteneur plein arrivé au port  : ${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            6 -> {
                holder.txtViewStepExact.text = "Transaction terminé  : ${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
        }

        holder.theAllFunction.stepChange(tc.step_TC, holder.typetransact,holder.bayoStepView, context)

        holder.dateCreationTc.text = "${tc.lesStepDateHour[tc.step_TC].stepDateLettre}"

    }
}