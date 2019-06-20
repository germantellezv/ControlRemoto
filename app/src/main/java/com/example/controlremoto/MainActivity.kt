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
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var Conectar:Button?= null
    private var mensaje:TextView? = null
    private var dirip: EditText?=null
    private var port:EditText?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mensaje = findViewById(R.id.textView3) as TextView
        dirip = findViewById(R.id.ipAddress) as EditText
        Conectar = findViewById(R.id.ButtonConnect) as Button
        port = findViewById(R.id.editPort) as EditText

        // Configurar direccion ip y puerto de manera centralizada
        var ipaddress = dirip?.text
        var puerto = port?.text

        Conectar?.setOnClickListener{

            var url="http://$ipaddress:$puerto/api/v1/login"
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

                            val intent = Intent(this,Main2Activity::class.java)
                            intent.putExtra("token",token)
                            startActivity(intent)
                        }
                    }
                }
        }
    }
}
