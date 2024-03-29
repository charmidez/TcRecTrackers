package com.charmidezassiobo.tcrec.setup.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.data.Sea
import com.charmidezassiobo.tcrec.ui.suivietc.subfragments.SuivietcBookingSousFragment

class TCBookingAdapter(var context: SuivietcBookingSousFragment, var items: List<Sea> )
    : RecyclerView.Adapter<TCBookingAdapter.TCBookingViewHolder>(){

    class TCBookingViewHolder(itemView : View)  : RecyclerView.ViewHolder(itemView) {
        var imgVtc2 : ImageView
        var txtView_tc1 : TextView
        var txtView_tc2 : TextView
        var txtView_plomb1 : TextView
        var txtView_plomb2 : TextView

        init {
            imgVtc2 = itemView.findViewById(R.id.imgView_numtc2)
            txtView_tc1 = itemView.findViewById(R.id.textViewTCNum_item_1)
            txtView_tc2 = itemView.findViewById(R.id.textViewTCNum_item_2)
            txtView_plomb1 = itemView.findViewById(R.id.num_plomb_tc_1)
            txtView_plomb2 = itemView.findViewById(R.id.num_plomb_tc_2)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TCBookingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tc_booking,parent, false)
        return TCBookingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TCBookingViewHolder, position: Int) {
        val tc = items[position]

        holder.txtView_tc1.text = tc.numTc1
        holder.txtView_plomb1.text = tc.numPlomb1

        holder.txtView_tc2.text = tc.numTc2
        holder.txtView_plomb2.text = tc.numPlomb2

        if (holder.txtView_plomb2.text.isNullOrEmpty()){
            holder.txtView_plomb2.visibility = View.INVISIBLE
        }

        if (holder.txtView_plomb1.text.isNullOrEmpty()){
            holder.txtView_plomb1.visibility = View.INVISIBLE
        }

    }

    override fun getItemCount() = items.size

}