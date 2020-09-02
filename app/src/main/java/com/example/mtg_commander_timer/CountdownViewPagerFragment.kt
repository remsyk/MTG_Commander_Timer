package com.example.mtg_commander_timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mtg_commander_timer.MainActivity.Companion.timerList

/**
 * The number of pages (wizard steps) to show in this demo.
 */

class CountdownViewPagerFragment : Fragment() {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private lateinit var demoCollectionPagerAdapter: DemoCollectionPagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countdown_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionPagerAdapter = DemoCollectionPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionPagerAdapter

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            var currentPage : Int = 0
            var mPreviousPosition : Int = 0
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

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

        })
    }
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class DemoCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = timerList.size

    override fun getItem(i: Int): Fragment {
        val fragment = CountdownFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt("FRG_POSITION", i)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}




