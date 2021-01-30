package com.example.mtg_commander_timer.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class WinnerLoserDialog : DialogFragment() {

    var getValue: ((value: Boolean) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setMessage("Player has died")
            .create()
    }

    companion object {
        private val newInstance = WinnerLoserDialog()
        private const val TAG = "WinnerLoserDialog"


        fun show(fragmentManager: FragmentManager, message:String): WinnerLoserDialog {
            val dialog = newInstance
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }
}