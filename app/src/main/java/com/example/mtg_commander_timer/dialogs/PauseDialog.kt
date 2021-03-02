package com.example.mtg_commander_timer.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.mtg_commander_timer.R
import com.example.mtg_commander_timer.activities.MainActivity
import com.example.mtg_commander_timer.fragments.CountdownViewPagerFragment
import com.example.mtg_commander_timer.log
import com.example.mtg_commander_timer.longToString2
import com.example.mtg_commander_timer.models.CountDownViewModel
import kotlinx.android.synthetic.main.viewgroup_dialog_set_countdown.view.*
import kotlinx.android.synthetic.main.viewgroup_timer_picker.view.*

class PauseDialog : DialogFragment() {

    var getValue: ((value: Boolean) -> Unit)? = null
    private var resetPreesed = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setMessage("Paused")
            .setNegativeButton("Reset") { dialog, _ ->
                resetPreesed = true
                dialog.dismiss()
            }

            .setPositiveButton("Resume") { dialog, _ ->
                resetPreesed = false

                dialog.dismiss()
            }

            .create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        getValue?.let { it(resetPreesed) }
        super.onDismiss(dialog)
    }

    companion object {
        private val newInstance = PauseDialog()
        private const val TAG = "WinnerLoserDialog"


        fun show(fragmentManager: FragmentManager): PauseDialog {
            val dialog = newInstance
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }
}