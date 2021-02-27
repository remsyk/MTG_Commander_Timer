package com.example.mtg_commander_timer.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mtg_commander_timer.R
import com.example.mtg_commander_timer.dialogs.PauseDialog
import com.example.mtg_commander_timer.fragments.CountdownViewPagerFragment
import com.example.mtg_commander_timer.fragments.MainFragment
import com.example.mtg_commander_timer.log
import com.example.mtg_commander_timer.models.CountDownViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds


//DONE TimerChangeDialog: Fragment already added error
//DONE space the chips for add minus time out
//TODO after someone dies start next timer that appears
//DONE fix when you go back main fragment the name are correct
//DONE just start new activity when you back press but presever the individual player time incase someone adds a player last second
//DONE name input fields request next focus on enter button
//VER2 when last timer runs out a winner is announced with animation
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
//DONE what happens when the user wants to play another game
//DONE IndexOutOfBoundsException when you click back button, remove a player, then restart the game this exception is thrown on the third fragment in viewpager
//DONE when a new field is made but no name it sets one of the players to "enter name" (tried fixing by clearing text field after set invisible)
//VER2 add cool animations
//DONE add restart game on pause dialog
//DONE add add space on bottom of the app
//TODO add comments
//TODO fix sound issue media player
//DONE player timer runs out and then reset the game and then set time to below 1 min and restart the game, no player lost is announced and game doesnt reset



class MainActivity : AppCompatActivity() {
    var pressing: Boolean = true
    private lateinit var mInterstitialAd: InterstitialAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        supportFragmentManager.beginTransaction().replace(R.id.framelayout_main, MainFragment()).addToBackStack("MainFragment").commit()


        MobileAds.initialize(this) {}
        mInterstitialAd = InterstitialAd(this)

        //TODO change on deployment from test ca-app
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())


    }

    //needs  to stop timer and and ensure that when there is only one fragment left exit the app instead of entering a empty activity
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
                //allow button to be pressed after user set battle time
                if (firstTimeSet) {
                    if (supportFragmentManager.backStackEntryCount == 1) {
                        supportFragmentManager.beginTransaction().replace(R.id.framelayout_main, CountdownViewPagerFragment()).addToBackStack("CountdownFragment").commit()

                        if (pressing) {
                            pressing = false
                            Handler().postDelayed({

                                if (soundOn) {

                                    /* var mediaPlayer = MediaPlayer.create(this, R.raw.loading)
                                    mediaPlayer.start()*/
                                }

                                CountDownViewModel.setTimer(0)
                                CountDownViewModel.startTimer()
                                pressing = true
                            }, 800)
                        }
                    //cant let user smash start button or to many timer threads will be started
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
                    //else toast message telling them to set a time to start
                } else {
                    Toast.makeText(this, "Set time to start", Toast.LENGTH_LONG).show()
                }
            }

            R.id.pause_timer -> {

                if (pressing) {

                    pressing = false

                    CountDownViewModel.stopTimer()
                    //Show pause dialog to allow user to reset game and know that game is paused
                    PauseDialog.show(supportFragmentManager).getValue={
                        if(it){
                            if(mInterstitialAd.isLoaded){
                                mInterstitialAd.show()
                            }else{
                                "ad failed to load".log()
                            }
                        }

                    }

                    Handler().postDelayed({
                        pressing = true
                    }, 800)
                }
            }
        }
        return true
    }


    companion object {
        var currentFragNum = 0 //current fragment in view
        var firstTimeSet = false //did the user set the first battle timer
        var battleTime: Long = 60000 //time for when player presses battle
        var removeFrag: Boolean = false //not sure what this is
        var mainTime: Long = 0//this is the  main countdown time
        var soundOn = false
        lateinit var mainMenu: Menu

        //dont show pause icon before game has started, dont need the functionality yet
        fun pauseIconEnabled(pauseEnabled: Boolean) {
            if (::mainMenu.isInitialized) {
                mainMenu.findItem(R.id.pause_timer).isVisible = pauseEnabled
            }
        }


    }
}