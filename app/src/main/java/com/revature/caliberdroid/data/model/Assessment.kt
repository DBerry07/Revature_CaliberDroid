package com.revature.caliberdroid.data.model

data class Assessment(
    val assessmentId:Long,
    var rawScore:Int? = 0,
    var assessmentTitle:String? = "",
    var assessmentType:String? = "",
    var weekNumber:Int? = 0,
    var batchId:Long? = 0,
    var assessmentCategory:Int? = 0
)