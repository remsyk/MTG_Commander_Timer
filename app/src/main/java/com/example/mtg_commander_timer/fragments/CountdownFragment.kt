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

    var fragmentPos: Int = 0 //current fragment position
    var pressing: Boolean = true  //if any button is  being pressed, dont allow things to be pressed at the same time


    override fun onResume() {
        super.onResume()


        arguments?.getInt("FRG_POSITION")?.let {
            fragmentPos = it
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_countdown, container, false)
        MainActivity.pauseIconEnabled(true) //enable pause button

        return root

    }


    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // MainActivity.pauseIconEnabled(true)


        progressBar.max = 1000


        CountDownViewModel.getPlayerList().observe(activity!!, Observer<MutableList<TimerModel>> {

            //it the text we are trying to populate on the countdown fragment
            if (textview_name != null) {
                try {

                    textview_name.text = it[currentFragNum].name //set the name of player being viewd
                    textview_countdown.text = it[currentFragNum].countdownTime.millisToMinString() //set the time for player being views

                    //have progress bar element decrement
                    progressBar.progress = ((((CountDownViewModel.getProgress(currentFragNum)).toDouble() / mainTime.toDouble()) * 1000)).toInt()


                    //The IndexOutOfBoundsException will be thrown by CountDownFragmetViewPager when a player is removed
                } catch (e: IndexOutOfBoundsException) {

                }


            }
        })


        //button to add time to main time for user
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

        //button to reduce time for user
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

        //button user presses when a player is killed in the game and needs to be removed from game
        button_died.setOnClickListener {
            if (pressing) {
                pressing = false

                //Prompt user that they are sure that player has died then, return the value from the users input either removing player or not
                DiedDialog.show(requireFragmentManager()).getValue = {
                    if (it) {

                        Toast.makeText(requireContext(), "${CountDownViewModel.getPLayerName(currentFragNum)} Died", Toast.LENGTH_LONG).show()

                        CountDownViewModel.stopTimer()
                        CountDownViewModel.removePlayerDied(currentFragNum)

                        if(soundOn){

                            var mediaPlayer = MediaPlayer.create(requireContext(), R.raw.player_died)
                            mediaPlayer.start()
                        }


                            //if there is only one player left then they are the winner and the game resets
                        if(CountDownViewModel.getPlayerList().value!!.size == 1){
                            activity!!.supportFragmentManager!!.beginTransaction().remove(CountdownViewPagerFragment()).commit()
                            activity!!.supportFragmentManager!!.popBackStack()

                            Toast.makeText(requireContext(), "${CountDownViewModel.getPLayerName(0)} Won", Toast.LENGTH_LONG).show()


                            CountDownViewModel.clearPlayers() // clear all players from the view model and add 2 more for the reset
                            mainTime = 0

                        }
                    }
                }
                Handler().postDelayed({
                    pressing = true
                }, 1000)
            }
        }

        //button for when players enter battle phase start battle timer
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
                //dont let them smash button
                Handler().postDelayed({
                    pressing = true
                }, 1000)
            }
        }

    }

}