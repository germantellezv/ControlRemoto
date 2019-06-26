package com.example.controlremoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity(), JoystickView.JoystickListener {

    // variable del menu desplegable
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val joystick = JoystickView(this)
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

    override fun onJoystickMoved(xPercent: Float, yPercent: Float, id: Int) {

        when (id) {

            R.id.joystickLeft ->
            {
                Log.d("Left Joystick", "X percent: $xPercent Y percent: $yPercent")

                if ((yPercent < 0.3 && yPercent > -0.3) && (xPercent > 0.0 && xPercent < 1.0 ))
                {
                    tvLog.text = "DERECHA"
                }
                if ((yPercent < 0.3 && yPercent > -0.3) && (xPercent < 0.0 && xPercent > -1.0 ))
                {
                    tvLog.text = "IZQUIERDA"
                }
                if ((yPercent > -1.0 && yPercent < 0.0) && (xPercent > -0.3 && xPercent < 0.3 ))
                {
                    tvLog.text = "AVANZAR"
                }
                if ((yPercent > 0.0 && yPercent < 1.0) && (xPercent > -0.3 && xPercent < 0.3 ))
                {
                    tvLog.text = "REVERSA"
                }
                if (yPercent == 0F && xPercent == 0F)
                {
                    tvLog.text = ""
                }



            }

        }
    }



}

