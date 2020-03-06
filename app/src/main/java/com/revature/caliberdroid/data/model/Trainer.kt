package com.revature.caliberdroid.data.model

data class Trainer (
    var trainerID: Long,
    var name: String,
    var title: String,
    var email: String,
    var tier: String,
    var password: String?
){
    constructor(
        name: String,
        title: String,
        email: String,
        tier: String,
        password: String?) : this(-1,name,title,email,tier,password){

    }

    private operator fun invoke(i: Int, s: String, s1: String, s2: String, s3: String, s4: String) {

    }

    override fun toString(): String {
        return "TrainersViewModel(trainerID=$trainerID, name='$name', title='$title', email='$email', tier='$tier', password=$password)"
    }
}