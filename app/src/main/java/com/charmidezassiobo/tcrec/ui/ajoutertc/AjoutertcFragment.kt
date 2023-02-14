package com.charmidezassiobo.tcrec.ui.ajoutertc

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentAjoutertcBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*

class AjoutertcFragment : Fragment() {

    private var _binding: FragmentAjoutertcBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var db = Firebase.firestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAjoutertcBinding.inflate(inflater, container, false)
        val root: View = binding.root

       //Numéro Booking
        var numBookingTc : TextInputEditText = binding.textInputBookingNum
        var numbookingtc = ""

        //Numéro Tc
        var numTCOff : TextInputEditText = binding.textInputTcNum
        var numtcoff =""

        //Numéro Camion
        var numCamion : TextInputEditText = binding.textInputCamionNum
        var numcam = ""

        //Desc Recup
        var descTC : TextInputEditText = binding.textInputDesc
        var desctc = ""

        //La date récupérée
        val currentDate = LocalDate.now()
        var ajouterdate : String

        //Etape
        var step_tc : Int


        //Prendre Les données du conteneur
        val butAjouter : Button = binding.ajouteTcButton
        butAjouter.setOnClickListener{
            //numbookingtc = numBookingTc.text.toString()
            butAjouter.text = "Chargement..."
            butAjouter.isEnabled = false
            //numtcoff = numTCOff.text.toString()
            //numcam = numCamion.text.toString()
            ajouterdate = "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"
            //desctc = descTC.text.toString()
            step_tc = 0

            val registerTc = hashMapOf(
                "Date" to ajouterdate,
                "num_Booking" to numBookingTc.text.toString(),
                "num_TC" to numTCOff.text.toString(),
                "num_Camion" to numCamion.text.toString(),
                "step_TC" to step_tc,
                "desc_TC" to descTC.text.toString()

            )

            //val voyageTcId = FirebaseFirestore.getInstance()

            db.collection("Voyage").document().set(registerTc)
                .addOnSuccessListener {
                    Toast.makeText(context, "Le conteneur $numtcoff a été bien enrégistré ce $ajouterdate", Toast.LENGTH_SHORT).show()
                    numBookingTc.text?.clear()
                    numTCOff.text?.clear()
                    numCamion.text?.clear()
                    descTC.text?.clear()
                    butAjouter.text = getText(R.string.but_addtc)
                    butAjouter.isEnabled = true
                }
                .addOnFailureListener{
                    Toast.makeText(context, "Le conteneur $numtcoff na pas pu être enrégistré", Toast.LENGTH_SHORT).show()
                    butAjouter.text = getText(R.string.but_addtc)
                    butAjouter.isEnabled = true
                }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun recupDate() : String{
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val date = "$day/${month + 1}/$year"

        return date
    }


}