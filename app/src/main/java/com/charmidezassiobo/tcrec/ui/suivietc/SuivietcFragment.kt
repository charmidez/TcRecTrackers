package com.charmidezassiobo.tcrec.ui.suivietc

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.TCAdapter
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBinding
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.currentCoroutineContext
import java.util.*

class SuivietcFragment : Fragment() {

    private var _binding: FragmentSuivietcBinding? = null
    private val binding get() = _binding!!

    val db = Firebase.firestore
    val voyRef = db.collection("Voyage")

    // var numBookingTc : TextInputEditText = binding.textInputBookingNum


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSuivietcBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView_TC : RecyclerView = binding.recyclerViewSuivieTc
        var txtView_charging : TextView = binding.textViewCharging

        //val voyId = FirebaseAuthCredentialsProvider

        fun recupDate() : String{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val date = "$day/${month + 1}/$year"

            return date
        }

        var items_tc : MutableList<Tc> = mutableListOf()

        /*items_tc = mutableListOf(
            Tc ( "MEDU1807661 20 DV", "TG-2253-AX","MEDU1807661 20 DV","1208",recupDate(),0),
        )*/

        //Récupération des données sur le net.
        voyRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    //Log.d(TAG, "${document.id} => ${document.data}")
                    //Log.d("Le numéro du TC Seul", "numtc_ok" )


                    if (documents != null){
                        val numtc_ok = document.data.get("num_TC").toString()
                        val num_cam_ok = document.data.get("num_Camion").toString()
                        val step_tc_ok = document.getLong("step_TC")?.toInt()
                        val date_ok = document.data.get("Date").toString()
                        //val step_ok = document.data.get("step_TC").toString().toInt()
                        items_tc.add(Tc("$numtc_ok","$num_cam_ok","$","","$date_ok",step_tc_ok))
                        recyclerView_TC.apply {
                            recyclerView_TC.adapter = TCAdapter(items_tc)
                        }
                        txtView_charging.isVisible  = false
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


        recyclerView_TC.apply {
            recyclerView_TC.adapter = TCAdapter(items_tc)
        }
        //txtView_charging.text = ""

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}