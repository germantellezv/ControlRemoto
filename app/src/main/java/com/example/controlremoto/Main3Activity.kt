package com.example.controlremoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main3.*
import org.json.JSONObject

class Main3Activity : AppCompatActivity(), JoystickView.JoystickListener {

    // variable del menu desplegable
    lateinit var toggle: ActionBarDrawerToggle

    // variables de datos de conexion a la api
    private var token:String? = null
    private var ipaddress:String?=null
    private var port:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val joystick = JoystickView(this)
        setContentView(R.layout.activity_main3)

        // obteniendo datos de conexion a la API
        val objetoIntent: Intent=intent
        token = objetoIntent.getStringExtra("token")
        ipaddress = objetoIntent.getStringExtra("ipaddress")
        port = objetoIntent.getStringExtra("port")


        //inicio del menu desplegable
        toggle = ActionBarDrawerToggle(this,basicoDrawerLayout,R.string.open,R.string.close)
        basicoDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        basicoNavigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_joystick -> {
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



            R.id.joystick ->
            {

                Log.d("Left Joystick", "X percent: $xPercent Y percent: $yPercent")
                // Aqui podemos modificar/apuntar la dirección IP de la API
                var baseUrl:String = "http://$ipaddress:$port/api/v1/move"


                if ((yPercent < 0.3 && yPercent > -0.3) && (xPercent > 0.0 && xPercent < 1.0 ))
                {
                    //tvLog.text = "DERECHA"
                    var url="$baseUrl/left/forward/100/0.1?token=$token"
                        .httpGet().responseJson{ request, response, result ->
                            when (result) {
                                is Result.Failure -> {println("hubo un fallo")
                                }
                                is Result.Success -> {
                                    var aux: JSONObject = JSONObject()
                                    aux = result.get().obj()
                                    var aux2:String= aux.getString("message")
                                    tvLog?.text = aux2.toString()
                                }
                            }
                        }
                }
                if ((yPercent < 0.3 && yPercent > -0.3) && (xPercent < 0.0 && xPercent > -1.0 ))
                {
                    //tvLog.text = "IZQUIERDA"
                    var url="$baseUrl/right/forward/100/0.1?token=$token"
                        .httpGet().responseJson{ request, response, result ->
                            when (result) {
                                is Result.Failure -> {println("hubo un fallo")
                                }
                                is Result.Success -> {
                                    var aux: JSONObject = JSONObject()
                                    aux = result.get().obj()
                                    var aux2:String= aux.getString("message")
                                    tvLog?.text = aux2.toString()
                                }
                            }
                        }

                }
                if ((yPercent > -1.0 && yPercent < 0.0) && (xPercent > -0.3 && xPercent < 0.3 ))
                {
                    //tvLog.text = "AVANZAR"
                    var url="$baseUrl/both/forward/100/0.1?token=$token"
                        .httpGet().responseJson{ request, response, result ->
                            when (result) {
                                is Result.Failure -> {println("hubo un fallo")
                                }
                                is Result.Success -> {
                                    var aux: JSONObject = JSONObject()
                                    aux = result.get().obj()
                                    var aux2:String= aux.getString("message")
                                    tvLog?.text = aux2.toString()
                                }
                            }
                        }
                }
                if ((yPercent > 0.0 && yPercent < 1.0) && (xPercent > -0.3 && xPercent < 0.3 ))
                {
                    //tvLog.text = "REVERSA"
                    var url="$baseUrl/both/reverse/100/0.1?token=$token"
                        .httpGet().responseJson{ request, response, result ->
                            when (result) {
                                is Result.Failure -> {println("hubo un fallo")
                                }
                                is Result.Success -> {
                                    var aux: JSONObject = JSONObject()
                                    aux = result.get().obj()
                                    var aux2:String= aux.getString("message")
                                    tvLog?.text = aux2.toString()
                                }
                            }
                        }
                }
                if (yPercent == 0F && xPercent == 0F)
                {
                    tvLog.text = "CENTRO"
                }



            }

        }
    }



}

