package com.example.mtg_commander_timer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mtg_commander_timer.R
import com.example.mtg_commander_timer.TimerModel
import kotlinx.android.synthetic.main.textlayout_player_name_item.view.*

class PlayerListAdapterRecyclerView(private val context: FragmentActivity) : RecyclerView.Adapter<PlayerListAdapterRecyclerView.ViewHolder>() {


    private lateinit var metricList: MutableList<TimerModel>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.textlayout_player_name_item, parent, false))

    }

    override fun getItemCount() = metricList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder){

            }

    }

    fun updateData(data: MutableList<TimerModel>) {
        metricList = data
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout = view.textLayout_player_name
        val textView = view.textinputeditText_player_name
    }

}