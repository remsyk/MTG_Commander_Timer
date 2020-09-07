package com.example.mtg_commander_timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    var pressing: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        supportFragmentManager.beginTransaction().replace(R.id.framelayout_main, MainFragment())
            .addToBackStack("MainFragment").commit()
    }

    override fun onBackPressed() {
        CountDownViewModel.stopTimer()
        if(supportFragmentManager.backStackEntryCount ==1){
            finish()
        }else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu_start_pause, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.start_timer -> {
                if(supportFragmentManager.backStackEntryCount==1) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout_main, CountdownViewPagerFragment())
                        .addToBackStack("CountdownFragment").commit()

                    if(pressing){
                        pressing = false
                        Handler().postDelayed({
                            CountDownViewModel.setTimer(0)
                            CountDownViewModel.startTimer()
                            pressing = true
                        }, 1000)
                    }

                }else {
                    if(pressing){
                        pressing = false
                        Handler().postDelayed({
                            CountDownViewModel.setTimer(currentFragNum)
                            CountDownViewModel.startTimer()
                            pressing = true
                        }, 1000)
                    }
                }
            }

            R.id.pause_timer -> {
               CountDownViewModel.stopTimer()
                Toast.makeText(this,"Paused", Toast.LENGTH_LONG).show()

            }
        }
        return true
    }

    companion object {
        var currentFragNum = 0
    }

}