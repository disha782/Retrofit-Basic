package com.example.flydest.Activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.flydest.Adapters.SampleData
import com.example.flydest.R
import com.example.flydest.Services.DestinationService
import com.example.flydest.Services.ServiceBuilder
import com.example.flydest.model.Destination
import com.google.android.material.appbar.CollapsingToolbarLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationDetailActivity : AppCompatActivity(){
    lateinit var toolbar: Toolbar; lateinit var et_city : EditText;
    lateinit var et_description : EditText; lateinit var et_country : EditText
    lateinit var collapsing_toolbar : CollapsingToolbarLayout; lateinit var btn_update : Button
    lateinit var btn_delete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_detail);
        init()
        setSupportActionBar(toolbar);

        //show the up button in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        val bundle : Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID) !!){
            val id = intent.getIntExtra(ARG_ITEM_ID, 0)
            loadDetails(id)
            initUpdateButton(id)
            initDeleteButton(id)
        }
    }

    private fun init(){
        toolbar = findViewById(R.id.detail_toolbar);
        et_city = findViewById(R.id.et_city)
        et_description = findViewById(R.id.et_description)
        et_country = findViewById(R.id.et_country)
        collapsing_toolbar = findViewById(R.id.collapsing_toolbar)
        btn_update = findViewById(R.id.btn_update)
        btn_delete = findViewById(R.id.btn_delete)
    }

    private fun initDeleteButton(id: Int) {
        btn_delete.setOnClickListener {
            val servicebuilder = ServiceBuilder.buildService(DestinationService::class.java)
            val deleteop = servicebuilder.deleteDestination(id)
            deleteop.enqueue(object : Callback<Unit>{
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful){
                        finish() // takes you back to the previous activity
                        Toast.makeText(this@DestinationDetailActivity, "Item deleted", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@DestinationDetailActivity, "Deletion Failed", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(this@DestinationDetailActivity, "Deletion Failed", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    private fun initUpdateButton(id: Int) {
        btn_update.setOnClickListener {
            val city = et_city.text.toString()
            val description = et_description.text.toString()
            val country = et_country.text.toString()

            val servicebuilder = ServiceBuilder.buildService(DestinationService::class.java)
            val updateservice = servicebuilder.updateDestination(id, city, country, description)
            updateservice.enqueue(object : Callback<Destination>{
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful){
                        finish()
                        Toast.makeText(this@DestinationDetailActivity, "Values Updated", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@DestinationDetailActivity, "Failed", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(this@DestinationDetailActivity, "Failed", Toast.LENGTH_LONG).show()
                }

            })
        }
    }

    private fun loadDetails(id: Int) {
//        val destination = SampleData.getDestinationById(id)
//        destination?.let {
//            et_city.setText(destination.city)
//            et_description.setText(destination.description)
//            et_country.setText(destination.city)
//            collapsing_toolbar.title = destination.city
//        }
        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = destinationService.getDestination(id)
        requestCall.enqueue(object : retrofit2.Callback<Destination>{
            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful){
                    val destination =  response.body()
                    destination?.let {
                        et_city.setText(destination.city)
                        et_country.setText(destination.country)
                        et_description.setText(destination.description)
                        collapsing_toolbar.title = destination.city
                    }
                }else{
                    Toast.makeText(this@DestinationDetailActivity, "Failed to return details", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {
                Toast.makeText(this@DestinationDetailActivity, "Failed to return details", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home){
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val ARG_ITEM_ID = "item_id"
    }
}