package com.example.mtg_commander_timer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mtg_commander_timer.MainActivity.Companion.timerList
import kotlinx.android.synthetic.main.fragment_countdown.*
import kotlinx.android.synthetic.main.fragment_main_view.*
import kotlinx.android.synthetic.main.viewgroup_enter_name.view.*
import java.lang.IndexOutOfBoundsException
import kotlin.time.milliseconds


class CountdownFragment : Fragment() {

    var fragmentPos: Int = 0

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

        arguments?.getInt("FRG_POSITION")?.let {
            fragmentPos = it

            textview_name.text = timerList.get(fragmentPos).name


        }

        textView_add_time.setOnClickListener {
            timerList.get(fragmentPos).countdownTime =
                timerList.get(fragmentPos).countdownTime + 60000
            textview_countdown.text = timerList.get(fragmentPos).countdownTime.millisToString()
        }

        textview_countdown.text = timerList.get(fragmentPos).countdownTime.millisToString()

        button_died.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "${timerList.get(fragmentPos).name} has died",
                Toast.LENGTH_LONG
            ).show()
        }

    }
}