package com.charmidezassiobo.tcrec.setup.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.charmidezassiobo.tcrec.ui.suivietc.tabfragment.TabSeaTrackingFragment
import com.charmidezassiobo.tcrec.ui.suivietc.tabfragment.TabImportTrackingFragment
import com.charmidezassiobo.tcrec.ui.suivietc.tabfragment.TabRoadTrackingFragment

internal class TabAdapter(var context : Context, fm : FragmentManager, var totalTabs : Int) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> TabSeaTrackingFragment()
            1 -> TabImportTrackingFragment()
            2 -> TabRoadTrackingFragment()
            else -> getItem(position)
        }
    }
}