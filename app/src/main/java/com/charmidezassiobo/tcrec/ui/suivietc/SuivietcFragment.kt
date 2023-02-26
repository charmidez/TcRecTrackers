package com.charmidezassiobo.tcrec.ui.suivietc

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.setup.TCAdapter
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBinding
import com.charmidezassiobo.tcrec.setup.RecyclerViewItemClickListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SuivietcFragment : Fragment() {

    private var _binding: FragmentSuivietcBinding? = null
    private val binding get() = _binding!!

    val db = Firebase.firestore
    val voyRef = db.collection("Voyage")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSuivietcBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val recyclerView_TC : RecyclerView = binding.recyclerViewSuivieTc
        var txtView_charging : TextView = binding.textViewCharging

        var progressBar_view : ProgressBar = binding.progressBarId

        var items_tc : MutableList<Tc> = mutableListOf()

        progressBar_view.setVisibility(View.VISIBLE)
        voyRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (documents != null){
                        val idtc_ok = document.getLong("step_TC")?.toInt()
                        val numtc_ok = document.data.get("num_TC").toString()
                        val num_booking_tc = document.data.get("num_Booking").toString()
                        val num_cam_ok = document.data.get("num_Camion").toString()
                        val step_tc_ok = document.getLong("step_TC")?.toInt()
                        val date_ok = document.data.get("Date").toString()
                        if ( idtc_ok != null){
                           if (step_tc_ok != null ){
                               items_tc.add(Tc( "$numtc_ok","$num_cam_ok","$num_booking_tc","","$date_ok", step_tc_ok))
                               recyclerView_TC.apply {
                                   recyclerView_TC.adapter = TCAdapter(items_tc,childFragmentManager)
                               }
                               txtView_charging.isVisible  = false
                               progressBar_view.setVisibility(View.GONE); // pour masquer la barre de progression
                           }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        recyclerView_TC.apply {
            recyclerView_TC.adapter = TCAdapter(items_tc, childFragmentManager)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}