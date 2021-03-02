package com.example.mtg_commander_timer.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.example.mtg_commander_timer.*
import com.example.mtg_commander_timer.activities.MainActivity
import com.example.mtg_commander_timer.activities.MainActivity.Companion.removeFrag
import com.example.mtg_commander_timer.activities.MainActivity.Companion.soundOn
import com.example.mtg_commander_timer.models.CountDownViewModel
import com.example.mtg_commander_timer.models.TimerModel

/**
 * The number of pages (wizard steps) to show in this demo.
 */

class CountdownViewPagerFragment : Fragment() {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private lateinit var demoCollectionPagerAdapter: DemoCollectionPagerAdapter
    private lateinit var viewPager: ViewPager
    lateinit var mediaPlayer: MediaPlayer
    var isTheMediaPlayerNotPlaying = true

    override fun onPause() {

        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()

        }
        super.onPause()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_countdown_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionPagerAdapter = DemoCollectionPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionPagerAdapter


        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.countdown)


        CountDownViewModel.getPlayerList().observe(activity!!, Observer<MutableList<TimerModel>> {
            demoCollectionPagerAdapter.updateData(it)


                //start audio when user has less than 1min left and sound is on and the mediaplayer isn't already playing
            if (CountDownViewModel.getProgress(MainActivity.currentFragNum) < 60000 && soundOn && isTheMediaPlayerNotPlaying) {

                mediaPlayer.start()
                isTheMediaPlayerNotPlaying = false

            }


            //If the timer goes to 0 which wont happen so its set to 1000, then begin the remove player protocol
            if (removeFrag) {

                 Toast.makeText(requireContext(), "${CountDownViewModel.getPLayerName(MainActivity.currentFragNum)} Died", Toast.LENGTH_LONG).show()

                //if there is only player left then the game ends
                if(CountDownViewModel.getPlayerList().value!!.size == 1){

                    Toast.makeText(requireContext(), "${CountDownViewModel.getPLayerName(0)} Won", Toast.LENGTH_LONG).show()

                    activity!!.supportFragmentManager!!.beginTransaction().remove(CountdownViewPagerFragment()).commit()
                    activity!!.supportFragmentManager!!.popBackStack()


                    CountDownViewModel.clearPlayers()
                    MainActivity.mainTime = 0 //reset main time

                }

                removeFrag = false
            }

        })

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            var currentPage: Int = 0
            var mPreviousPosition: Int = 0
            var mIsEndOfCycle = false

            override fun onPageScrollStateChanged(state: Int) {
                val pageCount = viewPager?.adapter?.count


                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mPreviousPosition == currentPage && !mIsEndOfCycle) {
                        if (currentPage == 0) {
                            pageCount?.minus(1)?.let { viewPager?.setCurrentItem(it, false) };
                        } else {
                            viewPager?.setCurrentItem(0, true);
                        }
                        mIsEndOfCycle = true;
                    } else {
                        mIsEndOfCycle = false;
                    }
                    mPreviousPosition = currentPage;
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                currentPage = position

                isTheMediaPlayerNotPlaying = true //stop current media player playing when page changes

                if (::mediaPlayer.isInitialized) {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                    }
                }

                MainActivity.currentFragNum = position


                //play swipe sound when swipping
                if (MainActivity.soundOn) {
                    var mediaPlayer = MediaPlayer.create(context, R.raw.swipe)
                    mediaPlayer.start()
                }


                CountDownViewModel.stopTimer()
                CountDownViewModel.setTimer(position)
                CountDownViewModel.startTimer()
            }
        })
    }
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class DemoCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var data = CountDownViewModel.getPlayerList().value!!

    override fun getCount(): Int = data.size

    override fun getItem(i: Int): Fragment {
        val fragment = CountdownFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer
            putInt("FRG_POSITION", i)
        }
        return fragment
    }

    //update data from view model for when players are added or removed
    fun updateData(newData: MutableList<TimerModel>) {
        data = newData
        notifyDataSetChanged()

    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}





