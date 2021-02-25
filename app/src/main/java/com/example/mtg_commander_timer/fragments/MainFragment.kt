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