package com.revature.caliberdroid.data.model

data class Location (
    val locationID: Long,
    var name: String,
    var city: String,
    var zipcode: String,
    var address: String,
    var state: String,
    var active: Boolean
){
    override fun toString(): String {
        return "Location(locationID=$locationID, name='$name', city='$city', zipcode='$zipcode', address='$address', state='$state', active=$active)"
    }
}