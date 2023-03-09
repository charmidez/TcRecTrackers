package com.charmidezassiobo.tcrec.ui.ajoutertc

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentAjoutertcBinding
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*
import kotlin.time.Duration.Companion.days

class AjoutertcFragment : Fragment() {

    private var _binding: FragmentAjoutertcBinding? = null
    private val binding get() = _binding!!
    private var db = Firebase.firestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentAjoutertcBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var numBookingTc : TextInputEditText = binding.textInputBookingNum
        var numTCOff : TextInputEditText = binding.textInputTcNum
        var numCamion : TextInputEditText = binding.textInputCamionNum
        var descTC : TextInputEditText = binding.textInputDesc
        val currentDate = LocalDate.now()
        //var ajouterdate : Date
        var ajouterdate : String
        var step_tc : Int

        //Prendre Les données du conteneur
        val butAjouter : Button = binding.ajouteTcButton



        butAjouter.setOnClickListener{
            butAjouter.text = "Chargement..."
            butAjouter.isEnabled = false
            butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_not_selected))
            ajouterdate = "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"
            step_tc = 0

            if (numBookingTc !=null ){

            val registerTc = hashMapOf(
                "Date" to ajouterdate,
                "num_Booking" to numBookingTc.text.toString(),
                "num_TC" to numTCOff.text.toString(),
                "num_Camion" to numCamion.text.toString(),
                "step_TC" to step_tc,
                "desc_TC" to descTC.text.toString()
            )

            db.collection("Voyage").document().set(registerTc)
                .addOnSuccessListener {
                    val snack = Snackbar.make(binding.root,"Le conteneur ${numTCOff.text.toString()} a été bien enrégistré ce $ajouterdate",Snackbar.LENGTH_LONG)
                    snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.blue))
                    snack.show()
                    numBookingTc.text?.clear()
                    numTCOff.text?.clear()
                    numCamion.text?.clear()
                    descTC.text?.clear()
                    butAjouter.text = getText(R.string.but_addtc)
                    butAjouter.isEnabled = true
                    butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                }
                .addOnFailureListener{
                    //Toast.makeText(context, "Le conteneur ${numTCOff.text.toString()} na pas pu être enrégistré", Toast.LENGTH_SHORT).show()
                    val snack = Snackbar.make(binding.root,"Le conteneur ${numTCOff.text.toString()} na pas pu être enrégistré",Snackbar.LENGTH_LONG)
                    snack.show()
                    butAjouter.text = getText(R.string.but_addtc)
                    butAjouter.isEnabled = true
                    butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                }
            } else {
                val snack = Snackbar.make(binding.root,"Veuillez renseigner les informations",Snackbar.LENGTH_LONG)
                snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.gray2))
                snack.show()
                butAjouter.text = getText(R.string.but_addtc)
                butAjouter.isEnabled = true
                butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}