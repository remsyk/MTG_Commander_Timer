package com.example.mtg_commander_timer.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.mtg_commander_timer.R
import com.example.mtg_commander_timer.longToString2
import kotlinx.android.synthetic.main.viewgroup_dialog_set_battle_countdown.*
import kotlinx.android.synthetic.main.viewgroup_dialog_set_battle_countdown.picker_time_seconds
import kotlinx.android.synthetic.main.viewgroup_dialog_set_battle_countdown.view.*
import kotlinx.android.synthetic.main.viewgroup_dialog_set_countdown.view.*
import kotlinx.android.synthetic.main.viewgroup_dialog_set_countdown.view.picker_time_minutes
import kotlinx.android.synthetic.main.viewgroup_timer_picker.view.*

class TimeChangeBattleDialog : DialogFragment() {

    var getValue: ((value: Long) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val rootView = LayoutInflater.from(context).inflate(
            R.layout.viewgroup_dialog_set_battle_countdown,
            null,
            false
        )

        with(rootView) {


            picker_time_minutes.picker_time.minValue = 0
            picker_time_minutes.picker_time.maxValue = 23
            picker_time_minutes.timer_picker_name.text = "m"
            picker_time_minutes.picker_time.displayedValues = resources.getStringArray(
                R.array.time
            )

            picker_time_seconds.picker_time.minValue = 0
            picker_time_seconds.picker_time.maxValue = 23
            picker_time_seconds.timer_picker_name.text = "s"
            picker_time_seconds.picker_time.displayedValues = resources.getStringArray(
                R.array.time
            )

        }

        return AlertDialog.Builder(requireContext())
            .setTitle("Set player countdown")
            .setNegativeButton("Set") { dialog, _ ->
                getValue?.let {
                    it(
                        longToString2(
                            0.toString(),
                            resources.getStringArray(R.array.time)
                                .get(rootView.picker_time_minutes.picker_time.value),
                            resources.getStringArray(R.array.time)
                                .get(rootView.picker_time_seconds.picker_time.value)
                        )
                    )
                }
                dialog.dismiss()
            }
            .setView(rootView)
            .create()
    }


    companion object {
        private val newInstance =
            TimeChangeBattleDialog()
        private const val TAG = "TimeChangeDialog"

        fun show(fragmentManager: FragmentManager): TimeChangeBattleDialog {
            val dialog =
                newInstance
            dialog.show(fragmentManager,
                TAG
            )
            return dialog

        }
    }
}