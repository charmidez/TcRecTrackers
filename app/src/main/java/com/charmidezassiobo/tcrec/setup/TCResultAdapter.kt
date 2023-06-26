package com.charmidezassiobo.tcrec.setup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.ui.clientlogin.ClientActivity

class TCResultAdapter(var context : ClientActivity, var items : List<Tc>) : RecyclerView.Adapter<TCResultAdapter.TCResultViewHolder>() {
    class TCResultViewHolder(itemView : View)  : RecyclerView.ViewHolder(itemView) {

        var etapeActuelDuTc : TextView
        //var dateActuelTc : TextView
        var numBooking : TextView

        var numTc : TextView
        var numTc2 : TextView
        var numPlomb : TextView
        var numPlomb2 : TextView

        var lnTc1 : LinearLayout
        var lnTc2 : LinearLayout

        var lnEtape0 : LinearLayout
        var txtViewDateHeure0 : TextView

        var lnEtape1 : LinearLayout
        var txtViewDateHeure1 : TextView

        var lnEtape2 : LinearLayout
        var txtViewDateHeure2 : TextView

        var lnEtape3 : LinearLayout
        var txtViewDateHeure3 : TextView

        var lnEtape4 : LinearLayout
        var txtViewDateHeure4 : TextView

        var lnEtape5 : LinearLayout
        var txtViewDateHeure5 : TextView

        //var lnEtape6 : LinearLayout
        //var txtViewDateHeure6 : TextView

        init {
            etapeActuelDuTc = itemView.findViewById(R.id.textView_etape_actuel_tc)
            //dateActuelTc = itemView.findViewById(R.id.txtView_heure_date_etape)

            numBooking = itemView.findViewById(R.id.txtView_numBooking_result)

            numTc = itemView.findViewById(R.id.textViewTCNum_result)
            numTc2 = itemView.findViewById(R.id.textViewTC2Num_result)

            numPlomb = itemView.findViewById(R.id.num_plomb_result)
            numPlomb2 = itemView.findViewById(R.id.num_plomb2_result)

            lnTc1 = itemView.findViewById(R.id.lineairLayoutTc1)
            lnTc2 = itemView.findViewById(R.id.lineairLayoutTc2)

            lnEtape0 = itemView.findViewById(R.id.lineairLaout_etap_0)
            txtViewDateHeure0 = itemView.findViewById(R.id.textView_date_et_heure_0)

            lnEtape1 = itemView.findViewById(R.id.lineairLaout_etap_1)
            txtViewDateHeure1 = itemView.findViewById(R.id.textView_date_et_heure_1)

            lnEtape2 = itemView.findViewById(R.id.lineairLaout_etap_2)
            txtViewDateHeure2 = itemView.findViewById(R.id.textView_date_et_heure_2)

            lnEtape3 = itemView.findViewById(R.id.lineairLaout_etap_3)
            txtViewDateHeure3 = itemView.findViewById(R.id.textView_date_et_heure_3)

            lnEtape4 = itemView.findViewById(R.id.lineairLaout_etap_4)
            txtViewDateHeure4 = itemView.findViewById(R.id.textView_date_et_heure_4)

            lnEtape5 = itemView.findViewById(R.id.lineairLaout_etap_5)
            txtViewDateHeure5 = itemView.findViewById(R.id.textView_date_et_heure_5)

            lnEtape5 = itemView.findViewById(R.id.lineairLaout_etap_6)
            txtViewDateHeure5 = itemView.findViewById(R.id.textView_date_et_heure_6)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TCResultViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_result,parent,false)
        return  TCResultViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TCResultViewHolder, position: Int) {
       /*        val tc = items[position]

        holder.txtView_tc1.text = tc.num_TC
        holder.txtView_plomb1.text = tc.num_plomb

        holder.txtView_tc2.text = tc.num_TCSecond
        holder.txtView_plomb2.text = tc.num_plomb_second

        //tc.num_TCSecond != null || tc.num_TCSecond != "" || tc.num_TCSecond != "null"

        if (holder.txtView_plomb2.text.isNullOrEmpty()){
            holder.imgVtc2.visibility = View.INVISIBLE
            holder.txtView_tc2.visibility = View.INVISIBLE
            holder.txtView_plomb2.visibility = View.INVISIBLE
        }*/
        val tc = items[position]

        holder.numBooking.text = tc.num_booking

        holder.numTc.text = tc.num_TC
        holder.numPlomb.text = tc.num_plomb

        holder.numTc2.text = tc.num_TCSecond
        holder.numPlomb2.text = tc.num_plomb_second

        if (holder.numTc2.text.isNullOrEmpty()){
            holder.lnTc2.visibility = View.GONE
        }

        if (holder.numPlomb.text.isNullOrEmpty()){
            holder.numPlomb.visibility = View.GONE
        }
        if (holder.numPlomb2.text.isNullOrEmpty()){
            holder.numPlomb2.visibility = View.GONE
        }


        /*when(tc.step_TC){
            0 -> {
                holder.etapeActuelDuTc.text = "Conteneur au port "
                //holder.dateActuelTc.text = "${tc.stepDateHeureReal!![stepvoyage].stepDateChiffre} "
                holder.dateActuelTc.text = "${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            1 -> {
                holder.etapeActuelDuTc.text = "Conteneur à l'usine "
                holder.dateActuelTc.text = "${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            2 -> {
                holder.etapeActuelDuTc.text = "Conteneur en chargement "
                holder.dateActuelTc.text = "${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            3 -> {
                holder.etapeActuelDuTc.text = "Conteneur à la douane "
                holder.dateActuelTc.text = "${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            4 -> {
                holder.etapeActuelDuTc.text = "Conteneur sortie de l'entrepot "
                holder.dateActuelTc.text = "${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            5 -> {
                holder.etapeActuelDuTc.text = "Conteneur plein arrivé au port "
                holder.dateActuelTc.text = "${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
            6 -> {
                holder.etapeActuelDuTc.text = "Transaction terminé "
                holder.dateActuelTc.text = "${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"
            }
        }*/
        holder.etapeActuelDuTc.text = "Conteneur enrégistré le ${tc.lesStepDateHour[tc.step_TC].stepDateLettre} à ${tc.lesStepDateHour[tc.step_TC].stepHeure}"

        showDate(tc.step_TC,holder,tc)
    }

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

}