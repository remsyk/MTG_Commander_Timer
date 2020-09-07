package com.example.mtg_commander_timer

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
import java.util.*
import androidx.lifecycle.Observer
import com.example.mtg_commander_timer.MainActivity.Companion.currentFragNum
import kotlin.properties.Delegates


class CountdownFragment : Fragment() {

    var fragmentPos: Int = 0
    lateinit var timer: CountDownTimer
    var pressing: Boolean = true

    override fun onResume() {
        super.onResume()


        arguments?.getInt("FRG_POSITION")?.let {
            fragmentPos = it
            currentFragNum = it
        }


        /*CountDownViewModel.setTimer(fragmentPos)
        CountDownViewModel.startTimer()*/

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_countdown, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        CountDownViewModel.getTimeList().observe(activity!!, Observer<MutableList<TimerModel>> {
            if (textview_name != null) {
                textview_name.text = it[fragmentPos].name
                textview_countdown.text = it[fragmentPos].countdownTime.millisToString()
            }
        })


        include_chip_groups.chip_add_time.setOnClickListener {
            CountDownViewModel.addMinute(fragmentPos)


            Handler().postDelayed({
                CountDownViewModel.setTimer(fragmentPos)
                CountDownViewModel.startTimer()
            }, 800)
        }

        include_chip_groups.chip_minus_timer.setOnClickListener {
            CountDownViewModel.removeMinute(fragmentPos)
            if(pressing){
                "pressing set to false".log()
                CountDownViewModel.stopTimer()

                pressing = false
                Handler().postDelayed({
                    CountDownViewModel.setTimer(fragmentPos)
                    CountDownViewModel.startTimer()
                    pressing = true
                    "pressing back to true".log()
                }, 1000)
            }

        }


        button_died.setOnClickListener {
            CountDownViewModel.removeTimer(fragmentPos)

        }

    }

}