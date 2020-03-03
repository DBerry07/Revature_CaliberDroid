package com.revature.caliberdroid.data.model

data class Trainer (
    val trainerID: Long,
    var name: String,
    var title: String,
    var email: String,
    var tier: String,
    var password: String?
){

    override fun toString(): String {
        return "TrainersViewModel(trainerID=$trainerID, name='$name', title='$title', email='$email', tier='$tier', password=$password)"
    }
}