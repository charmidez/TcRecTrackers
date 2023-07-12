package com.charmidezassiobo.tcrec.interfaces

import androidx.navigation.NavController

interface RecyclerViewClickItemInterface {
    fun onItemClick( position : Int)
    fun onLongClickListener(position: Int)
}