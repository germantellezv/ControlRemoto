package com.example.controlremoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var mensaje:TextView? = null

    //private var editIpAddress: EditText?=null
    private var puerto:EditText?=null

    private var Conectar:Button?= null
    private var txvDebug: TextView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mensaje = findViewById(R.id.textView3) as TextView
        var editIpAddress = findViewById(R.id.editIpAddress) as EditText
        Conectar = findViewById(R.id.ButtonConnect) as Button
        puerto = findViewById(R.id.editPort) as EditText
        txvDebug = findViewById(R.id.txvDebug) as TextView

        // Configurar direccion ip y puerto de manera centralizada
        var api_ip = editIpAddress.text
        var api_port = puerto?.text

        Conectar?.setOnClickListener{

            /*var url:String="http://$api_ip:$api_port/api/v1/login"
            var texto = "Hola, soy $api_ip"
            txvDebug?.text=url*/
            var url="http://$api_ip:$api_port/api/v1/login"
                .httpGet().responseJson{ request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            Toast.makeText(this,"Error de conexión", Toast.LENGTH_SHORT).show()
                            mensaje!!.text = result.get().toString()
                        }

                        is Result.Success -> {
                            //var token:String? = ""
                            var aux: JSONObject = JSONObject()
                            aux = result.get().obj()
                            var token:String = aux.getString("token")
                            //mensaje!!.text = result.get().obj().toString()

                            var ipaddress = api_ip.toString()
                            var port = api_port?.toString()

                            val intent = Intent(this,Main2Activity::class.java)
                            intent.putExtra("token",token)
                            intent.putExtra("ipaddress",ipaddress)
                            intent.putExtra("port",port)
                            startActivity(intent)
                        }
                    }
                }
        }
    }
}
