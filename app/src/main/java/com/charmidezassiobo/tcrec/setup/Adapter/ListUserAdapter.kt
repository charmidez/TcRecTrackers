package com.charmidezassiobo.tcrec.setup.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.data.RecUser


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
/*hrome/deb stable InRelease
Atteint :7 https://repo.protonvpn.com/debihrome/deb stable InRelease
Atteint :7 https://repo.protonvpn.com/debihrome/deb stable InRelease
Atteint :7 https://repo.protonvpn.com/debi*/
    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val userPosition = items[position]
        when(userPosition.typeUser){
            true -> {
                holder.userType.text = "Administrateur"
            }
            else -> {
                holder.userType.text = "Moderateur"
                holder.userType.setBackground(
                    AppCompatResources.getDrawable(
                        holder.itemView.context,
                        R.drawable.btn_bleu_drawable_call
                    )
                )
            }
        }
        holder.userName.text = userPosition.nameUser
        holder.userPseudo.text = userPosition.pseudoUser
    }
}