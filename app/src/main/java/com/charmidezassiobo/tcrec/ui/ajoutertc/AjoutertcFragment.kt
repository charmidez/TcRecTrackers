package com.charmidezassiobo.tcrec.ui.ajoutertc

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.data.GetDataFromDB
import com.charmidezassiobo.tcrec.data.HeureStep
import com.charmidezassiobo.tcrec.databinding.FragmentAjoutertcBinding
import com.charmidezassiobo.tcrec.setup.AllFunctions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.time.LocalDate
import java.util.*
import java.sql.Date

class AjoutertcFragment : Fragment() {

    private var _binding: FragmentAjoutertcBinding? = null
    private val binding get() = _binding!!
    private var db = Firebase.firestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentAjoutertcBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val numBookingTc : TextInputEditText = binding.textInputBookingNum
        val numTCOff : TextInputEditText = binding.textInputTcNum
        val numCamion : TextInputEditText = binding.textInputCamionNum
        val descTC : TextInputEditText = binding.textInputDesc
        val phone : TextInputEditText = binding.textInputPhoneChauffeur
        val numTCSecondOff : TextInputEditText = binding.textInputTcNum2

        val numTCSecondLabel : TextInputLayout = binding.textFieldTcNum2
        val imgViewBtn : ImageView = binding.imageViewPlusTc
        val frameLayout_ajout_tc = binding.frameLayoutAjoutTc
        val textViewTC_label = binding.textFieldTcNum
        val textViewBooking_label = binding.textFieldBookingNum

        val connectivityManager = binding.root.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting

        val currentDate = LocalDate.now()

        var ajouterdate : String
        var step_tc : Int
        var num_plomb : String
        var num_plomb_2 : String

        var typeTransat = ""

        //var bookingList = ArrayList<String>()
        var bookingList = mutableListOf<String>()
        var spinner_ajout_tc = binding.spinnerAjoutTc
        var getData = GetDataFromDB()
        var items_tc = getData.itemListTc

        var bookingListRD : List<String> = listOf()

        imgViewBtn.isVisible = false

        val context : Context
        context = requireContext()

        val radioGrp : RadioGroup = binding.radioGroupOk
        radioGrp.setOnCheckedChangeListener { group, i ->
            val radioBtn = group.findViewById<RadioButton>(i)
            val selectOption = radioBtn.id
            when(selectOption){
                R.id.radioButton_Import_ajout -> {
                    typeTransat = "Import"
                    imgViewBtn.isVisible = false
                }
                R.id.radioButton_export_ajout -> {
                    typeTransat = "Export"
                    imgViewBtn.isVisible = true
                }
                R.id.radioButton_road_ajout -> {
                    typeTransat = "Export"
                    imgViewBtn.isVisible = true
                }
            }
        }

        imgViewBtn.setOnClickListener{

            numTCSecondLabel.isVisible = true
            imgViewBtn.isVisible = false
        }


        //Spinner
        getData.updateTc {
            bookingList = getData.listBooking
            //bookingList.distinct()
            //bookingListRD = bookingList.distinct().toList()
            val set: Set<String> =  bookingList.toHashSet()
            bookingList.clear()
            bookingList.addAll(set)
            val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, bookingList)
            spinner_ajout_tc.adapter  = adapter
        }
        bookingList.add("")
        spinner_ajout_tc.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = bookingList[position]
                numBookingTc.setText(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })

        //Prendre Les données du conteneur
        val butAjouter : Button = binding.ajouteTcButton

        var getDateReal : HeureStep
        val currentTimestamp = Timestamp(System.currentTimeMillis())
        val currentRealDate = Date()

        var heureRealStartTc = AllFunctions().miseEnPlaceHeure()
        val dateRealChiffre = AllFunctions().miseEnPlaceDate(true)
        val dateRealDate = AllFunctions().miseEnPlaceDate(false)


        getDateReal = HeureStep(dateRealChiffre,dateRealDate,heureRealStartTc)

        val lesStepHour = listOf(
            HeureStep(dateRealChiffre,dateRealDate,heureRealStartTc)
        )

        butAjouter.setOnClickListener{
/*
            val registerNewTc = hashMapOf(
                "numsTc" to "numsTc",
                "stepTC" to 4,
                "lesStepHour" to  lesDatesNew
            )
            db.collection("Voyagetest").document().set(registerNewTc)

            */

            if (isConnected){
                butAjouter.text = "Chargement..."
                butAjouter.isEnabled = false
                butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_not_selected))
                ajouterdate = "${currentDate.dayOfMonth}/${currentDate.monthValue}/${currentDate.year}"
                step_tc = 0
                num_plomb =""
                num_plomb_2 = ""

                val recup_numCamion  = numCamion.text.toString()
                val recup_numTc = numTCOff.text.toString()

                if (typeTransat != ""){
                    if (recup_numCamion != "" || recup_numTc != "" ){
                        val registerTc = hashMapOf(
                            "Date" to ajouterdate,
                            "num_Booking" to numBookingTc.text.toString(),
                            "num_TC" to numTCOff.text.toString(),
                            "num_TC_Second" to numTCSecondOff.text.toString(),
                            "num_Camion" to numCamion.text.toString(),
                            "step_TC" to step_tc,
                            "desc_TC" to descTC.text.toString(),
                            "num_plomb_TC" to num_plomb,
                            "num_plomb_TC_2" to num_plomb_2,
                            "phone_chauffeur_TC" to phone.text.toString(),
                            "import_export" to typeTransat,
                            "lesStepDateHour" to  lesStepHour
                        )
                        //db.collection("Voyage").document().set(registerTc)
                        db.collection("Voyagetest").document().set(registerTc)
                            .addOnSuccessListener {
                                val snack = Snackbar.make(binding.root,"Le conteneur ${numTCOff.text.toString()} a été bien enrégistré ce $ajouterdate",Snackbar.LENGTH_LONG)
                                snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                                snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.blue))
                                snack.show()
                                numBookingTc.text?.clear()
                                numTCOff.text?.clear()
                                numCamion.text?.clear()
                                descTC.text?.clear()
                                phone.text?.clear()
                                numTCSecondOff.text?.clear()
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

                    }
                    else if(recup_numCamion == "" || recup_numTc == "") {
                        val snack = Snackbar.make(binding.root,"Veuillez renseigner les informations du TC",Snackbar.LENGTH_LONG)
                        snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.gray2))
                        snack.show()
                        butAjouter.text = getText(R.string.but_addtc)
                        butAjouter.isEnabled = true
                        butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                    }
                }else if(typeTransat == ""){
                    val snack = Snackbar.make(binding.root,"Veuillez renseigner le type de transaction",Snackbar.LENGTH_LONG)
                    snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.gray2))
                    snack.show()
                    butAjouter.text = getText(R.string.but_addtc)
                    butAjouter.isEnabled = true
                    butAjouter.setBackground(resources.getDrawable(R.drawable.btn_drawable_red))
                }
            } else {
                // Pas de connexion Internet
                val snack = Snackbar.make(binding.frameLayoutAjoutTc,"Veuillez vous connecter à internet", Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(ContextCompat.getColor(root.context, R.color.gray2))
                snack.show()
            }

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}