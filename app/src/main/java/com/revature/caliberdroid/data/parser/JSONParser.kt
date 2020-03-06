package com.revature.caliberdroid.data.parser

import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.util.DateConverter
import org.json.JSONArray
import org.json.JSONObject

object JSONParser {

    fun parseBatches(response: JSONArray): List<Batch> {

        val batchList = ArrayList<Batch>()

        var batch: Batch
        val length = response.length() - 1
        for (i in 0 .. length) {
            response.getJSONObject(i).apply {
                batch = Batch(
                    batchID = getLong("batchId"),
                    _trainingName = getString("trainingName"),
                    trainingType = getString("trainingType"),
                    skillType = getString("skillType"),
                    trainerName = getString("trainer"),
                    coTrainerName = getString("coTrainer"),
                    locationID = getLong("locationId"),
                    location = getString("location"),
                    _startDate = getLong("startDate"),
                    _endDate = getLong("endDate"),
                    goodGrade = getInt("goodGrade"),
                    passingGrade = getInt("passingGrade"),
                    weeks = getInt("weeks"))
            }

            batchList.add(batch)
        }

        return batchList
    }

    fun parseAuditWeekNotes(response: JSONObject): AuditWeekNotes {

        var auditWeekNotes: AuditWeekNotes

        response.apply {
            auditWeekNotes = AuditWeekNotes(
                weekNumber = getInt("week"),
                overallStatus = getString("technicalStatus"),
                overallNotes = getString("content"))
        }

        return auditWeekNotes
    }

    fun parseSkillCategories(response: JSONArray) : List<SkillCategory> {
        val categoryList = ArrayList<SkillCategory>()

        var skillCategory: SkillCategory
        val length = response.length() - 1
        for (i in 0 .. length) {
            response.getJSONObject(i).apply {
                skillCategory = SkillCategory(getLong("categoryId"), getString("skillCategory"))
            }

            categoryList.add(skillCategory)
        }

        return categoryList
    }

    fun parseAssessments(response: JSONArray): List<Assessment> {
        val assessmentList = ArrayList<Assessment>()

        var assessment: Assessment
        val length = response.length()
        for(i in 0 until length){
            response.getJSONObject(i).apply {
                assessment = Assessment(getLong("assessmentId"),
                    getInt("rawScore"),
                    getString("assessmentTitle"),
                    getString("assessmentType"),
                    getInt("weekNumber"),
                    getLong("batchId"),
                    getInt("assessmentCategory"))
            }
            assessmentList.add(assessment)
        }

        return assessmentList
    }

    fun parseGrades(response: JSONArray): List<Grade> {
        val gradeList = ArrayList<Grade>()

        var grade: Grade
        val length = response.length()
        for(i in 0 until length){
            response.getJSONObject(i).apply {
                grade = Grade(getLong("gradeId"),
                    getString("dateReceived"),
                    getInt("score"),
                    getLong("assessmentId"),
                    getLong("traineeId"))
            }
            gradeList.add(grade)
        }

        return gradeList
    }

    fun parseNote(note:JSONObject):Note {
        note.apply {
            return Note(getLong("noteId"),
                getString("noteContent"),
                getString("noteType"),
                getInt("weekNumber"),
                getLong("batchId"),
                getLong("traineeId"))
        }
    }

    fun parseTraineeNotes(response:JSONObject): List<Note> {
        val noteList = ArrayList<Note>()

        lateinit var note:Note
        val keys:Iterator<String> = response.keys()
        var key:String
        while(keys.hasNext()){
            key = keys.next()
            if(response.get(key) is JSONArray) {
                (response.get(key) as JSONArray).getJSONObject(0).apply {
                    note = parseNote(this)
                }
            }
            noteList.add(note)
        }

        return noteList

    }

    fun parseTrainees(response: JSONArray): List<Trainee> {
        val traineeList = ArrayList<Trainee>()

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
                    //Had to alter techScreenScore from getLong to get to allow null from API
                    get("techScreenScore"),
                    getString("projectCompletion"),
                    getString("flagStatus"),
                    getString("flagNotes"),
                    getString("flagAuthor"),
                    getString("flagTimestamp"))
            }
            traineeList.add(trainee)
        }

        return traineeList
    }

    fun getBatchJSONObject(batch: Batch) : JSONObject {
        val request = JSONObject()

        return request.apply {
                batch.apply {
                put("batchId", batchID)
                put("trainingName", trainingName)
                put("trainingType", trainingType)
                put("skillType", skillType)
                put("trainer", trainerName)
                put("coTrainer", coTrainerName)
                put("locationId", locationID)
                put("location", location)
                put("startDate", _startDate)
                put("endDate", _endDate)
                put("goodGrade", goodGrade)
                put("passingGrade", passingGrade)
                put("weeks", weeks)
            }
        }
    }

}