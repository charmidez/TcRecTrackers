package com.charmidezassiobo.tcrec.ui.reglages

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charmidezassiobo.tcrec.databinding.FragmentReglagesBinding
import com.charmidezassiobo.tcrec.ui.BaseActivity


class ReglagesFragment : Fragment() {

    private var _binding: FragmentReglagesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentReglagesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonDeconection.setOnClickListener {
            showLogoutConfirmation()
        }


        return root
    }

    fun showLogoutConfirmation(){

        val mContext = binding.root.context

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmation")
        builder.setMessage("Voulez-vous quitter ? ")
        builder.setPositiveButton("Oui"){ dialogueInterface, i ->
            val editor = mContext.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
            editor.remove("is_authenticated")
            editor.apply()
            onDestroyView()
            val i = Intent(activity, BaseActivity::class.java)
            startActivity(i)
        }
        builder.setNegativeButton("Non"){dialogueInterface, i ->
            dialogueInterface.dismiss()
        }
        builder.setNeutralButton("Annuler"){dialogueInterface, i ->
            dialogueInterface.dismiss()
        }

        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}