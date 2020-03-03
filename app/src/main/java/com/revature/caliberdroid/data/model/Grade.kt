package com.revature.caliberdroid.data.model

data class Grade(
    val gradeId: Long,
    var dateReceived: String? = "",
    var score: Int? = 0,
    var assessmentId: Long? = 0,
    var traineeId: Long? = 0
) {
}