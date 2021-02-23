package com.example.mtg_commander_timer.fragments

import android.media.MediaPlayer
import android.os.Bundle
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
import com.example.mtg_commander_timer.activities.MainActivity
import com.example.mtg_commander_timer.activities.MainActivity.Companion.currentFragNum
import com.example.mtg_commander_timer.activities.MainActivity.Companion.mainTime
import com.example.mtg_commander_timer.activities.MainActivity.Companion.soundOn
import com.example.mtg_commander_timer.dialogs.BattleDialog
import com.example.mtg_commander_timer.dialogs.DiedDialog
import com.example.mtg_commander_timer.models.CountDownViewModel
import com.example.mtg_commander_timer.models.TimerModel


class CountdownFragment : Fragment() {

    var fragmentPos: Int = 0
    var pressing: Boolean = true
    var fragmentChanged: Boolean = false


    override fun onResume() {
        super.onResume()


        arguments?.getInt("FRG_POSITION")?.let {
            fragmentPos = it
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_countdown, container, false)
        MainActivity.pauseIconEnabled(true)

        return root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MainActivity.pauseIconEnabled(true)


        progressBar.max = 1000


        CountDownViewModel.getTimeList().observe(activity!!, Observer<MutableList<TimerModel>> {


            if (textview_name != null) {
                try {
                    textview_name.text = it[currentFragNum].name
                    textview_countdown.text = it[currentFragNum].countdownTime.millisToMinString()

                    progressBar.progress = ((((CountDownViewModel.getProgress(currentFragNum)).toDouble() / mainTime.toDouble()) * 1000)).toInt()


                    if (CountDownViewModel.getProgress(currentFragNum).equals(0)) {
                        CountDownViewModel.stopTimer()
                        CountDownViewModel.removePlayer(currentFragNum)

                    }


                } catch (e: IndexOutOfBoundsException) {
                    Toast.makeText(requireContext(), "${CountDownViewModel.getPLayerName(currentFragNum)} Died", Toast.LENGTH_LONG).show()

                }


            }
        })


        include_chip_groups.chip_add_time.setOnClickListener {
            CountDownViewModel.addMinute(currentFragNum)

            if (pressing) {
                CountDownViewModel.stopTimer()
                pressing = false

                Handler().postDelayed({
                    CountDownViewModel.setTimer(currentFragNum)
                    CountDownViewModel.startTimer()
                    pressing = true
                }, 1700)
            }
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
                }, 1700)
            }

        }

        button_died.setOnClickListener {
            if (pressing) {
                pressing = false
                DiedDialog.show(requireFragmentManager()).getValue = {
                    if (it) {

                        Toast.makeText(requireContext(), "${CountDownViewModel.getPLayerName(currentFragNum)} Died", Toast.LENGTH_LONG).show()

                        CountDownViewModel.stopTimer()
                        CountDownViewModel.removePlayerDied(currentFragNum)

                        if(soundOn){

                            var mediaPlayer = MediaPlayer.create(requireContext(), R.raw.player_died)
                            mediaPlayer.start()
                        }


                       /* if(CountDownViewModel.getTimeList().value!!.size == 1){

                            Toast.makeText(requireContext(), "${CountDownViewModel.getPLayerName(0)} Won", Toast.LENGTH_LONG).show()

                        }*/


                        if(CountDownViewModel.getTimeList().value!!.size == 1){

                            "got here".log()

                            //requireFragmentManager().beginTransaction().replace(R.id.framelayout_main, MainFragment()).addToBackStack("CountdownFragment").commit()


                            activity!!.supportFragmentManager!!.beginTransaction().remove(CountdownViewPagerFragment()).commit()
                            activity!!.supportFragmentManager!!.popBackStack()

                            CountDownViewModel.removePlayer(0)
                            CountDownViewModel.addPlayer(TimerModel("Enter Name", mainTime, null, true))
                            CountDownViewModel.addPlayer(TimerModel("Enter Name", mainTime, null, true))
                        }

                    }
                }
                Handler().postDelayed({
                    pressing = true
                }, 1000)
            }
        }

        button_battle.setOnClickListener {
            CountDownViewModel.stopTimer()
            if (pressing) {
                pressing = false
                BattleDialog.show(requireFragmentManager()).getValue = {
                    if (it) {
                        CountDownViewModel.setTimer(currentFragNum)
                        CountDownViewModel.startTimer()
                        pressing = true
                    }
                }
                Handler().postDelayed({
                    pressing = true
                }, 1000)
            }
        }

    }

}