package com.revature.caliberdroid.data.model

data class Note(
    val noteId: Long,
    var noteContent: String? = "",
    var noteType: String? = "",
    var weekNumber: Int? = 0,
    var batchId: Long? = 0,
    var traineeId: Long? = 0
) {
}