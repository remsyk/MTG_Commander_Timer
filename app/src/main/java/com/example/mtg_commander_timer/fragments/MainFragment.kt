package com.example.mtg_commander_timer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mtg_commander_timer.*
import com.example.mtg_commander_timer.adapters.PlayerListAdapterRecyclerView
import com.example.mtg_commander_timer.adapters.TopItemsAdapter
import com.example.mtg_commander_timer.dialogs.TimeChangeDialog
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
    var timerList: MutableList<TimerModel> = mutableListOf<TimerModel>(TimerModel("Enter Name", 0, null, true), TimerModel("Enter Name", 0, null, true))

    //TODO use this for the bot trader
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        CountDownViewModel.getTimeList().observe(activity!!, Observer<MutableList<TimerModel>> {
            timerList = it

        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val root = inflater.inflate(R.layout.fragment_main_view, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()


        //textview_timer.text = MainActivity.mainTime.millisToString()

        /* include_enter_name_1.textLayout_name.hint = "Enter Name"
         include_enter_name_2.textLayout_name.hint = "Enter Name"

         include_enter_name_1.button_timer.text = timerList.get(0).countdownTime.millisToString()
         include_enter_name_2.button_timer.text = timerList.get(1).countdownTime.millisToString()


         when (timerList.size) {

             3 -> {


                 include_enter_name_3.textLayout_name.hint = "Enter Name"
                 include_enter_name_3.button_timer.text = timerList.get(2).countdownTime.millisToString()
                 include_enter_name_3.visibility = View.VISIBLE
             }

             4 -> {
                 include_enter_name_3.textLayout_name.hint = "Enter Name"
                 include_enter_name_3.button_timer.text = timerList.get(2).countdownTime.millisToString()


                 include_enter_name_4.button_timer.text = timerList.get(3).countdownTime.millisToString()
                 include_enter_name_4.textLayout_name.hint = "Enter Name"

                 include_enter_name_3.visibility = View.VISIBLE
                 include_enter_name_4.visibility = View.VISIBLE
             }
         }*/
    }


    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView_top_items.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val adapter = TopItemsAdapter(requireActivity())
        recyclerView_top_items.adapter = adapter


        recyclerView_player_list.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        val playersAdapter = PlayerListAdapterRecyclerView(requireActivity())
        recyclerView_top_items.adapter = adapter


        CountDownViewModel.getTimeList().observe(activity!!, Observer<MutableList<TimerModel>> {
            adapter.updateData(it)
            playersAdapter.updateData(it)
        })


        var temp2 = timerList.size

        //picker_player_num.maxValue = 4
        //picker_player_num.minValue = CountDownViewModel.getTimeList().value!!.size

        /*   include_enter_name_1.textLayout_name.hint = timerList.get(0).name
           include_enter_name_2.textLayout_name.hint = timerList.get(1).name

           include_enter_name_1.button_timer.text = timerList.get(0).countdownTime.millisToString()
           include_enter_name_2.button_timer.text = timerList.get(1).countdownTime.millisToString()

           when (timerList.size) {

               3 -> {
                   include_enter_name_3.textLayout_name.hint = timerList.get(2).name
                   include_enter_name_3.button_timer.text = timerList.get(2).countdownTime.millisToString()
                   include_enter_name_4.button_timer.text = timerList.get(3).countdownTime.millisToString()
                   include_enter_name_3.visibility = View.VISIBLE
               }

               4 -> {
                   include_enter_name_4.textLayout_name.hint = timerList.get(3).name
                   include_enter_name_3.button_timer.text = timerList.get(2).countdownTime.millisToString()
                   include_enter_name_4.button_timer.text = timerList.get(3).countdownTime.millisToString()
                   include_enter_name_3.visibility = View.VISIBLE
                   include_enter_name_4.visibility = View.VISIBLE
               }
           }*/


        /*include_enter_name_1.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if (b) {
                include_enter_name_1.textinputeditText_name.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(it.toString(), 0)
                }
            }
        }


        include_enter_name_2.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if (b) {
                include_enter_name_2.textinputeditText_name.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(it.toString(), 1)
                }
            }
        }

        include_enter_name_3.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if (b) {
                include_enter_name_3.textinputeditText_name.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(it.toString(), 2)

                }
            }
        }

        include_enter_name_4.textinputeditText_name.setOnFocusChangeListener { view, b ->
            if (b) {
                include_enter_name_4.textinputeditText_name.doAfterTextChanged {
                    CountDownViewModel.setPlayerName(it.toString(), 3)
                }
            }
        }*/







      //  picker_player_num.setOnValueChangedListener { numberPicker, i, i2 ->

            // var tempSize = timerList.size
           /* when (i2) {

                2 -> {
                    *//*   include_enter_name_3.visibility = View.GONE
                       include_enter_name_4.visibility = View.GONE*//*


                    if (timerList.size != 2) {
                        CountDownViewModel.removeRangePlayer(2)
                    }
                }

                3 -> {
                    *//*   include_enter_name_3.visibility = View.VISIBLE
                       include_enter_name_4.visibility = View.GONE*//*


                    CountDownViewModel.addPlayer(TimerModel("Enter Name", 0, null, true))


                    if (timerList.size != 3 || timerList.size == 4) {

                        CountDownViewModel.removeRangePlayer(3)
                    }
                }

                4 -> {


                    *//* include_enter_name_3.visibility = View.VISIBLE
                     include_enter_name_4.visibility = View.VISIBLE*//*

                    if (timerList.size == 3) {

                        CountDownViewModel.addPlayer(TimerModel("Enter Name", 0, null, true))

                    }

                }
            }*/


        }

        /* include_enter_name_1.button_timer.setOnClickListener {
             TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                 include_enter_name_1.button_timer.text = value.millisToString()
                 timerList[0].countdownTime = value
             }
         }

         include_enter_name_2.button_timer.setOnClickListener {
             TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                 include_enter_name_2.button_timer.text = value.millisToString()
                 timerList[1].countdownTime = value
             }
         }

         include_enter_name_3.button_timer.setOnClickListener {
             TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                 include_enter_name_3.button_timer.text = value.millisToString()
                 timerList[2].countdownTime = value
             }
         }

         include_enter_name_4.button_timer.setOnClickListener {
             TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                 include_enter_name_4.button_timer.text = value.millisToString()
                 timerList[3].countdownTime = value
             }
         }*/

        /*textview_timer.setOnClickListener {
            TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                MainActivity.mainTime = value
                textview_timer.text = value.millisToString()
                MainActivity.firstTimeSet = true

                timerList.iterator().forEach {
                    it.countdownTime = value
                }

                for (x in 0..timerList.size - 1) {
                    CountDownViewModel.setPlayerTime(timerList[x].countdownTime, x)
                }

                *//* include_enter_name_1.button_timer.text = value.millisToString()
                 include_enter_name_2.button_timer.text = value.millisToString()
                 include_enter_name_3.button_timer.text = value.millisToString()
                 include_enter_name_4.button_timer.text = value.millisToString()*//*

            }
        }

        textview_battle_time.text = MainActivity.battleTime.millisToMinString()
        textview_battle_time.setOnClickListener {
            TimeChangeDialog.show(requireFragmentManager()).getValue = { value ->
                textview_battle_time.text = value.millisToMinString()
                MainActivity.battleTime = value

            }
        }*/
    }
}