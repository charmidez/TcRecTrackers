package com.charmidezassiobo.tcrec.ui.suivietc

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.HeureStep
import com.charmidezassiobo.tcrec.setup.TCAdapter
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.charmidezassiobo.tcrec.setup.AllVariables
import com.charmidezassiobo.tcrec.setup.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.setup.TabAdapter
import com.charmidezassiobo.tcrec.ui.BaseActivity
import com.charmidezassiobo.tcrec.ui.suivietc.subfragments.SuivietcSousFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.io.Serializable
import java.util.Locale

class SuivietcFragment : Fragment(), OnBackPressedDispatcherOwner/*, RecyclerViewClickItemInterface*/ {

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

        val tabAdapter = TabAdapter(mContext, childFragmentManager, tabLayoutSuivieTc.tabCount)
        viewPagerSuivieTc.adapter = tabAdapter
        viewPagerSuivieTc.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tabLayoutSuivieTc).apply {
                titreSuivieTc.text = getText(R.string.exp_tracking)
                imgVoirBooking.visibility = View.VISIBLE
            }
        )
        tabLayoutSuivieTc.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPagerSuivieTc.currentItem = tab!!.position
                when (viewPagerSuivieTc.currentItem) {
                    0 -> {
                        titreSuivieTc.text = getText(R.string.exp_tracking)
                        imgVoirBooking.visibility = View.VISIBLE
                    }

                    1 -> {
                        titreSuivieTc.text = getText(R.string.imp_tracking)
                        imgVoirBooking.visibility = View.GONE
                    }

                    2 -> {
                        titreSuivieTc.text = getText(R.string.road_track)
                        imgVoirBooking.visibility = View.GONE
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