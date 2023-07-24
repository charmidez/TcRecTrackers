package com.charmidezassiobo.tcrec.ui.suivietc

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBinding
import com.charmidezassiobo.tcrec.setup.Adapter.TabAdapter
import com.charmidezassiobo.tcrec.ui.BaseActivity
import com.google.android.material.tabs.TabLayout

class SuivietcFragment : Fragment(), OnBackPressedDispatcherOwner{

    override fun getOnBackPressedDispatcher(): OnBackPressedDispatcher {
        return requireActivity().onBackPressedDispatcher
    }

    private var _binding: FragmentSuivietcBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSuivietcBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mContext = binding.root.context
        var navController = findNavController()


        /***********Tab*********/
        var tabLayoutSuivieTc = binding.tabLayoutSuivieTc
        var viewPagerSuivieTc = binding.viewPagerSuivieTc
        var titreSuivieTc = binding.titreSuivieTc
        var imgVoirBooking = binding.imageViewVoirBooking
        var imgVoirHawb = binding.imageViewVoirHawb
        var imgVoirImmatriculation = binding.imageViewVoirImmatriculation

        val tabAdapter = TabAdapter(mContext, childFragmentManager, tabLayoutSuivieTc.tabCount)
        viewPagerSuivieTc.adapter = tabAdapter
        viewPagerSuivieTc.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tabLayoutSuivieTc).apply {
                titreSuivieTc.text = getText(R.string.sea_tracking)
                imgVoirBooking.visibility = View.VISIBLE
            }
        )
        tabLayoutSuivieTc.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPagerSuivieTc.currentItem = tab!!.position
                when (viewPagerSuivieTc.currentItem) {
                    0 -> {
                        titreSuivieTc.text = getText(R.string.sea_tracking)
                        imgVoirBooking.visibility = View.VISIBLE
                        imgVoirHawb.visibility = View.GONE
                        imgVoirImmatriculation.visibility = View.GONE
                    }

                    1 -> {
                        titreSuivieTc.text = getText(R.string.air_tracking)
                        imgVoirBooking.visibility = View.GONE
                        imgVoirHawb.visibility = View.VISIBLE
                        imgVoirImmatriculation.visibility = View.GONE
                    }

                    2 -> {
                        titreSuivieTc.text = getText(R.string.road_tracking)
                        imgVoirBooking.visibility = View.GONE
                        imgVoirHawb.visibility = View.GONE
                        imgVoirImmatriculation.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onStop()
                val intent = Intent(mContext, BaseActivity::class.java)
                startActivity(intent)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        //voirListDesTc(imgView_list_tc_bk)
        imgVoirBooking.setOnClickListener {
            navController.navigate(R.id.action_navigation_suivietc_to_suivietcBookingSousFragment)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}