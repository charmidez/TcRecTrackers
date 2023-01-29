package com.charmidezassiobo.tcrec.ui.ajoutertc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.charmidezassiobo.tcrec.databinding.FragmentAjoutertcBinding
import com.google.android.material.textfield.TextInputEditText

class AjoutertcFragment : Fragment() {

    private var _binding: FragmentAjoutertcBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ajoutertcViewModel =
            ViewModelProvider(this).get(AjoutertcViewModel::class.java)

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


        //Prendre Les données du conteneur
        val butAjouter : Button = binding.ajouteTcButton
        butAjouter.setOnClickListener{
            numbookingtc = numBookingTc.text.toString()
            numtcoff = numTCOff.text.toString()
            numcam = numCamion.text.toString()

            //Toast.makeText(context, "Conteneur $numtc Ajouté. Consulter  'Suivie TC ' pour suivre son parcour", Toast.LENGTH_SHORT).show()
            Toast.makeText(context, "Le conteneur $numtcoff a été bien enrégistré", Toast.LENGTH_SHORT).show()
            numBookingTc.setText("")
            numTCOff.setText("")
            numCamion.setText("")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}