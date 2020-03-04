package com.revature.caliberdroid.data.model

data class Category(
    val category:Long,
    var skillCategory: String,
    var active: Boolean
){

    override fun toString(): String {
        return "Category(category=$category, skillCategory='$skillCategory', active=$active)"
    }
}