package com.charmidezassiobo.tcrec.setup.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.dataclass.RecUser


class ListUserAdapter (var context : Context, var items : MutableList<RecUser>) : RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>(){

    inner class ListUserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var userType : TextView
        var userName : TextView
        var userPseudo : TextView

        init {
            userType = itemView.findViewById(R.id.textView_userType_item)
            userName = itemView.findViewById(R.id.txtView_userName_item)
            userPseudo = itemView.findViewById(R.id.textView_userPseudo_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)

        return ListUserViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val userPosition = items[position]
        when(userPosition.typeUser){
            true -> {
                holder.userType.text = "Administrateur"
            }
            else -> {
                holder.userType.text = "Moderateur"
            }
        }
        holder.userName.text = userPosition.nameUser
        holder.userPseudo.text = userPosition.pseudoUser
    }
}