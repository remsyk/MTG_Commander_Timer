package com.example.mtg_commander_timer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mtg_commander_timer.*
import com.example.mtg_commander_timer.activities.MainActivity
import com.example.mtg_commander_timer.activities.MainActivity.Companion.battleTime
import com.example.mtg_commander_timer.activities.MainActivity.Companion.mainTime
import com.example.mtg_commander_timer.dialogs.TimeChangeBattleDialog
import com.example.mtg_commander_timer.dialogs.TimeChangeDialog
import com.example.mtg_commander_timer.models.CountDownViewModel
import com.example.mtg_commander_timer.models.TimerModel
import kotlinx.android.synthetic.main.cardview_top_item.view.*

class TopItemsAdapter(private val context: FragmentActivity) : RecyclerView.Adapter<TopItemsAdapter.ViewHolder>() {

    private lateinit var metricList: MutableList<TimerModel>
    private var playerCount = 2
    private var soundOn = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_top_item, parent, false))


    }

    override fun getItemCount() = 4

    @ExperimentalStdlibApi
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            when (position) {

                0 -> {
                    title.text = "Turn Time"
                    body.text = mainTime.millisToMinString()

                    card.setOnClickListener {
                        TimeChangeDialog.show(context.supportFragmentManager).getValue = { value ->
                            mainTime = value
                            body.text = mainTime.millisToMinString() //return value from dialog and convert to string for view

                            //set each player with the same main time
                            for (x in 0 until playerCount) {
                                CountDownViewModel.setPlayerTime(mainTime, x)
                            }

                        }

                        MainActivity.firstTimeSet = true
                    }

                }

                1 -> {
                    title.text = "Battle Time"
                    body.text = battleTime.millisToMinString()

                    //get battle time back from dialog
                    card.setOnClickListener {
                        TimeChangeBattleDialog.show(context.supportFragmentManager).getValue = { value ->
                            battleTime = value
                            body.text = value.millisToMinString()
                        }
                    }
                }

                2 -> {
                    title.text = "Sound"
                    body.text = "Off"

                    card.setOnClickListener {
                        if (soundOn) {
                            body.text = "Off"
                            soundOn = false
                            MainActivity.soundOn = soundOn
                        } else {
                            body.text = "On"
                            soundOn = true
                            MainActivity.soundOn = soundOn
                        }
                    }
                }

                3 -> {

                    title.text = "Players"
                    body.text = CountDownViewModel.getPlayerList().value!!.size.toString()
                    addPlayer.visibility = View.VISIBLE
                    removePlayer.visibility = View.VISIBLE

                    //add new player with enter name as name but has mainTime that was set
                    addPlayer.setOnClickListener {
                        CountDownViewModel.addPlayer(TimerModel("Enter Name", mainTime, null, true))
                    }

                    removePlayer.setOnClickListener {
                        CountDownViewModel.removeLastPlayerSetup()
                    }
                }

                else -> {
                    TODO()
                }


            }
        }
    }

    //update data from the view model
    fun updateData(data: MutableList<TimerModel>) {
        metricList = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.cardview_top_item_item
        val title: TextView = view.textView_title_item
        val body: TextView = view.textView_body_item
        val addPlayer = view.button_add_player
        val removePlayer = view.button_remove_player
    }
}
