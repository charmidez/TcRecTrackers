package com.charmidezassiobo.tcrec.ui.reglages

//import com.charmidezassiobo.tcrec.databinding.FragmentNotificationsBinding
import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.charmidezassiobo.tcrec.databinding.FragmentReglagesBinding


class ReglagesFragment : Fragment() {

    private var _binding: FragmentReglagesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val reglagesViewModel =
            ViewModelProvider(this).get(ReglagesViewModel::class.java)

        _binding = FragmentReglagesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}