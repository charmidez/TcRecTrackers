package com.charmidezassiobo.tcrec.ui.adminlogin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentAdminloginBinding
import com.charmidezassiobo.tcrec.ui.MainActivity
import com.google.android.material.snackbar.Snackbar


class AdminloginFragment : Fragment(), OnBackPressedDispatcherOwner {

    private  var _binding : FragmentAdminloginBinding? = null
    private val binding get() = _binding!!

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAdminloginBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context
        var navController = findNavController()

        var username = binding.editTextUsername
        val password = binding.editTextPassword
        var btnConnection = binding.loginButton

        sharedPreferences = mContext.getSharedPreferences("app_state", Context.MODE_PRIVATE)

        binding.btnBackToPreviousFragment.setOnClickListener {
            navController.popBackStack(R.id.clientHomeFragment, false)
        }

        btnConnection.setOnClickListener {

            var recupUsername = username.text.toString()
            var recupPassword = password.text.toString()

            if (username.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                val snack = Snackbar.make(binding.root,"Veuillez entrer votre nom d'utilisateur et mot de passe", Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(ContextCompat.getColor(mContext, R.color.gray2))
                snack.show()
            } else if (recupUsername == "recModerate" && recupPassword == "recModerate") {

                //enregistement dans les shared préferences le booleean isAuthentica
                val editor = sharedPreferences.edit()
                editor.putBoolean("is_authenticated",true).apply()

                username.setText("")
                password.setText("")

                val i = Intent(mContext, MainActivity::class.java)
                startActivity(i)
                onDestroyView()
            }
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                onStop()
                navController.popBackStack(R.id.clientHomeFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return root
    }

    override fun getOnBackPressedDispatcher(): OnBackPressedDispatcher {
        return requireActivity().onBackPressedDispatcher
    }
}