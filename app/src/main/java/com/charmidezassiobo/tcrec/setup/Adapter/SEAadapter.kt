package com.charmidezassiobo.tcrec.setup.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.dataclass.Sea
import com.charmidezassiobo.tcrec.interfaces.RecyclerViewClickItemInterface

class SEAadapter (var items : List<Sea>, val listener : RecyclerViewClickItemInterface)  : RecyclerView.Adapter<SEAadapter.SEAViewHolder>() {

    inner class SEAViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SEAViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tc, parent, false)
        return SEAViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SEAViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}