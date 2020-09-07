package com.example.mtg_commander_timer

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

object CountDownViewModel : ViewModel() {


   private lateinit var timer: CountDownTimer


    private val timerList: MutableLiveData<MutableList<TimerModel>> by lazy {
        MutableLiveData<MutableList<TimerModel>>().also {
            it.value = mutableListOf<TimerModel>(
                TimerModel("Enter Name", 0, null, true),
                TimerModel("Enter Name", 0, null, true)
            )
        }
    }


    fun setPlayerName(name: String, position: Int){
        timerList.value!![position].name = name
        timerList.postValue(timerList.value)
    }

    fun setPlayerTime(time:Long, position: Int){
        timerList.value!![position].countdownTime = time
        timerList.postValue(timerList.value)
    }

    fun addTimer(timer: TimerModel) {
        timerList.value!!.add(timer)
        timerList.postValue(timerList.value)
    }

    fun removeTimer(position: Int) {
        timerList.value!!.removeAt(position)
        timerList.postValue(timerList.value)
    }

    @ExperimentalStdlibApi
    fun removeLastTimer() {
        timerList.value!!.removeLast()
        timerList.postValue(timerList.value)
    }

    fun getTimeList(): MutableLiveData<MutableList<TimerModel>> = timerList

    fun removeMinute(position: Int) {
        timerList.value?.get(position)?.countdownTime =
            timerList.value?.get(position)?.countdownTime?.minus(60000)!!
        timerList.postValue(timerList.value)
    }

    fun addMinute(position: Int) {
        stopTimer()
        timerList.value?.get(position)?.countdownTime =
            timerList.value?.get(position)?.countdownTime?.plus(60000)!!
        timerList.postValue(timerList.value)
    }



    fun setTimer(position: Int) {
        timer =  object : CountDownTimer(timerList.value!![position].countdownTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                timerList.value!!.get(position).countdownTime = millisUntilFinished
                timerList.postValue(timerList.value)

            }

            override fun onFinish() {
                /*Toast.makeText(
                    requireContext(),
                    "${MainActivity.timerList.get(position).name} has died",
                    Toast.LENGTH_LONG
                ).show()*/
            }

        }



    }


    fun startTimer(){
        timer.start()
    }


    fun stopTimer() {
        timer.cancel()
    }


}




