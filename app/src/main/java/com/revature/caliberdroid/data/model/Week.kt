package com.revature.caliberdroid.data.model

class Week() {
    var assessments:ArrayList<Assessment>? = null
    var grades:ArrayList<Grade>? = null
    var overallNote:Note? = null
    var traineeNotes:ArrayList<Note>? = null

    fun getGradesForAssessment(assessmentId:Long):List<Grade>? {
        if(grades == null) return null

        lateinit var assessmentGrades:ArrayList<Grade>
        for(grade in this.grades!!){
            if(grade.traineeId==assessmentId){
                assessmentGrades.add(grade)
            }
        }
        return assessmentGrades
    }

    fun getGradesForTrainee(traineeId:Long):List<Grade>? {
        if(grades == null) return null

        lateinit var traineeGrades:ArrayList<Grade>
        for(grade in this.grades!!){
            if(grade.traineeId==traineeId){
                traineeGrades.add(grade)
            }
        }
        return traineeGrades
    }

}