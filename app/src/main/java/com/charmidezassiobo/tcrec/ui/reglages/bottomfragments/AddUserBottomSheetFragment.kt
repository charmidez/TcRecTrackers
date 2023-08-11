package com.charmidezassiobo.tcrec.ui.reglages.bottomfragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.BottomSheetAddUserBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.StringBuilder
import kotlin.random.Random

class AddUserBottomSheetFragment  : BottomSheetDialogFragment() {
    private var _binding : BottomSheetAddUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetAddUserBinding.inflate(inflater, container, false)
        val root : View = binding.root
        var mContext : Context

        var radioGrp : RadioGroup = binding.radioGroupUser
        var nameUserEditView : EditText = binding.textInputUserName
        var btnAjoutUser = binding.btnAddUser

        //result view
        var lnResult = binding.lnUserdataCeate

        var typeUserAdminOrModerate = false
        var nameUser : String
        var pseudoUser : String
        var passwordUser : String

        /*****************************************Entrée des données******************************/
        btnAjoutUser.setOnClickListener {

            //Récup Radio Group Admin/Moderateur
            radioGrp.setOnCheckedChangeListener { group, i ->
                val radioBtn = group.findViewById<RadioButton>(i)
                val selectOption = radioBtn.id
                when(selectOption){
                    R.id.radioButton_admin -> {
                        typeUserAdminOrModerate = true
                    }
                    R.id.radioButton_moderate -> {
                        typeUserAdminOrModerate =false
                    }
                }
            }

            //Récup nom et prénom
            nameUser = nameUserEditView.text.toString()

            //faire le pseudo
            pseudoUser = getPseudo(typeUserAdminOrModerate, nameUser)

            //mot de passe
            passwordUser = "MotDePasse_rec"

            

            Log.d("lnresult", "${typeUserAdminOrModerate.toString()} + $nameUser +  $pseudoUser + $passwordUser ")
        }


        return root
    }


    fun getPseudo(typeUser : Boolean, nameUser : String) : String{
        //utiliser un string builder
        var pseudo =""
        var recupNameUser = " ${nameUser[0]}${nameUser[1]}${nameUser[2]}"
        when(typeUser){
            true -> {
                pseudo = "rec_${recupNameUser}_admin"
            }
            false -> {
                pseudo = "rec_${recupNameUser}_mod"
            }
        }
        return pseudo
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
