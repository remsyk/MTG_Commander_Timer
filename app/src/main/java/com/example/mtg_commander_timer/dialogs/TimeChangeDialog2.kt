package com.example.mtg_commander_timer.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.mtg_commander_timer.R
import com.example.mtg_commander_timer.log
import com.example.mtg_commander_timer.longToString2
import kotlinx.android.synthetic.main.viewgroup_dialog_set_countdown.view.*
import kotlinx.android.synthetic.main.viewgroup_dialog_set_countdown_2.view.*
import kotlinx.android.synthetic.main.viewgroup_timer_picker.view.*

class TimeChangeDialog2 : DialogFragment() {

    var getValue: ((value: Long) -> Unit)? = null

    var testString: CharArray = CharArray(9)
    var testString2: String = ""
    var inputCounter: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val rootView = LayoutInflater.from(context).inflate(R.layout.viewgroup_dialog_set_countdown_2, null, false)

        with(rootView) {

            /*textView_set_countdown.setOnKeyListener { view, i, keyEvent ->

                if (keyEvent.keyCode == KeyEvent.KEYCODE_DEL){
                    inputCounter--
                }
                false
            }*/


            textView_set_countdown.text.length.log()


            /*textView_set_countdown.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }
                })*/



            textView_set_countdown.addTextChangedListener {


                inputCounter++


                when(inputCounter){
                    1-> {
                        testString2 = it.toString()
                        it!!.clear()
                        it!!.append("00 00, 0$testString2")
                       // testString[9] = it!![0]
                    }

                    2-> {
                        //it!!.insert(8,it)
                        //testString[8] = it!![]
                    }

                    3-> testString[6] = it!![2]

                    4-> testString[5] = it!![3]
                }

                testString.log()

            }


        }

        return AlertDialog.Builder(requireContext()).setTitle("Set countdown time").setNegativeButton("Set") { dialog, _ ->
                getValue?.let {
                    it(


                        longToString2(resources.getStringArray(R.array.time).get(rootView.picker_time_hours.picker_time.value),
                            resources.getStringArray(R.array.time).get(rootView.picker_time_minutes.picker_time.value),
                            resources.getStringArray(R.array.time).get(rootView.picker_time_seconds.picker_time.value)))
                }
                dialog.dismiss()
            }.setView(rootView).create()
    }


    companion object {
        private val newInstance = TimeChangeDialog2()
        private const val TAG = "TimeChangeDialog"

        fun show(fragmentManager: FragmentManager): TimeChangeDialog2 {
            val dialog = newInstance
            dialog.show(fragmentManager, TAG)
            return dialog

        }
    }
}