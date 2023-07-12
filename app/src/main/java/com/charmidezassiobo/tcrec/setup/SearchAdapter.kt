package com.charmidezassiobo.tcrec.setup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.interfaces.RecyclerViewClickItemInterface

class SearchAdapter(var context : Context, var items : List<String>, val listener : RecyclerViewClickItemInterface) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        var searchWord : TextView

        init {
            searchWord = itemView.findViewById(R.id.txtView_searchWord_item)

            //quand le mot est appuyé, recommencer la recherche
            itemView.setOnClickListener {
                val position = adapterPosition
                listener.onItemClick(position)
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_search_words, parent, false)
        return SearchViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchWordItemPosition = items[position]
        holder.searchWord.text = searchWordItemPosition
    }

}