package com.example.mtg_commander_timer.models

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mtg_commander_timer.activities.MainActivity.Companion.mainTime
import com.example.mtg_commander_timer.log
import java.lang.IndexOutOfBoundsException

object CountDownViewModel : ViewModel() {


    private lateinit var timer: CountDownTimer
    private var timerRunning = false

    private val timerList: MutableLiveData<MutableList<TimerModel>> by lazy {
        MutableLiveData<MutableList<TimerModel>>().also {
            it.value = mutableListOf<TimerModel>(TimerModel("Enter Name", 0, null, true), TimerModel("Enter Name", 0, null, true))
        }
    }


    fun setPlayerName(name: String, position: Int) {
        timerList.value!![position].name = name
        timerList.postValue(timerList.value)
    }

    fun setPlayerTime(time: Long, position: Int) {
        timerList.value!![position].countdownTime = time
        timerList.postValue(timerList.value)
    }

    fun addPlayer(timer: TimerModel) {
        if (timerList.value!!.size < 4) {
            timerList.value!!.add(timer)
            timerList.postValue(timerList.value)
        }
    }

    fun getPLayerName(position: Int): String {
        return timerList.value!![position].name
    }

    //remove player during main fragment setup cant let there be less than 2 players
    fun removePlayerFromSetup(position: Int) {
        if (timerList.value!!.size > 2) {
            timerList.value!!.removeAt(position)
            timerList.postValue(timerList.value)
        }
    }

    //remove player during play when they die from time running our being killed
    fun removePlayerDied(position: Int) {
        timerList.value!!.removeAt(position)
        timerList.postValue(timerList.value)
    }

    //remove last player during setup
    @ExperimentalStdlibApi
    fun removeLastPlayerSetup() {
        if (timerList.value!!.size > 2) {
            timerList.value!!.removeLast()
            timerList.postValue(timerList.value)
        }
    }

    //remove last player during play when they die from time running our being killed
    @ExperimentalStdlibApi
    fun removeLastPlayerDied() {
        timerList.value!!.removeLast()
        timerList.postValue(timerList.value)
    }

    fun getPlayerList(): MutableLiveData<MutableList<TimerModel>> = timerList

    fun removeMinute(position: Int) {
        if ((timerList.value?.get(position)?.countdownTime!!) >= 60000.00) {

            timerList.value?.get(position)?.countdownTime = timerList.value?.get(position)?.countdownTime?.minus(60000)!!
            timerList.postValue(timerList.value)
        }
    }

    fun addMinute(position: Int) {
        timerList.value?.get(position)?.countdownTime = timerList.value?.get(position)?.countdownTime?.plus(60000)!!
        timerList.postValue(timerList.value)

    }

    //remove all players from game and resest the game back to 2 players
    fun clearPlayers() {
        timerList.value!!.clear()
        timerList.postValue(timerList.value)

        addPlayer(TimerModel("Enter Name", mainTime, null, true))
        addPlayer(TimerModel("Enter Name", mainTime, null, true))
    }

    fun setTimer(position: Int) {

        if (!timerRunning) {

            timer = object : CountDownTimer(timerList.value!![position].countdownTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    try {
                        timerList.value!!.get(position).countdownTime = millisUntilFinished
                        timerList.postValue(timerList.value)

                    } catch (e: IndexOutOfBoundsException) {

                    }
                }

                override fun onFinish() {
                    "Timer onFishing Called".log()
                    //removePlayerDied(position)
                }
            }
        }
    }

    fun startTimer() {
        if (!timerRunning) {
            timerRunning = true
            timer.start()
            "Timer Started: ${timer.hashCode()}".log()
        }
    }


    fun stopTimer() {
        timerRunning = false
        ("Timer Stopped: ${timer.hashCode()}").log()
        "___________________".log()
        timer.cancel()

    }

    fun getProgress(position: Int) = (timerList.value!![position].countdownTime)


}




