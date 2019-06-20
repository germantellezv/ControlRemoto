package com.example.controlremoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONObject

class Main2Activity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    //variable del menu desplegable
    lateinit var toggle: ActionBarDrawerToggle


    private var tiempo: Float? = null
    private var velocidad:Int? = null
    private var titulo: TextView?=null
    private var token:String? = null
    private var ipaddress:String?=null
    private var port:String?=null
    
    
    // RadioButtons de la dirección de los motores
    private var radioF: RadioButton? = null
    private var radioR: RadioButton? = null
    private var direccion: String? = null
    // RadioButtons del Motor
    private var radioMI:RadioButton?=null
    private var radioMD: RadioButton?=null
    private var radioAm:RadioButton?=null
    private var motor:String?= null
    // Boton Ejecutar, Detener y Desconectar
    private var ejecutar:Button? = null
    private var detener:Button?=null
    private var desconectar: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        titulo = findViewById(R.id.textView6) as TextView
        radioF = findViewById(R.id.radioButton_F) as RadioButton
        radioR = findViewById(R.id.radioButton_R) as RadioButton
        radioMI= findViewById(R.id.radioButton_left) as RadioButton
        radioMD= findViewById(R.id.radioButton_right) as RadioButton
        radioAm= findViewById(R.id.radioButton_both) as RadioButton
        ejecutar = findViewById(R.id.button) as Button
        detener = findViewById(R.id.button2) as Button
        desconectar = findViewById(R.id.button3) as Button

        // Comportamiento de los RadioButton de la dirección del motor
        radioF!!.setOnCheckedChangeListener(this)
        radioR!!.setOnCheckedChangeListener(this)
        
        
        // Comportamiento de los RadioButton de los motores
        radioMI!!.setOnCheckedChangeListener(this)
        radioMD!!.setOnCheckedChangeListener(this)
        radioAm!!.setOnCheckedChangeListener(this)
        
        
        // La variable token contiene el token de una peticion GET del activity anterior
        // La variable ipaddress contiene la Direccion IP a donde se realizaran las peticiones
        // La variable port contiene el puerto para realizar las peticiones
        val objetoIntent: Intent=intent
        token = objetoIntent.getStringExtra("token")
        ipaddress = objetoIntent.getStringExtra("ipaddress")
        port = objetoIntent.getStringExtra("portNumber")
        
        ejecutar!!.setOnClickListener(this)
        detener!!.setOnClickListener{
            var url="http://$ipaddress:$port/api/v1/stop?token=$token"
                .httpGet().responseJson{ request, response, result ->
                    when (result) {
                        is Result.Failure -> {println("hubo un fallo")
                        }
                        is Result.Success -> {
                            //titulo!!.text=result.get().toString()
                            var aux: JSONObject = JSONObject()
                            aux = result.get().obj()
                            var aux2:String= aux.getString("message")
                            titulo!!.text = aux2.toString()
                        }
                    }
                }

        }
        desconectar!!.setOnClickListener{
            var url="http://$ipaddress:$port/api/v1/logout"
                .httpGet().responseJson{ request, response, result ->
                    when (result) {
                        is Result.Failure -> {println("hubo un fallo")
                        }
                        is Result.Success -> {
                            //titulo!!.text=result.get().toString()
                            token = ""
                            var aux: JSONObject = JSONObject()
                            aux = result.get().obj()
                            var aux2:String= aux.getString("message")
                            titulo!!.text = aux2.toString()
                        }
                    }
                }
        }
        // inicio codigo del menu desplegable
        toggle = ActionBarDrawerToggle(this,avanzadoDrawerLayout,R.string.open,R.string.close)
        avanzadoDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        avanzadoNavigationView.setNavigationItemSelectedListener {
            when(it.itemId){
            R.id.menu_basico -> {
                startActivity(Intent(this@Main2Activity,Main3Activity::class.java))
                true
            }
                R.id.menu_avanzado -> {
                    startActivity(Intent(this@Main2Activity,Main2Activity::class.java))
                    true
                }

                else -> false


        }
        } // fin del codigo del menu desplegable

    }

    override fun onClick(p0: View?) {


        // Aqui podemos modificar/apuntar la dirección IP de la API
        var baseUrl:String = "http://$ipaddress:$port/api/v1/move"

        //var url="htpp://$ipaddress:$port/api/v1/move/$motor/$direccion/$velocidad/$tiempo"+"?token=$token"
        if ( editVelocidad.text.isEmpty() && !editTiempo.text.isEmpty()){

            tiempo = editTiempo?.text.toString().toFloat()

            var url="$baseUrl/$motor/$direccion/$tiempo?token=$token"
                .httpGet().responseJson{ request, response, result ->
                    when (result) {
                        is Result.Failure -> {println("hubo un fallo")
                        }
                        is Result.Success -> {
                            //titulo!!.text=result.get().toString()
                            var aux: JSONObject = JSONObject()
                            aux = result.get().obj()
                            var aux2:String= aux.getString("message")
                            titulo!!.text = aux2.toString()
                        }
                    }
                }
        } else if(!editVelocidad.text.isEmpty() && editTiempo.text.isEmpty()){

            velocidad = editVelocidad?.text.toString().toInt()

            var url="$baseUrl/$motor/$direccion/$velocidad?token=$token"
                .httpGet().responseJson{ request, response, result ->
                    when (result) {
                        is Result.Failure -> {println("hubo un fallo")
                        }
                        is Result.Success -> {
                            //titulo!!.text=result.get().toString()
                            var aux: JSONObject = JSONObject()
                            aux = result.get().obj()
                            var aux2:String= aux.getString("message")
                            titulo!!.text = aux2.toString()
                        }
                    }
                }
        } else if(editVelocidad.text.isEmpty() && editTiempo.text.isEmpty()){
            var url="$baseUrl/$motor/$direccion?token=$token"
                .httpGet().responseJson{ request, response, result ->
                    when (result) {
                        is Result.Failure -> {println("hubo un fallo")
                        }
                        is Result.Success -> {
                            //titulo!!.text=result.get().toString()
                            var aux: JSONObject = JSONObject()
                            aux = result.get().obj()
                            var aux2:String= aux.getString("message")
                            titulo!!.text = aux2.toString()

                        }
                    }
                }
        }else{

            velocidad = editVelocidad?.text.toString().toInt()
            tiempo = editTiempo?.text.toString().toFloat()
            var url="$baseUrl/$motor/$direccion/$velocidad/$tiempo?token=$token"
                .httpGet().responseJson{ request, response, result ->
                    when (result) {
                        is Result.Failure -> {println("hubo un fallo")
                        }
                        is Result.Success -> {
                            var aux: JSONObject = JSONObject()
                            aux = result.get().obj()
                            var aux2:String= aux.getString("message")
                            titulo!!.text = aux2.toString()
                        }
                    }
                }
        }
    }
    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when(p0!!.id){
            R.id.radioButton_F -> {
                if(p1)
                    direccion = "forward"
                //Toast.makeText(this,"Ha seleccionado la opción $direccion", Toast.LENGTH_SHORT).show()
            }

            R.id.radioButton_R -> {
                if (p1)
                    direccion = "reverse"
                //Toast.makeText(this,"Ha seleccionado la opcion $direccion", Toast.LENGTH_SHORT).show()
            }
            R.id.radioButton_left -> {
                if (p1)
                    motor = "left"
                //Toast.makeText(this,"You've selected the $motor engine", Toast.LENGTH_SHORT).show()
            }
            R.id.radioButton_right -> {
                if (p1)
                    motor = "right"
                //Toast.makeText(this,"You've selected the $motor engine", Toast.LENGTH_SHORT).show()
            }
            R.id.radioButton_both -> {
                if (p1)
                    motor = "both"
                //Toast.makeText(this,"You have selected $motor engines", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // funcion del menu desplegable
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
