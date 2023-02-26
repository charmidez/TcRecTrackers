package com.charmidezassiobo.tcrec.setup

import com.charmidezassiobo.tcrec.data.Tc

interface RecyclerViewItemClickListener {
    fun onItemClick(position: Int)
    fun setOnItemClickListener(listener: RecyclerViewItemClickListener)
    fun onItemClicked(tc: Tc)
}