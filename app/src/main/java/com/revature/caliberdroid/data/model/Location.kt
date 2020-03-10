package com.revature.caliberdroid.data.model

data class Location (
    var locationID: Long,
    var name: String,
    var city: String,
    var zipcode: String,
    var address: String,
    var state: String,
    var active: Boolean
){
    constructor(
        name: String,
        city: String,
        zipcode: String,
        address: String,
        state: String,
        active: Boolean
    ):this(-1,name,city,zipcode,address,state,active){}

    override fun toString(): String {
        return "Location(locationID=$locationID, name='$name', city='$city', zipcode='$zipcode', address='$address', state='$state', active=$active)"
    }

    fun getAddressLines(): String{
        var stringText:String = address+"\n"
        stringText+= city+", "+state+" "+zipcode
        return stringText
    }
}