package com.charmidezassiobo.tcrec.setup.interfaces

import android.content.Context
import android.view.View
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.charmidezassiobo.tcrec.setup.dataclass.Sea
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface AllFunctionsInterface {
    fun removeSpaces(str: String) : String
    fun miseEnPlaceDate(boolean: Boolean) : String
    fun miseEnPlaceHeure() : String
    fun filterResultSea(query: String?, itemList: MutableList<Sea>) : MutableList<Sea>
    fun removeRedundance(oldList: ArrayList<String>) : ArrayList<String>
    fun clearRadioBtnInAjoutTC(v: RadioGroup)
    fun resetFragmentInAjoutTC(radioBtn1: RadioGroup, radioBtn2: RadioGroup, radioBtn3: RadioGroup, ln1EditTextView: View, ln2EditTextView: View, ln3EditTextView: View, ln4EditTextView: View, ln5EditTextView: View)
    fun resetAllLnInAjoutTC( ln1EditTextView: View, ln2EditTextView: View, ln3EditTextView: View, ln4EditTextView: View, ln5EditTextView: View)
    fun filterListWithRecyclerSea(mContext : Context, listener: RecyclerViewClickItemInterface, query: String, items_tc: MutableList<Sea>, recyclerView_TC: RecyclerView)
    fun showCustomAlertDialog(context: Context, title: String, message: String)
    fun createPdf(context: Context, contenuDuPdf: String, nomDuPdf: String, convertPdfBtn: FloatingActionButton, recyclerView: RecyclerView)
    fun toggleItemSelectionSea(selectedItems: HashSet<Sea>, item: Sea)
    fun testKeyInDbBeforeGetData(collectionName : String, keyLookingFor : String)  : MutableList<String>
    fun snackBarShowWarning(mContext : Context, rootView : View, message : String)
    fun snackBarShowSucces(mContext : Context, rootView : View, message : String)
    //abstract fun requireContext(): Context?
}