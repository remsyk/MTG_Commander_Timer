package com.example.mtg_commander_timer

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.mtg_commander_timer.MainActivity.Companion.timerList
import kotlinx.android.synthetic.main.viewgroup_dialog_set_countdown.view.*
import kotlinx.android.synthetic.main.viewgroup_timer_picker.view.*
import java.util.concurrent.TimeUnit

class TimeChangeDialog : DialogFragment() {

    var getValue: ((value: Long) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val rootView = LayoutInflater.from(context).inflate(
            R.layout.viewgroup_dialog_set_countdown,
            null,
            false
        )

        with(rootView) {
            picker_time_hours.picker_time.minValue = 0
            picker_time_hours.picker_time.maxValue = 23
            picker_time_hours.timer_picker_name.text = "hr"
            picker_time_hours.picker_time.displayedValues = resources.getStringArray(R.array.time)

            picker_time_minutes.picker_time.minValue = 0
            picker_time_minutes.picker_time.maxValue = 23
            picker_time_minutes.timer_picker_name.text = "m"
            picker_time_minutes.picker_time.displayedValues = resources.getStringArray(R.array.time)

            picker_time_seconds.picker_time.minValue = 0
            picker_time_seconds.picker_time.maxValue = 23
            picker_time_seconds.timer_picker_name.text = "s"
            picker_time_seconds.picker_time.displayedValues = resources.getStringArray(R.array.time)

        }

        return AlertDialog.Builder(requireContext())
            .setTitle("Set player countdown")
            .setNegativeButton("Set") { dialog, _ ->
                getValue?.let {
                    it(
                        longToString2(
                            resources.getStringArray(R.array.time)
                                .get(rootView.picker_time_hours.picker_time.value),
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
        private val newInstance = TimeChangeDialog()
        private const val TAG = "ConditionMakerDialog"

        fun show(fragmentManager: FragmentManager): TimeChangeDialog {
            val dialog = newInstance
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }
}