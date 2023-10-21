package com.example.flydest.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flydest.Activity.DestinationDetailActivity
import com.example.flydest.R
import com.example.flydest.model.Destination

class DestinationAdapter(private val destinationList : List<Destination>) : RecyclerView.Adapter<DestinationAdapter.ViewHolder>(){

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val txtDesti : TextView = itemView.findViewById(R.id.txt_destination);
        var destination : Destination? = null

        override fun toString(): String {
            return """${super.toString()} '${txtDesti.text}'"""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return destinationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.destination = destinationList[position]
        holder.txtDesti.text = destinationList[position].city
        holder.itemView.setOnClickListener{v->
            val context = v.context
            val intent = Intent(context,DestinationDetailActivity::class.java)
            intent.putExtra(DestinationDetailActivity.ARG_ITEM_ID, holder.destination!!.id)
            context.startActivity(intent)
        }
    }

}