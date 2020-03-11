package com.revature.caliberdroid.data.api

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.util.DateConverter
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.time.Instant

object GradeAPIHandler {

    fun getGrades(assessWeekNotes: AssessWeekNotes) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(APIHandler.context)
        //response is JSONarray of assessments
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/all/grade/batch/${assessWeekNotes.batch!!.batchID}?week=${assessWeekNotes.weekNumber}"
        val arrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> { response ->
                assessWeekNotes.grades = JSONParser.parseGrades(response)
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) })

        queue.add(arrayRequest)
    }

    fun putGrade(grade:Grade) {
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/grade"

        lateinit var jsonBody: JSONObject
        if(grade.gradeId==-1L){
            jsonBody = JSONObject(
                " {\"score\": " + grade.score + ", " +
                        "\"assessmentId\": " + grade.assessmentId + ", " +
                        "\"traineeId\": " + grade.traineeId + ", " +
                        "\"dateReceived\": " + System.currentTimeMillis() +  "} "
            )

        } else {
            jsonBody = JSONObject(
                " {\"gradeId\": " + grade.gradeId + ", " +
                        "\"score\": " + grade.score + ", " +
                        "\"assessmentId\": " + grade.assessmentId + ", " +
                        "\"traineeId\": " + grade.traineeId + ", " +
                        "\"dateReceived\": " + System.currentTimeMillis() +  "} "
            )
        }

        val objectRequest = JsonObjectRequest(
            Request.Method.PUT,
            url,
            jsonBody,
            Response.Listener { response ->
                Timber.d("put grade "+grade.score)
                grade.gradeId = JSONParser.parseGrade(response).gradeId
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )

        queue.add(objectRequest)
    }
}