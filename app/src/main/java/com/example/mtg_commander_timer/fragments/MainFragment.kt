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
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_main_view.*
import kotlinx.android.synthetic.main.fragment_main_view.textLayout_name_4
import kotlinx.android.synthetic.main.fragment_main_view.textinputeditText_name_3
import kotlinx.android.synthetic.main.fragment_main_view.textinputeditText_name_4
import kotlinx.android.synthetic.main.fragment_main_view.view.*
import java.text.FieldPosition


//DONE TimerChangeDialog: Fragment already added error
//DONE space the chips for add minus time out
//TODO after someone dies start next timer that appears
//DONE fix when you go back main fragment the name are correct
//TODO just start new activity when you back press but presever the individual player time incase someone adds a player last second
//DONE name input fields request next focus on enter button
//TODO when last timer runs out a winner is announced with animation
//DONE circular countdown timer
//DONE create app icon (swords as a clock hands)
//VER2 try to make true circular swiping for count down activity view pager
//DONE make the padding for the margins on the cards the same for the sides as the top
//DONE edit texts request focus
//TODO implement dagger into project
//VER2 implement unit testing into project
//VER2 add view that shows everyones percentage left on clock for each view
//DONE fix the wonkey shit that happens when you go back to mainFragment after starting game
//DONE implement sound into app
//TODO what happens when the user wants to play another game
//TODO IndexOutOfBoundsException when you click back button, remove a player, then restart the game this exception is thrown on the third fragment in viewpager
//DONE when a new field is made but no name it sets one of the players to "enter name" (tried fixing by clearing text field after set invisible)
//VER2 add cool animations

class MainFragment : Fragment() {


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
            updatePLayerCount(view, it.size)
        })

        editTextHandler(textinputeditText_name_1,0)
        editTextHandler(textinputeditText_name_2,1)
        editTextHandler(textinputeditText_name_3,2)
        editTextHandler(textinputeditText_name_4,3)

    }

    private fun editTextHandler(editText: TextInputEditText, position: Int){
        editText.setOnFocusChangeListener { view, b ->
            if (b) {
                editText.doAfterTextChanged {
                    if(it.toString() != "") {
                        CountDownViewModel.setPlayerName(it.toString(), position)
                    }
                }
            }
        }

    }

    private fun updatePLayerCount(view: View, size: Int) {

        with(view) {

            when (size) {

                2 -> {
                    textLayout_name_3.visibility = View.INVISIBLE
                    textinputeditText_name_3.visibility = View.INVISIBLE
                    textinputeditText_name_3.text!!.clear()

                    textLayout_name_4.visibility = View.INVISIBLE
                    textinputeditText_name_4.visibility = View.INVISIBLE
                    textinputeditText_name_4.text!!.clear()

                }

                3 -> {
                    textLayout_name_3.visibility = View.VISIBLE
                    textinputeditText_name_3.visibility = View.VISIBLE

                    textLayout_name_4.visibility = View.INVISIBLE
                    textinputeditText_name_4.visibility = View.INVISIBLE
                    textinputeditText_name_4.text!!.clear()
                }

                4 -> {
                    textLayout_name_3.visibility = View.VISIBLE
                    textinputeditText_name_3.visibility = View.VISIBLE
                    textLayout_name_4.visibility = View.VISIBLE
                    textinputeditText_name_4.visibility = View.VISIBLE
                }
            }
        }

    }

}