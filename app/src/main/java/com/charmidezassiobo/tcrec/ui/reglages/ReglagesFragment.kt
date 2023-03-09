package com.charmidezassiobo.tcrec.ui.reglages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.baoyachi.stepview.HorizontalStepView
import com.baoyachi.stepview.bean.StepBean
import com.charmidezassiobo.tcrec.databinding.FragmentReglagesBinding


class ReglagesFragment : Fragment() {

    private var _binding: FragmentReglagesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentReglagesBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /*
    fun bayo_view_init(){
        bayoStepView = binding.stepViewBaoya
        stepsBeanList.add(stepBean0)
        stepsBeanList.add(stepBean1)
        stepsBeanList.add(stepBean2)
        stepsBeanList.add(stepBean3)
        stepsBeanList.add(stepBean4)

        bayoStepView
            .setStepViewTexts(stepsBeanList)
            .setTextSize(8)
            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(context!!, com.charmidezassiobo.tcrec.R.drawable.ic_cam))
            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(context!!, com.charmidezassiobo.tcrec.R.drawable.next_step))
            .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(context!!, com.charmidezassiobo.tcrec.R.color.blue))
            .setStepViewUnComplectedTextColor(ContextCompat.getColor(context!!, com.charmidezassiobo.tcrec.R.color.blue))
            .setStepViewComplectedTextColor(ContextCompat.getColor(context!!, com.charmidezassiobo.tcrec.R.color.blue))
            .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(context!!, com.charmidezassiobo.tcrec.R.color.blue))
            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(context!!, com.charmidezassiobo.tcrec.R.drawable.check))
    }

    fun step_change(){
        when(etape){
            0 -> {
                stepsBeanList= ArrayList()
                stepBean0 = StepBean("Port", 0)
                stepBean1 = StepBean("Usine", -1)
                stepBean2 = StepBean("Chargement", -1)
                stepBean3 = StepBean("Sortie", -1)
                stepBean4 = StepBean("Arrivée Port", -1)
                bayo_view_init()

            }
            1 -> {
                stepsBeanList= ArrayList()
                stepBean0 = StepBean("Port", 1)
                stepBean1 = StepBean("Usine", 0)
                stepBean2 = StepBean("Chargement", -1)
                stepBean3 = StepBean("Sortie", -1)
                stepBean4 = StepBean("Arrivée Port", -1)
                bayo_view_init()

            }
            2 -> {
                stepsBeanList= ArrayList()
                stepBean0 = StepBean("Port", 1)
                stepBean1 = StepBean("Usine", 1)
                stepBean2 = StepBean("Chargement", 0)
                stepBean3 = StepBean("Sortie", -1)
                stepBean4 = StepBean("Arrivée Port", -1)
                bayo_view_init()

            }
            3 -> {
                stepsBeanList= ArrayList()
                stepBean0 = StepBean("Port", 1)
                stepBean1 = StepBean("Usine", 1)
                stepBean2 = StepBean("Chargement", 1)
                stepBean3 = StepBean("Sortie", 0)
                stepBean4 = StepBean("Arrivée Port", -1)
                bayo_view_init()

            }
            4 -> {
                stepsBeanList= ArrayList()
                stepBean0 = StepBean("Port", 1)
                stepBean1 = StepBean("Usine", 1)
                stepBean2 = StepBean("Chargement", 1)
                stepBean3 = StepBean("Sortie", 1)
                stepBean4 = StepBean("Arrivée Port", 0)
                bayo_view_init()

            }
            5 -> {
                stepsBeanList= ArrayList()
                stepBean0 = StepBean("Port", 1)
                stepBean1 = StepBean("Usine", 1)
                stepBean2 = StepBean("Chargement", 1)
                stepBean3 = StepBean("Sortie", 1)
                stepBean4 = StepBean("Arrivée Port", 1)
                bayo_view_init()
                Toast.makeText(context!!,"Voyage Terminé",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clickBut(){
        var butBayo : Button = binding.buttonBaoya
        butBayo.setOnClickListener {
            if (etape<5){
                etape=etape+1
                step_change()
            }
        }
    }
    */
}