package com.example.flydest.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.flydest.R
import com.example.flydest.Services.MessageService
import com.example.flydest.Services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var message : TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.message);
//        message.text = "Black Friday! Get 50% cash back on saving your first spot."
        val messageService = ServiceBuilder.buildService(MessageService::class.java)
        val requestCall = messageService.getMessages(ServiceBuilder.ALTERNATE_URL+"messages")
        requestCall.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    val msg = response.body()
                    message.text = msg
                } else{
                    Toast.makeText(this@MainActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("error", "${t.message}")
                Toast.makeText(this@MainActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
            }

        })
    }
    fun getStarted(view : View){
        val intent = Intent(this, DestinationListActivity::class.java)
        startActivity(intent);
        finish()
    }
}