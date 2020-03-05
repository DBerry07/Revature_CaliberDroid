package com.revature.caliberdroid.adapter.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.locations.listeners.EditLocationInterface
import com.revature.caliberdroid.data.model.Location

class LocationsAdapter(val locations:ArrayList<Location>, val editListener:EditLocationInterface): RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>(){

//Need to implement data binding in recycler view adapter
    class LocationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvLocationName:TextView = itemView.findViewById(R.id.tvLocation)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val imgActiveStatus: ImageView = itemView.findViewById(R.id.imgActiveStatus)
        val imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        var view:View  = LayoutInflater.from(parent.context).inflate(R.layout.item_location,parent,false)
        return LocationsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val location: Location = locations.get(position)
        holder.tvLocationName.text = location.name
        holder.tvAddress.text = formatAddress(location)
        setActiveStatusImage(location, holder.imgActiveStatus)
        holder.imgEdit.setOnClickListener{
            editListener.onEditLocation(location)
        }
    }

    private fun formatAddress(location:Location): String{
        var stringText:String = location.address+"\n"
        stringText+= location.city+", "+location.state+" "+location.zipcode
        return stringText
    }

    private fun setActiveStatusImage(location:Location, imgView: ImageView){
        if(location.active){
            imgView.setImageResource(R.drawable.ic_active_green)
            imgView.setBackgroundResource(R.drawable.background_active_status)
        }else{
            imgView.setImageResource(R.drawable.ic_inactive_red)
            imgView.setBackgroundResource(R.drawable.background_inactive_status)
        }
    }
}