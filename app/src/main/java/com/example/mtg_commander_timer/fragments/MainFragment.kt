package com.example.mtg_commander_timer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mtg_commander_timer.*
import com.example.mtg_commander_timer.dialogs.TimeChangeDialog
import kotlinx.android.synthetic.main.fragment_main_view.*
import kotlinx.android.synthetic.main.viewgroup_enter_name.view.*


class MainFragment : Fragment() {
    var timerList: MutableList<TimerModel> = mutableListOf<TimerModel>(
        TimerModel("Enter Name", 0, null, true),
        TimerModel("Enter Name", 0, null, true)
    )
    var listSet: Boolean = false

    //TODO use this for the bot trader
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        CountDownViewModel.getTimeList()
            .observe(activity!!, Observer<MutableList<TimerModel>> {
            timerList = it
        })

    }

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

        picker_player_num.maxValue = 4
        picker_player_num.minValue = CountDownViewModel.getTimeList().value!!.size

        include_enter_name_1.textLayout_name.hint = timerList.get(0).name
        include_enter_name_2.textLayout_name.hint = timerList.get(1).name

        if (timerList.size > 2) {
            include_enter_name_3.textLayout_name.hint = timerList.get(2).name
            include_enter_name_4.textLayout_name.hint = timerList.get(3).name
        }

        include_enter_name_1.button_timer.text = timerList.get(0).countdownTime.millisToString()
        include_enter_name_2.button_timer.text = timerList.get(1).countdownTime.millisToString()

        if (timerList.size > 2) {
            include_enter_name_3.button_timer.text =
                timerList.get(2).countdownTime.millisToString()
            include_enter_name_4.button_timer.text =
                timerList.get(3).countdownTime.millisToString()
        }


        include_enter_name_1.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if(b){
                include_enter_name_1.textinputeditText_name.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(
                        it.toString(),
                        0
                    )
                }
            }
        }


        include_enter_name_2.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if(b){
                include_enter_name_2.textinputeditText_name.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(
                        it.toString(),
                        1
                    )
                }
            }
        }

        include_enter_name_3.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if(b){
                include_enter_name_3.textinputeditText_name.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(
                        it.toString(),
                        2
                    )
                }
            }
        }

        include_enter_name_4.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if(b){
                include_enter_name_4.textinputeditText_name.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(
                        it.toString(),
                        3
                    )
                }
            }
        }





        picker_player_num.setOnValueChangedListener { numberPicker, i, i2 ->

            when (i2) {
                3 -> {
                    include_enter_name_3.visibility = View.VISIBLE
                    include_enter_name_4.visibility = View.GONE
                    if (timerList.size <= 2) {
                        timerList.add(
                            TimerModel(
                                "Enter Name",
                                0,
                                null,
                                true
                            )
                        )
                    }
                    if (timerList.size == 4) {

                        CountDownViewModel.removeLastTimer()
                        timerList.removeLast()
                    }
                }

                4 -> {
                    include_enter_name_3.visibility = View.VISIBLE
                    include_enter_name_4.visibility = View.VISIBLE
                    if (timerList.size <= 3) {
                        timerList.add(
                            TimerModel(
                                "Enter Name",
                                0,
                                null,
                                true
                            )
                        )

                    }
                }

                else -> {
                    if (timerList.size == 3) {
                        CountDownViewModel.removeLastTimer()
                        include_enter_name_3.visibility = View.GONE
                        include_enter_name_4.visibility = View.GONE
                    }
                }
            }


        }

        include_enter_name_1.button_timer.setOnClickListener {
            TimeChangeDialog.show(
                requireFragmentManager()
            ).getValue = { value ->
                include_enter_name_1.button_timer.text = value.millisToString()
                timerList[0].countdownTime = value
            }
        }

        include_enter_name_2.button_timer.setOnClickListener {
            TimeChangeDialog.show(
                requireFragmentManager()
            ).getValue = { value ->
                include_enter_name_2.button_timer.text = value.millisToString()
                timerList[1].countdownTime = value
            }
        }

        include_enter_name_3.button_timer.setOnClickListener {
            TimeChangeDialog.show(
                requireFragmentManager()
            ).getValue = { value ->
                include_enter_name_3.button_timer.text = value.millisToString()
                timerList[2].countdownTime = value
            }
        }

        include_enter_name_4.button_timer.setOnClickListener {
            TimeChangeDialog.show(
                requireFragmentManager()
            ).getValue = { value ->
                include_enter_name_4.button_timer.text = value.millisToString()
                timerList[3].countdownTime = value
            }
        }

        textview_timer.setOnClickListener {
            TimeChangeDialog.show(
                requireFragmentManager()
            ).getValue = { value ->
                textview_timer.text = value.millisToString()
                MainActivity.firstTimeSet = true
                timerList.iterator().forEach {
                    it.countdownTime = value
                }

                include_enter_name_1.button_timer.text = value.millisToString()
                include_enter_name_2.button_timer.text = value.millisToString()
                include_enter_name_3.button_timer.text = value.millisToString()
                include_enter_name_4.button_timer.text = value.millisToString()

            }
        }

        textview_battle_time.text = MainActivity.battleTime.millisToMinString()
        textview_battle_time.setOnClickListener {
            TimeChangeDialog.show(
                requireFragmentManager()
            ).getValue = { value ->
                textview_battle_time.text = value.millisToMinString()
                MainActivity.battleTime = value

            }
        }
    }
}