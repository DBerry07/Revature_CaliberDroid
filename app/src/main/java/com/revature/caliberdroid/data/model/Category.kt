package com.revature.caliberdroid.data.model

data class Category(
    val categoryId:Long,
    var skillCategory: String,
    var active: Boolean
){

    override fun toString(): String {
        return "Category(category=$categoryId, skillCategory='$skillCategory', active=$active)"
    }
}