package com.example.mtg_commander_timer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mtg_commander_timer.CountDownViewModel
import com.example.mtg_commander_timer.R
import com.example.mtg_commander_timer.TimerModel
import com.example.mtg_commander_timer.dialogs.TimeChangeDialog
import com.example.mtg_commander_timer.log
import kotlinx.android.synthetic.main.cardview_top_item.view.*

class TopItemsAdapter(private val context: FragmentActivity) : RecyclerView.Adapter<TopItemsAdapter.ViewHolder>() {

    private lateinit var metricList: MutableList<TimerModel>

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
                    body.text = "00:00:00"

                    card.setOnClickListener {
                        TimeChangeDialog.show(context.supportFragmentManager).getValue = { value ->
                            value.log()
                        }
                    }

                }

                1 -> {
                    title.text = "Battle Time"
                    body.text = "1:00"
                }

                2 -> {
                    title.text = "Players"
                    body.text = metricList.size.toString()
                    addPlayer.visibility = View.VISIBLE
                    removePlayer.visibility = View.VISIBLE

                    addPlayer.setOnClickListener {
                        "add player pressed".log()
                        CountDownViewModel.addPlayer(TimerModel("Enter Name", 0, null, true))


                    }

                    removePlayer.setOnClickListener {
                        CountDownViewModel.removeLastPlayer()
                    }
                }

                3 -> {
                    title.text = "Sound"
                    body.text = "On"
                }

                else -> {
                    TODO()
                }


            }
        }
    }

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
