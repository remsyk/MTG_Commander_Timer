package com.example.mtg_commander_timer.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.mtg_commander_timer.activities.MainActivity
import com.example.mtg_commander_timer.R
import com.example.mtg_commander_timer.millisToMinString
import kotlinx.android.synthetic.main.viewgroup_battle_dialog.view.*

class BattleDialog : DialogFragment() {

    var getValue: ((value: Boolean) -> Unit)? = null
    private lateinit var timer: CountDownTimer

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val rootView = LayoutInflater.from(context).inflate(R.layout.viewgroup_battle_dialog, null, false)

        with(rootView) {
            timer = object : CountDownTimer(MainActivity.battleTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    textView_battletime.text = millisUntilFinished.millisToMinString()
                }

                override fun onFinish() {
                    getValue?.let{it(true)}
                    dialog!!.dismiss()
                    cancel()
                }

            }.start()



        }

        return AlertDialog.Builder(requireContext()).setTitle("Battle!").setView(rootView).create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        getValue?.let { it(true)
            timer.cancel()
        }

    }


    companion object {
        private val newInstance = BattleDialog()
        private const val TAG = "BattleDialog"
        private var delete = false

        fun show(fragmentManager: FragmentManager): BattleDialog {
            val dialog = newInstance
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }
}