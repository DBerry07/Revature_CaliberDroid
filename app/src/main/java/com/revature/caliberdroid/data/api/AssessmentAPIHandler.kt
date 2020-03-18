package com.revature.caliberdroid.data.api

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.data.api.APIHandler.context
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.data.parser.JSONParser.getAssessmentJSONObject
import com.revature.caliberdroid.data.parser.JSONParser.parseAssessment
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

object AssessmentAPIHandler{

    fun getAssessments(assessWeekNotes: AssessWeekNotes) {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/all/assessment/batch/${assessWeekNotes.batch!!.batchID}/?week=${assessWeekNotes.weekNumber}"

        //response is JSONArray of assessments
        val arrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> { response ->
                assessWeekNotes.assessments = JSONParser.parseAssessments(response)
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )

        queue.add(arrayRequest)
    }

    fun deleteAssessment(assessment: Assessment) {
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/all/assessment/delete/${assessment.assessmentId}"

        val deleteRequestBody = JSONObject()
        deleteRequestBody.put("assessmentId", assessment.assessmentId)
        deleteRequestBody.put("rawScore", assessment.rawScore)
        deleteRequestBody.put("assessmentTitle", assessment.assessmentTitle)
        deleteRequestBody.put("assessmentType", assessment.assessmentType)
        deleteRequestBody.put("weekNumber", assessment.weekNumber)
        deleteRequestBody.put("batchId", assessment.batchId)
        deleteRequestBody.put("assessmentCategory", assessment.assessmentCategory)

        Timber.d(deleteRequestBody.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.DELETE,
            url,
            deleteRequestBody,
            Response.Listener { Timber.d("Response from delete assessment: %s", "Success") },
            Response.ErrorListener { error -> Timber.d("Response from delete assessment: %s", error.toString()) }
        )

        queue.add(jsonObjectRequest)
    }

    fun postAssessment(assessment: MutableLiveData<Assessment>) {

        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/all/assessment/create"

        val requestBody = getAssessmentJSONObject(assessment.value!!)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            requestBody,
            Response.Listener { response ->
                Timber.d(response.toString())
                assessment.value = parseAssessment(response)
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )

        queue.add(jsonObjectRequest)
    }
}