package com.charmidezassiobo.tcrec.ui.reglages

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentReglagesBinding
import com.charmidezassiobo.tcrec.setup.functions.AllFunctions
import com.charmidezassiobo.tcrec.ui.BaseActivity
import com.charmidezassiobo.tcrec.ui.reglages.bottomfragments.AddUserBottomSheetFragment
import com.charmidezassiobo.tcrec.ui.reglages.bottomfragments.ChangeLangageBottomSheetFragment
import com.google.firebase.firestore.FirebaseFirestore


class ReglagesFragment : Fragment() {

    private var _binding: FragmentReglagesBinding? = null
    private val binding get() = _binding!!

    var allFun = AllFunctions()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentReglagesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mContext = binding.root.context
        val navController = findNavController()

        var btnDeconnexion = binding.buttonDeconection
        var btnAddUser = binding.btnAddUserReglage
        var btnListUser = binding.btnListUser
        var imgView = binding.imgViewChoiceLanguageFlags


        retrieveSingleStringFromREC(
            documentId = "CNNzyzjHspk1RLFopI9B",
            fieldName = "textelire",
            onSuccess = { fieldValue ->
                println("Field value: $fieldValue")
                Log.d("ONSUCCES","$fieldValue")
            },
            onFailure = { exception ->
                println("Error: ${exception.message}")
            }
        )


        btnAddUser.setOnClickListener {
            val addUserBottomSheetFragment = AddUserBottomSheetFragment()
            addUserBottomSheetFragment.show(childFragmentManager, addUserBottomSheetFragment.tag)
        }

       btnDeconnexion.setOnClickListener {
            showLogoutConfirmation()
        }

        btnListUser.setOnClickListener {
            navController.navigate(R.id.action_navigation_reglages_to_listUserFragment)
        }

        imgView.setOnClickListener {
            val changeLangageBottomSheetFragment = ChangeLangageBottomSheetFragment()
            changeLangageBottomSheetFragment.show(childFragmentManager, changeLangageBottomSheetFragment.tag)
        }

        return root
    }

    fun showLogoutConfirmation(){

        val mContext = binding.root.context

        val builder = AlertDialog.Builder(requireContext())

        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)
        builder.setView(dialogView)
        val titleDialog = dialogView.findViewById<TextView>(R.id.txtView_titleAlertDialog)
        val messageDialog = dialogView.findViewById<TextView>(R.id.txtView_messageAlertDialog)
        val btnOui = dialogView.findViewById<Button>(R.id.buttonOui)
        val btnNon = dialogView.findViewById<Button>(R.id.buttonNon)
        titleDialog.text = "Confirmation"
        messageDialog.text = "Voulez-vous quitter"
        val alertDialog  = builder.create()
        btnOui.setOnClickListener {
            val editor = mContext.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
            editor.remove("is_authenticated")
            editor.apply()
            onDestroyView()
            val i = Intent(activity, BaseActivity::class.java)
            startActivity(i)
        }
        btnNon.setOnClickListener {
            dialogView.visibility = View.GONE
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    fun outAndRemovePreferences(){
        val editor = binding.root.context.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
        editor.remove("is_authenticated")
        editor.apply()
        onDestroyView()
        val i = Intent(activity, BaseActivity::class.java)
        startActivity(i)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun retrieveSingleStringFromREC(documentId: String, fieldName: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("REC").document(documentId)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val fieldValue = document.getString(fieldName)
                    if (fieldValue != null) {
                        onSuccess(fieldValue)
                    } else {
                        onFailure(Exception("Field $fieldName does not exist in document $documentId"))
                    }
                } else {
                    onFailure(Exception("Document $documentId does not exist in REC collection"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }



}