package com.example.flydest.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.flydest.Adapters.SampleData
import com.example.flydest.R
import com.example.flydest.Services.DestinationService
import com.example.flydest.Services.ServiceBuilder
import com.example.flydest.model.Destination
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DesitantionCreateActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar; lateinit var btn_add :Button
    lateinit var et_city : EditText; lateinit var et_description :EditText;
    lateinit var et_country : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desitantion_create)
        init()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_add.setOnClickListener {
            val newDestination = Destination()
            newDestination.city = et_city.text.toString()
            newDestination.country = et_country.text.toString()
            newDestination.description = et_description.text.toString()
            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.addDestination(newDestination)
            requestCall.enqueue(object : Callback<Destination>{
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful){
                        finish() //moving back to destination list
                        Toast.makeText(this@DesitantionCreateActivity, "Successfully Added", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@DesitantionCreateActivity, "Failed to Add Item", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(this@DesitantionCreateActivity, "Failed to Add Item", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        btn_add = findViewById(R.id.btn_add)
        et_city = findViewById(R.id.et_city)
        et_description = findViewById(R.id.et_description)
        et_country = findViewById(R.id.et_country)
    }
}