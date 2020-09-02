package com.example.mtg_commander_timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        supportFragmentManager.beginTransaction().replace(R.id.framelayout_main, MainFragment())
            .addToBackStack("MainFragment").commit()
    }

    override fun onBackPressed() {
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
                "start timer pressed".log()
                supportFragmentManager.beginTransaction().replace(R.id.framelayout_main, CountdownViewPagerFragment()).addToBackStack("CountdownFragment").commit()
                //timerList?.iterator().forEach { it.name.log() }
            }

            R.id.pause_timer -> {
                "pause timer pressed".log()
                Toast.makeText(this,"Paused", Toast.LENGTH_LONG).show()

            }
        }
        return true
    }

    companion object{
         var timerList: ArrayList<TimerModel> = arrayListOf()
    }
}