package com.revature.caliberdroid.data.parser

import com.revature.caliberdroid.data.model.AuditTraineeNotes
import com.revature.caliberdroid.data.model.AuditTraineeWithNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.ui.qualityaudit.trainees.TraineeWithNotesLiveData
import org.json.JSONArray

object AuditParser {

    fun parseTrainees(response: JSONArray): List<TraineeWithNotesLiveData> {
        val traineeWithNotesList = ArrayList<TraineeWithNotesLiveData>()


        var trainee: Trainee
        val length = response.length()

        for(i in 0 until length){
            response.getJSONObject(i).apply {
                trainee = Trainee(
                    traineeId = getLong("traineeId"),
                    name = getString("name"),
                    email = getString("email"),
                    batchId = getLong("batchId"),
                    profileUrl = getString("profileUrl"),
                    flagStatus = getString("flagStatus"),
                    _flagNotes = getString("flagNotes"),
                    flagAuthor = getString("flagAuthor"),
                    flagTimestamp = getString("flagTimestamp"))
            }
            val liveData = TraineeWithNotesLiveData()
            liveData.value = AuditTraineeWithNotes(trainee = trainee)
            traineeWithNotesList.add(liveData)
        }

        return traineeWithNotesList
    }

    fun parseTraineeNotes(
        response: JSONArray,
        traineeWithNotesList: List<TraineeWithNotesLiveData>,
        batch: Batch,
        weekNumber: Int
    ): List<TraineeWithNotesLiveData> {

        lateinit var auditTraineeNotes: AuditTraineeNotes
        val length: Int = traineeWithNotesList.size
        val responseLength = response.length()
        if (responseLength > 0) {
            for (i in 0 until responseLength) {
                response.getJSONObject(i).apply {
                    auditTraineeNotes = AuditTraineeNotes(
                        getLong("noteId"),
                        getInt("week"),
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
                    if (traineeWithNotesList.get(j).value!!.trainee!!.traineeId.equals(
                            auditTraineeNotes.traineeId
                        )
                    ) {
                        traineeWithNotesList.get(j).value!!.auditTraineeNotes = auditTraineeNotes
                    }
                }
            }

            for (j in 0 until length) {
                traineeWithNotesList.get(j).value!!.apply {
                    if (this.auditTraineeNotes == null) {
                        this.auditTraineeNotes =
                            AuditTraineeNotes(weekNumber, batch, this.trainee!!.traineeId)
                    }
                }
            }
        }

        return traineeWithNotesList
    }

}