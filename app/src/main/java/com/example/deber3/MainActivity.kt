package com.example.deber3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun pedApi(view: View){

        val lblBancos = findViewById<TextView>(R.id.lblBancos)
        val url = "https://api-uat.kushkipagos.com/payouts/transfer/v1/bankList"

        val colaPetVoll = Volley.newRequestQueue(this)

        val request = object : StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    val jsonArRes=JSONArray(response)
                    lblBancos.text = parcJsonBanc(jsonArRes)
                } catch (e: Exception) {
                    Log.d("Error: ", e.toString())
                    lblBancos.text = "Ups. Ha ocurrido un error"
                }
            },
            Response.ErrorListener {
                Log.d("Error: ", it.toString())
                lblBancos.text = "Ups. Ha ocurrido un error"
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Public-Merchant-Id"] = "b731f14fb3f64274a1d5411575c624fe"
                return headers
            }
        }
        colaPetVoll.add(request)
    }

    fun parcJsonBanc(jsonArray: JSONArray): String {

        var listBanc: String = ""
        for (i in 0 until jsonArray.length()) {
            var nomBanc = jsonArray.getJSONObject(i).getString("name").toString()
            listBanc += nomBanc +"\n\n"
        }
        return listBanc
    }

}