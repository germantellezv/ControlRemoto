package com.example.controlremoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var Conectar:Button?= null
    private var mensaje:TextView? = null
    private var ajustes:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mensaje = findViewById(R.id.textView3) as TextView
        ajustes = findViewById(R.id.btnAjustes) as Button
        Conectar = findViewById(R.id.ButtonConnect) as Button
        Conectar?.setOnClickListener{

            var url="http://10.3.141.1:5000/api/v1/login"
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
        ajustes?.setOnClickListener{
            val intent = Intent(this,Main3Activity::class.java)
            startActivity(intent)
        }




    }
}
