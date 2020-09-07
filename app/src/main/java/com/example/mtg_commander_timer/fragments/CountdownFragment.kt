package com.example.mtg_commander_timer.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_countdown.*
import kotlinx.android.synthetic.main.viewgroup_add_minus_chips.view.*
import androidx.lifecycle.Observer
import com.example.mtg_commander_timer.*
import com.example.mtg_commander_timer.MainActivity.Companion.currentFragNum
import com.example.mtg_commander_timer.dialogs.BattleDialog
import com.example.mtg_commander_timer.dialogs.DiedDialog


class CountdownFragment : Fragment() {

    var fragmentPos: Int = 0
    var pressing: Boolean = true

    override fun onResume() {
        super.onResume()


        arguments?.getInt("FRG_POSITION")?.let {
            fragmentPos = it
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_countdown, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        CountDownViewModel.getTimeList().observe(activity!!, Observer<MutableList<TimerModel>> {
                if (textview_name != null) {
                    try {
                        currentFragNum.log()
                        textview_name.text = it[currentFragNum].name
                        textview_countdown.text = it[currentFragNum].countdownTime.millisToString()
                    }catch (e: IndexOutOfBoundsException){
                        Toast.makeText(requireContext(),"Player Died", Toast.LENGTH_SHORT).show()

                    }


                }
            })


        include_chip_groups.chip_add_time.setOnClickListener {
            CountDownViewModel.addMinute(currentFragNum)


            Handler().postDelayed({
                CountDownViewModel.setTimer(currentFragNum)
                CountDownViewModel.startTimer()
            }, 800)
        }

        include_chip_groups.chip_minus_timer.setOnClickListener {
            CountDownViewModel.removeMinute(currentFragNum)
            if (pressing) {
                CountDownViewModel.stopTimer()

                pressing = false
                Handler().postDelayed({
                    CountDownViewModel.setTimer(currentFragNum)
                    CountDownViewModel.startTimer()
                    pressing = true
                }, 1000)
            }

        }


        button_died.setOnClickListener {
            DiedDialog.show(requireFragmentManager()).getValue = {
                if (it) {
                    CountDownViewModel.stopTimer()
                    CountDownViewModel.removeTimer(currentFragNum)
                }
            }
        }

        button_battle.setOnClickListener {
            CountDownViewModel.stopTimer()
            BattleDialog.show(requireFragmentManager()).getValue = {
                if (it) {
                    CountDownViewModel.setTimer(currentFragNum)
                    CountDownViewModel.startTimer()
                    pressing = true
                }
            }
        }

    }

}