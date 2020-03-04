package com.revature.caliberdroid.data.api

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekLiveData
import org.json.JSONArray
import timber.log.Timber

object APIHandler {

    lateinit var context: Context

    fun getBatches(liveData: MutableLiveData<List<Batch>>) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/vp/batch/all/?year=2020&quarter=1"
        // Request a string response from the provided URL.
        val batchesRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                Timber.d(response.toString())
                liveData.postValue(JSONParser.parseBatches(response))
            },
            Response.ErrorListener {
                    error -> Timber.d(error.toString())
            })

        queue.add(batchesRequest)
    }

    fun getAuditWeekNotes(liveData: MutableLiveData<ArrayList<WeekLiveData>>, batch: Batch) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/qa/audit/notes/overall/${batch.batchID}"

        lateinit var auditWeekNotesRequest: JsonObjectRequest
        lateinit var auditWeekNotes: AuditWeekNotes

        for (i in 1 .. batch.weeks) {
            auditWeekNotesRequest = JsonObjectRequest(
                Request.Method.GET,
                "$url/$i",
                null,
                Response.Listener {
                    Timber.d(it.toString())
                    liveData.postValue(liveData.value.apply {
                        auditWeekNotes = JSONParser.parseAuditWeekNotes(response = it)
                        liveData.value?.get(i - 1).apply {
                            if (auditWeekNotes.overallNotes != null) {
                                this?.value?.overallNotes = auditWeekNotes.overallNotes
                            }
                            if (auditWeekNotes.overallStatus != null) {
                                this?.value?.overallStatus = auditWeekNotes.overallStatus
                            }

                        }
                    })
                },
                Response.ErrorListener {
                    Timber.d(it.toString())
                }
            )

            queue.add(auditWeekNotesRequest)
        }
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