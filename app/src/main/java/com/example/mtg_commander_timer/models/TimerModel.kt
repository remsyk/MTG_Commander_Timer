package com.example.mtg_commander_timer.models

import android.graphics.Color

class TimerModel (
    var name: String,
    var countdownTime: Long,
    var color: Color?,
    var alive: Boolean
)