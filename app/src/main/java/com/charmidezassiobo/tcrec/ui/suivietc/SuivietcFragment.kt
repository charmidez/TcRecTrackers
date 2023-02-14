package com.charmidezassiobo.tcrec.ui.suivietc

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.TCAdapter
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

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

        //Récupération des données sur le net.
        voyRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


        fun recupDate() : String{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
             val date = "$day/${month + 1}/$year"

            return date
        }

        val items_tc = listOf(

            Tc ( "MEDU1807661 20 DV", "TG-2253-AX","MEDU1807661 20 DV","1208",recupDate()),
            Tc ( "MSDU2941187 20 DV", "TG-2835-X","MEDU1807661 20 DV","1208",recupDate()),
            Tc ( "MSCU6870920 20 DV", "TG-6525-AI","MEDU1807661 20 DV","1208",recupDate()),
            Tc ( "GLDU3457516 20 DV", "TG-2253-AX","MEDU1807661 20 DV","1208",recupDate()),
            Tc ( "MSDU1807661 20 DV", "TG-0053-AM","MEDU1807661 20 DV","1208",recupDate()),
            Tc ( "MSMU1805660 20 DV", "TG-2113-AP","MEDU1807661 20 DV","1208",recupDate())

        )

        recyclerView_TC.apply {
            //recyclerView_TC.layoutManager = LinearLayoutManager(_binding)
            recyclerView_TC.adapter = TCAdapter(items_tc)
        }

        /*val textView: TextView = binding.textHome
        suivietcViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}