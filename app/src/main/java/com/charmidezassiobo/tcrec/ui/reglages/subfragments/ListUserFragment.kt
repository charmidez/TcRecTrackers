package com.charmidezassiobo.tcrec.ui.reglages.subfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.databinding.FragmentListUserBinding
import com.charmidezassiobo.tcrec.setup.Adapter.ListUserAdapter
import com.charmidezassiobo.tcrec.setup.dataclass.RecUser

class ListUserFragment : Fragment() {

    private var _binding : FragmentListUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        val root : View = binding.root
        val mContext = binding.root.context
        val navController = findNavController()

        val btnBack = binding.btnBackToPreviousFragment
        val recyclerViewListUser = binding.recyclerViewListUser


        btnBack.setOnClickListener {
            navController.popBackStack(R.id.navigation_reglages, false)
        }

        var itemRecUser = mutableListOf(
            RecUser(true, "Charmidez", "recCharmidez", "motDePasse"),
            RecUser(false, "Koffi", "recKoffi", "motDePasse"),
            RecUser(false, "Richard", "recRichard", "motDePasse"),
        )

        recyclerViewListUser.adapter = ListUserAdapter(mContext, itemRecUser)

        return root
    }
}