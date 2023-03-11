package com.charmidezassiobo.tcrec.ui.suivietc

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.TCAdapter
import com.charmidezassiobo.tcrec.data.Tc
import com.charmidezassiobo.tcrec.databinding.FragmentSuivietcBinding
import com.charmidezassiobo.tcrec.setup.RecyclerViewItemClickListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SuivietcFragment : Fragment() {

    private var _binding: FragmentSuivietcBinding? = null
    private val binding get() = _binding!!

    val db = Firebase.firestore
    val voyRef = db.collection("Voyage")

    val datadb = FirebaseFirestore.getInstance()
    val docRef = datadb.collection("Voyage")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentSuivietcBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView_TC : RecyclerView = binding.recyclerViewSuivieTc
        val refresh : SwipeRefreshLayout = binding.swippRefreshLayout

        var items_tc : MutableList<Tc> = mutableListOf()
        fun removedItem(v : ViewHolder){
            items_tc.removeAt(v.adapterPosition)
            recyclerView_TC.removeView(v.itemView)
            recyclerView_TC.adapter!!.notifyItemRemoved(v.adapterPosition)
            Log.d("Recup data",items_tc.lastIndex.toString())

            //Suppression dans la base de donnée
            //v.adapterPosition >= 0 && v.adapterPosition < items_tc.size
            if (true) {
                val tc = items_tc.get(v.adapterPosition)
                //val tc = items_tc.

                val db = FirebaseFirestore.getInstance()
                val query = db.collection("Voyage")
                    .whereEqualTo("num_TC", tc.num_TC)
                    .whereEqualTo("num_Camion", tc.num_Camion)
                query.get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        var docId = document.id
                        //iddoc = docId
                        val docRef = db.collection("Voyage").document(docId)
                        docRef.delete()
                    }
                    //Log.d("Doc Id",iddoc)
                }
                val snack = Snackbar.make(recyclerView_TC,"TC Supprimé", Snackbar.LENGTH_LONG)
                snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.gray2))
                snack.show()
            }else {
                val snack = Snackbar.make(recyclerView_TC,"tc suppression error", Snackbar.LENGTH_LONG)
                snack.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.gray2))
                snack.show()
            }
        }

        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {

                return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.END  or ItemTouchHelper.START)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.END -> {
                        removedItem(viewHolder)
                    }
                    ItemTouchHelper.START -> {
                        removedItem(viewHolder)
                    }
                }
            }
        })

        var txtView_charging : TextView = binding.textViewCharging

        var progressBar_view : ProgressBar = binding.progressBarId
        progressBar_view.setVisibility(View.VISIBLE)

        fun inputItemInRecyclerView(){
            voyRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (documents != null){
                            val idtc_ok = document.getLong("step_TC")?.toInt()
                            val numtc_ok = document.data.get("num_TC").toString()
                            val num_booking_tc = document.data.get("num_Booking").toString()
                            val num_cam_ok = document.data.get("num_Camion").toString()
                            val step_tc_ok = document.getLong("step_TC")?.toInt()
                            val date_ok = document.data.get("Date").toString()
                            val plomb_ok = document.data.get("num_plomb_TC").toString()
                            val num_phone_chauffeur_ok = document.data.get("phone_chauffeur_TC").toString()
                            val numtcsecond_ok = document.data.get("num_TC_Second").toString()
                            val numplombsecond_ok = document.data.get("num_plomb_TC_2").toString()
                            if ( idtc_ok != null){
                                if (step_tc_ok != null ){
                                    items_tc.add(Tc( "$numtc_ok",
                                        "$numtcsecond_ok",
                                        "$num_cam_ok",
                                        "$num_phone_chauffeur_ok",
                                        " $num_booking_tc",
                                        "$plomb_ok",
                                        "$date_ok",
                                        step_tc_ok,
                                        "$numplombsecond_ok"))
                                    recyclerView_TC.apply {
                                        recyclerView_TC.adapter = TCAdapter(items_tc)
                                    }
                                    txtView_charging.isVisible  = false
                                    progressBar_view.setVisibility(View.GONE)
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }

        inputItemInRecyclerView()

        touchHelper.attachToRecyclerView(recyclerView_TC)

        refresh.setOnRefreshListener{

            items_tc.removeAll(items_tc)
            inputItemInRecyclerView()

            refresh.isRefreshing = false
            val snack = Snackbar.make(binding.root,"Page mis à jour",Snackbar.LENGTH_LONG)
            snack.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}