package com.example.mtg_commander_timer

import android.util.Log
import java.util.concurrent.TimeUnit

fun Any.log(){
    Log.v("+++", this.toString())
}

fun longToString2(hr: String, m: String, s: String): Long{

    return (hr.toLong() * 3600000) + (m.toLong() * 60000) + (s.toLong() * 1000)

}

fun longToString(hr: String, m: String, s: String): String{

    val countdownTime = (hr.toLong() * 3600000) + (m.toLong() * 60000) + (s.toLong() * 1000)


    return String.format("%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(countdownTime),
        TimeUnit.MILLISECONDS.toMinutes(countdownTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(countdownTime)),
        TimeUnit.MILLISECONDS.toSeconds(countdownTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(countdownTime))
    )
}

fun Long.millisToString() = String.format("%02d:%02d:%02d",
    TimeUnit.MILLISECONDS.toHours(this),
    TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this)),
    TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
)