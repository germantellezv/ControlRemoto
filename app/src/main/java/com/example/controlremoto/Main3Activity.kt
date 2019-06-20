package com.example.controlremoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    // variable del menu desplegable
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        //inicio del menu desplegable
        toggle = ActionBarDrawerToggle(this,basicoDrawerLayout,R.string.open,R.string.close)
        basicoDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        basicoNavigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_basico -> {
                    startActivity(Intent(this@Main3Activity,Main3Activity::class.java))
                    true
                }
                R.id.menu_avanzado -> {
                    startActivity(Intent(this@Main3Activity,Main2Activity::class.java))
                    true
                }

                else -> false

            }
        } // fin del menu desplegable

    }

    // funcion del menu desplegable
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

