package com.revature.caliberdroid.adapter.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Location

class LocationsAdapter(val locations:ArrayList<Location>): RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>(){

//Need to implement data binding in recycler view adapter
    class LocationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvLocationName:TextView = itemView.findViewById<TextView>(R.id.tvLocationName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        var view:View  = LayoutInflater.from(parent.context).inflate(R.layout.row_location,parent,false)
        return LocationsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.tvLocationName.text = locations.get(position).toString()
    }
}