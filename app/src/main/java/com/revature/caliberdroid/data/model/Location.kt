package com.revature.caliberdroid.data.model

class Location (
    val locationID: Long,
    var name: String,
    var city: String,
    var zipcode: String,
    var address: String,
    var state: String,
    var active: Boolean
){
}