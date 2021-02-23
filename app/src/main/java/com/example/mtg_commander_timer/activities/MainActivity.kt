package com.example.mtg_commander_timer.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.mtg_commander_timer.R
import com.example.mtg_commander_timer.fragments.CountdownViewPagerFragment
import com.example.mtg_commander_timer.fragments.MainFragment
import com.example.mtg_commander_timer.log
import com.example.mtg_commander_timer.models.CountDownViewModel

class MainActivity : AppCompatActivity() {
    var pressing: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        supportFragmentManager.beginTransaction().replace(R.id.framelayout_main, MainFragment()).addToBackStack("MainFragment").commit()
    }

    override fun onBackPressed() {
        CountDownViewModel.stopTimer()
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu_start_pause, menu)
        mainMenu = menu!!
        MainActivity.pauseIconEnabled(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.start_timer -> {
                if (firstTimeSet) {
                    if (supportFragmentManager.backStackEntryCount == 1) {
                        supportFragmentManager.beginTransaction().replace(R.id.framelayout_main, CountdownViewPagerFragment()).addToBackStack("CountdownFragment").commit()

                        if (pressing) {
                            pressing = false
                            Handler().postDelayed({

                                if(soundOn) {

                                   /* var mediaPlayer = MediaPlayer.create(this, R.raw.loading)
                                    mediaPlayer.start()*/
                                }

                                CountDownViewModel.setTimer(0)
                                CountDownViewModel.startTimer()
                                pressing = true
                            }, 800)
                        }

                    } else {
                        if (pressing) {
                            pressing = false
                            Handler().postDelayed({
                                CountDownViewModel.setTimer(currentFragNum)
                                CountDownViewModel.startTimer()
                                pressing = true
                            }, 800)
                        }
                    }
                } else {
                    Toast.makeText(this, "Set time to start", Toast.LENGTH_LONG).show()
                }
            }

            R.id.pause_timer -> {

                if (pressing) {

                    pressing = false

                    CountDownViewModel.stopTimer()
                    Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show()

                    Handler().postDelayed({
                        pressing = true
                    }, 800)
                }
            }
        }
        return true
    }


    companion object {
        var currentFragNum = 0
        var firstTimeSet = false
        var battleTime: Long = 60000
        var removeFrag: Boolean = false
        var mainTime: Long = 0
        var soundOn = false
        lateinit var mainMenu: Menu
        val mediaPlayer = MediaPlayer()


        fun test (){

        }



        fun pauseIconEnabled(pauseEnabled: Boolean) {
            if (::mainMenu.isInitialized) {
                mainMenu.findItem(R.id.pause_timer).isVisible = pauseEnabled
            }
        }


    }
}