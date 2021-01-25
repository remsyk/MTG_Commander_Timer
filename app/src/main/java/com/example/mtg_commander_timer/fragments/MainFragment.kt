package com.example.mtg_commander_timer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mtg_commander_timer.*
import com.example.mtg_commander_timer.activities.MainActivity
import com.example.mtg_commander_timer.adapters.TopItemsAdapter
import com.example.mtg_commander_timer.models.CountDownViewModel
import com.example.mtg_commander_timer.models.TimerModel
import kotlinx.android.synthetic.main.fragment_main_view.*


//TODO TimerChangeDialog: Fragment already added error
//TODO space the chips for add minus time out
//TODO after someone dies start next timer that appears
//TODO fix when you go back main fragment the name are correct
//TODO just start new activity when you back press but presever the individual player time incase someone adds a player last second
//TODO name input fields request next focus on enter button
//TODO when last timer runs out a winner is announced with animation
//TODO circular countdown timer
//TODO create app icon (swords as a clock hands)
//TODO fix button next to text input i dont like how it look
//TODO try to make true circular swiping for count down activity view pager
//TODO make the padding for the margins on the cards the same for the sides as the top
//TODO Remove the app bar


class MainFragment : Fragment() {


    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        CountDownViewModel.getTimeList().observe(activity!!, Observer<MutableList<TimerModel>> {
            timerList = it

        })

    }*/


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView_top_items.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val adapter = TopItemsAdapter(requireActivity())
        recyclerView_top_items.adapter = adapter

        CountDownViewModel.getTimeList().observe(activity!!, Observer<MutableList<TimerModel>> {
            adapter.updateData(it)

            when (it.size){

                2->{

                    textLayout_name_3.visibility = View.INVISIBLE
                    textLayout_name_3.visibility = View.INVISIBLE

                    textLayout_name_4.visibility = View.INVISIBLE
                    textLayout_name_4.visibility = View.INVISIBLE

                }

                3->{

                    textLayout_name_3.visibility = View.VISIBLE
                    textLayout_name_3.visibility = View.VISIBLE

                    textLayout_name_4.visibility = View.INVISIBLE
                    textLayout_name_4.visibility = View.INVISIBLE

                }

                4->{

                    textLayout_name_3.visibility = View.VISIBLE
                    textLayout_name_3.visibility = View.VISIBLE

                    textLayout_name_4.visibility = View.VISIBLE
                    textLayout_name_4.visibility = View.VISIBLE

                }
            }


        })



        textinputeditText_name_1.setOnFocusChangeListener { view, b ->
            if (b) {
                textinputeditText_name_1.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(it.toString(), 0)
                }
            }
        }

        textinputeditText_name_2.setOnFocusChangeListener { view, b ->
            if (b) {
                textinputeditText_name_2.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(it.toString(), 1)
                }
            }
        }

        textinputeditText_name_3.setOnFocusChangeListener { view, b ->
            if (b) {
                textinputeditText_name_3.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(it.toString(), 2)

                }
            }
        }

        textinputeditText_name_4.setOnFocusChangeListener { view, b ->
            if (b) {
                textinputeditText_name_4.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(it.toString(), 3)
                }
            }
        }

    }

}