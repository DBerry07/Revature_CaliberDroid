package com.revature.caliberdroid.data.parser

import com.revature.caliberdroid.data.model.AuditTraineeNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.data.model.TraineeWithNotes
import org.json.JSONArray

object AuditParser {

    fun parseTrainees(response: JSONArray): List<TraineeWithNotes> {
        val traineeWithNotesList = ArrayList<TraineeWithNotes>()

        var trainee: Trainee
        val length = response.length()
        for(i in 0 until length){
            response.getJSONObject(i).apply {
                trainee = Trainee(getLong("traineeId"),
                    getString("resourceId"),
                    getString("name"),
                    getString("email"),
                    getString("trainingStatus"),
                    getLong("batchId"),
                    getString("phoneNumber"),
                    getString("skypeId"),
                    getString("profileUrl"),
                    getString("recruiterName"),
                    getString("college"),
                    getString("degree"),
                    getString("major"),
                    getString("techScreenerName"),
                    getLong("techScreenScore"),
                    getString("projectCompletion"),
                    getString("flagStatus"),
                    getString("flagNotes"),
                    getString("flagAuthor"),
                    getString("flagTimestamp"))
            }
            traineeWithNotesList.add(TraineeWithNotes(trainee = trainee))
        }

        return traineeWithNotesList
    }

    fun parseTraineeNotes(response: JSONArray, traineeWithNotesList: List<TraineeWithNotes>, batch: Batch, weekNumber: Int) : List<TraineeWithNotes> {

        lateinit var auditTraineeNotes: AuditTraineeNotes
        val length: Int = traineeWithNotesList.size
        val responseLength = response.length()
        for(i in 0 until responseLength){
            response.getJSONObject(i).apply {
                auditTraineeNotes = AuditTraineeNotes(
                    getLong("noteId"),
                    weekNumber,
                    getString("content"),
                    getString("technicalStatus"),
                    batch,
                    getLong("traineeId")
                )
            }

            if (auditTraineeNotes.content.equals("null")) {
                auditTraineeNotes.content = ""
            }

            for (j in 0 until length) {
                if (traineeWithNotesList.get(j).trainee.traineeId.equals(auditTraineeNotes.traineeId)) {
                    traineeWithNotesList.get(j).auditTraineeNotes = auditTraineeNotes
                }
            }
        }

        return traineeWithNotesList
    }

}