package com.example.mtg_commander_timer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mtg_commander_timer.MainActivity.Companion.timerList
import kotlinx.android.synthetic.main.fragment_countdown.view.*
import kotlinx.android.synthetic.main.fragment_main_view.*
import kotlinx.android.synthetic.main.viewgroup_enter_name.view.*
import kotlin.concurrent.timer


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_view, container, false)
        return root
    }

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerList =
            arrayListOf(TimerModel("Enter name", 0, null, true), TimerModel("name", 0, null, true))

        picker_player_num.maxValue = 4
        picker_player_num.minValue = timerList.size

        include_enter_name_1.textLayout_name.hint = timerList.get(0).name
        include_enter_name_2.textLayout_name.hint = timerList.get(1).name

        if (timerList.size >2) {
            include_enter_name_3.textLayout_name.hint = timerList.get(2).name
            include_enter_name_4.textLayout_name.hint = timerList.get(3).name
        }

        include_enter_name_1.button_timer.text = timerList.get(0).countdownTime.millisToString()
        include_enter_name_2.button_timer.text = timerList.get(1).countdownTime.millisToString()

        if (timerList.size >2) {
            include_enter_name_3.button_timer.text = timerList.get(2).countdownTime.millisToString()
            include_enter_name_4.button_timer.text = timerList.get(3).countdownTime.millisToString()
        }


        include_enter_name_1.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if (!b) {
                timerList[0].name = include_enter_name_1.textinputeditText_name.text.toString()
            }
        }

        include_enter_name_2.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if (!b) {
                timerList[1].name = include_enter_name_2.textinputeditText_name.text.toString()
            }
        }

        include_enter_name_3.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if (!b) {
                timerList[2].name = include_enter_name_3.textinputeditText_name.text.toString()
            }
        }

        include_enter_name_4.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if (!b) {
                timerList[3].name = include_enter_name_4.textinputeditText_name.text.toString()
            }
        }



        picker_player_num.setOnValueChangedListener { numberPicker, i, i2 ->

            when (i2) {
                3 -> {
                    include_enter_name_3.visibility = View.VISIBLE
                    include_enter_name_4.visibility = View.GONE
                    if (timerList.size <= 2) {
                        timerList.add(TimerModel("Enter Name", 0, null, true))

                    }
                    if (timerList.size == 4) {
                        timerList.removeLast()
                    }
                }

                4 -> {
                    include_enter_name_3.visibility = View.VISIBLE
                    include_enter_name_4.visibility = View.VISIBLE
                    if (timerList.size <= 3) {
                        timerList.add(TimerModel("Enter Name", 0, null, true))

                    }
                }

                else -> {
                    if (timerList.size == 3) {
                        timerList.removeLast()
                        include_enter_name_3.visibility = View.GONE
                        include_enter_name_4.visibility = View.GONE
                    }
                }
            }


        }

        include_enter_name_1.button_timer.setOnClickListener {
            TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                include_enter_name_1.button_timer.text = value.millisToString()
                timerList.get(0).countdownTime = value
            }
        }

        include_enter_name_2.button_timer.setOnClickListener {
            TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                include_enter_name_2.button_timer.text = value.millisToString()
                timerList.get(1).countdownTime = value
            }
        }

        include_enter_name_3.button_timer.setOnClickListener {
            TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                include_enter_name_3.button_timer.text = value.millisToString()
                timerList.get(2).countdownTime = value
            }
        }

        include_enter_name_4.button_timer.setOnClickListener {
            TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                include_enter_name_4.button_timer.text = value.millisToString()
                timerList.get(3).countdownTime = value
            }
        }

        textview_timer.setOnClickListener {
            TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                textview_timer.text = value.millisToString()

                MainActivity.timerList.iterator().forEach {
                    it.countdownTime = value
                }

                include_enter_name_1.button_timer.text = value.millisToString()
                include_enter_name_2.button_timer.text = value.millisToString()
                include_enter_name_3.button_timer.text = value.millisToString()
                include_enter_name_4.button_timer.text = value.millisToString()

            }
        }
    }
}