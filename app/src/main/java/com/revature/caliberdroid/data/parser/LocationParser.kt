package com.revature.caliberdroid.data.parser

import com.revature.caliberdroid.data.model.Location
import org.json.JSONArray

object LocationParser {

    fun parseLocation(response: JSONArray): ArrayList<Location>{
        val locations:ArrayList<Location> = ArrayList()
        for( i in 0 until response.length()){
            var location = response.getJSONObject(i)
            locations.add( Location(
                location.getLong("id"),
                location.getString("name"),
                location.getString("city"),
                location.getString("zipcode"),
                location.getString("address"),
                location.getString("state"),
                location.getBoolean("active")
            ))
        }
        return locations
    }
}