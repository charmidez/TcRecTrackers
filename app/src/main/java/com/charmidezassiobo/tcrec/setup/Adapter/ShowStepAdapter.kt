package com.charmidezassiobo.tcrec.setup.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R

class ShowStepAdapter(var context : Context, var items : ArrayList<String>) : RecyclerView.Adapter<ShowStepAdapter.ShowStepViewHolder>()  {

    inner class ShowStepViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        var imgViewStep : ImageView
        var txtViewStepDesc : TextView

        init {
            imgViewStep = itemView.findViewById(R.id.imgView_validate_or_not_icone)
            txtViewStepDesc = itemView.findViewById(R.id.txtView_step_desc_validate_or_not)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowStepViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_step_date_hour, parent, false)

        return ShowStepViewHolder(itemView)
    }

    override fun getItemCount()  = items.size

    override fun onBindViewHolder(holder: ShowStepViewHolder, position: Int) {
        val showDatePosition = items[position]
        holder.txtViewStepDesc.text = showDatePosition
        //Dire que seul le dernier élement de l'image doit être un camion
        when(position){
            items.lastIndex -> {
                //holder.imgViewStep.setImageResource(R.drawable.ic_cam_road)
                holder.imgViewStep.setImageDrawable(getDrawable(holder.itemView.context,R.drawable.ic_cam_road))
                holder.txtViewStepDesc.setBackground(getDrawable(holder.itemView.context,R.drawable.rounded_blue_corner_view))
            }
        }
    }
}