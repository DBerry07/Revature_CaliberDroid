package com.revature.caliberdroid.data.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.data.parser.JSONParser
import org.json.JSONArray
import timber.log.Timber

object APIHandler {

    lateinit var context: Context

    fun getBatches(liveData: MutableLiveData<List<Batch>>) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/vp/batch/all/?year=2020&quarter=1"
        // Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> { response ->
                // Display the first 500 characters of the response string.
                Timber.d(response.toString())
                liveData.postValue(JSONParser.parseBatch(response))
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) })

        queue.add(stringRequest)
    }

    fun getAssessments(liveData: MutableLiveData<List<Assessment>>,batchId:Long,weekNumber:Int) {
        AssessmentAPIHandler.getAssessments(liveData,batchId,weekNumber)
    }

    fun getGrades(liveData: MutableLiveData<List<Grade>>, batchId:Long, weekNumber:Int) {
        GradeAPIHandler.getGrades(liveData,batchId,weekNumber)
    }

    fun getAssessBatchOverallNote(liveData: MutableLiveData<Note>,batchId:Long, weekNumber:Int){
        NoteAPIHandler.getAssessBatchOverallNote(liveData,batchId,weekNumber)
    }

    fun getTraineeNotes(liveData: MutableLiveData<List<Note>>, batchId:Long, weekNumber:Int) {
        NoteAPIHandler.getTraineeNotes(liveData,batchId,weekNumber)
    }

    fun getTrainees(liveData:MutableLiveData<List<Trainee>>,batchId:Long) {
        TraineeAPIHandler.getTrainees(liveData,batchId)
    }
}