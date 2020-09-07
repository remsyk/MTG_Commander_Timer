package com.example.mtg_commander_timer.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class DiedDialog : DialogFragment() {

    var getValue: ((value: Boolean) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setTitle("Are you sure?")
            .setPositiveButton("YES") { dialog,  which ->
                getValue?.let{it(true)}
                delete = true

            }
            .setNegativeButton("No") { dialog, which ->
                getValue?.let{it(false)}
            }

            .create()
    }

    companion object {
        private val newInstance = DiedDialog()
        private const val TAG = "DiedDialog"
         private var delete=  false

        fun show(fragmentManager: FragmentManager): DiedDialog {
            val dialog = newInstance
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }
}