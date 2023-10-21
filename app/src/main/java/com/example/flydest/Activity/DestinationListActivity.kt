package com.example.flydest.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.flydest.Adapters.DestinationAdapter
import com.example.flydest.R
import com.example.flydest.Services.DestinationService
import com.example.flydest.Services.ServiceBuilder
import com.example.flydest.model.Destination
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationListActivity : AppCompatActivity() {
    lateinit var toolbar : Toolbar
    lateinit var fab : FloatingActionButton
    lateinit var rcv : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_list)
        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.fab)
        rcv = findViewById(R.id.destiny_recycler_view)
        setSupportActionBar(toolbar)
        toolbar.title = title
        fab.setOnClickListener{
            val intent = Intent(this, DesitantionCreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadDestinations()
    }

    private fun loadDestinations() {
        //rcv.adapter = DestinationAdapter(SampleData.DESTINATIONS)
        val hashmap = HashMap<String, String>()
//        hashmap["country"] = "India"
//        hashmap["count"] = "1"
        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = destinationService.getDestinationList(hashmap)
        requestCall.enqueue(object : Callback<List<Destination>>{
            override fun onResponse(call: Call<List<Destination>>,
                response: Response<List<Destination>>
            ) {
               if(response.isSuccessful){
                   val destinationList = response.body();
                   rcv.adapter = destinationList?.let { DestinationAdapter(it) }
               }else if(response.code() == 401){
                   Toast.makeText(this@DestinationListActivity,
                   "Session Expired. Login Again", Toast.LENGTH_LONG).show()
               }else{
                   Toast.makeText(this@DestinationListActivity, "Failed to Retrieve Items", Toast.LENGTH_LONG).show()
               }
            }

            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
                Toast.makeText(this@DestinationListActivity, "Error Occurred$t", Toast.LENGTH_LONG).show()
            }

        })
    }
}