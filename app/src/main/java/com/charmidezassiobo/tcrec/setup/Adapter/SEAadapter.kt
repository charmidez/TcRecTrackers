package com.charmidezassiobo.tcrec.setup.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.baoyachi.stepview.HorizontalStepView
import com.charmidezassiobo.tcrec.R
import com.charmidezassiobo.tcrec.setup.data.HeureStep
import com.charmidezassiobo.tcrec.setup.data.Sea
import com.charmidezassiobo.tcrec.setup.interfaces.RecyclerViewClickItemInterface
import com.charmidezassiobo.tcrec.setup.functions.BayoStepViewFunctionsSetup

class SEAadapter(
    mContext: Context,
    var items: List<Sea>,
    val listener: RecyclerViewClickItemInterface
) : RecyclerView.Adapter<SEAadapter.SEAViewHolder>() {

    private val mContext: Context = mContext

    inner class SEAViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardV: FrameLayout
        var numtc1: TextView
        var numcamion: TextView
        var datetc: TextView
        var phonechauffeur: TextView
        var numtc2: TextView
        var txtV_sousTypeTransact: TextView
        var imgCheck: ImageView

        var stepvoyage: Int
        var stepView: HorizontalStepView
        var btnSuivant: AppCompatButton
        var etape: Int
        var step_tc: Int
        var bayoStep: BayoStepViewFunctionsSetup

        var tableauSateHeureStep: MutableList<HeureStep>

        //val txtSizeStep = 8

        var numPlomb_string: String
        var numPlomb_second_string: String

        //Pour  tester la connection
        //val connectivityManager = itemView.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //val activeNetworkInfo = connectivityManager.activeNetworkInfo
        //val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting

        init {
            cardV = itemView.findViewById(R.id.itemCardView_item_tc)
            numtc1 = itemView.findViewById(R.id.textViewTCNum_item)
            numcamion = itemView.findViewById(R.id.textViewCamionNum_item)
            datetc = itemView.findViewById(R.id.date_item)
            stepView = itemView.findViewById(R.id.step_view_baoya_view_item)
            btnSuivant = itemView.findViewById(R.id.button_suivant_item)
            phonechauffeur = itemView.findViewById(R.id.btnNumChauffeur)
            numtc2 = itemView.findViewById(R.id.textViewSecondTCNum_item)

            txtV_sousTypeTransact = itemView.findViewById(R.id.textView_sousTypeTransact)

            bayoStep = BayoStepViewFunctionsSetup(stepView)

            imgCheck = itemView.findViewById(R.id.imgCheck)
            stepvoyage = 0
            etape = 0

            step_tc = 0
            numPlomb_string = ""
            numPlomb_second_string = ""

            tableauSateHeureStep = mutableListOf()

            itemView.setOnClickListener {
                val position = adapterPosition
                listener.onItemClick(position)
                true
            }
            itemView.setOnLongClickListener {
                val position = adapterPosition
                listener.onLongClickListener(position)
                true
            }

        }

        fun bindTC(sea: Sea) {
            numtc1.text = sea.numTc1
            numtc2.text = sea.numTc2
            numcamion.text = sea.numCamion
            datetc.text = sea.dateAjoutSea
            etape = sea.stepTc
            phonechauffeur.text = sea.numChauffeur
            tableauSateHeureStep = sea.dateHourStep!!

            if (phonechauffeur.text == "NonDisponible" || phonechauffeur == null || phonechauffeur.text == "") {
                phonechauffeur.text = "Indisponible"
                phonechauffeur.isEnabled = false
                phonechauffeur.setBackground(
                    AppCompatResources.getDrawable(
                        itemView.context,
                        R.drawable.btn_drawable_not_selected
                    )
                )
            }
            if (numtc2.text == "null" || numtc2 == null || numtc2.text == "") {
                numtc2.isInvisible = true
            }
        }

        fun callButton() {
            phonechauffeur.setOnClickListener {
                val phoneNumber = phonechauffeur.text.toString()
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:$phoneNumber")
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SEAViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sea, parent, false)
        return SEAViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SEAViewHolder, position: Int) {
        val tc = items[position]

        holder.txtV_sousTypeTransact.text = tc.typeSousTransact
        holder.bindTC(tc)
        holder.bayoStep.stepChange(mContext, tc.stepTc, tc.typeTransact, tc.typeSousTransact)
        holder.bayoStep.clickSuivant(
            mContext,
            tc,
            tc.typeTransact,
            tc.typeSousTransact,
            holder.btnSuivant
        )
        holder.callButton()
    }

    fun setItemBackground(position: Int, recyclerView: RecyclerView, backgroundResId: Int) {
        val itemView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
        itemView?.setBackgroundResource(backgroundResId)
    }

}