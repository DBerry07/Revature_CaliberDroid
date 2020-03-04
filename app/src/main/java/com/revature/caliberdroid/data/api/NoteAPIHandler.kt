package com.revature.caliberdroid.data.api

import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Note
import com.revature.caliberdroid.data.parser.JSONParser
import org.json.JSONObject
import timber.log.Timber

object NoteAPIHandler {

    fun getAssessBatchOverallNote(liveData: MutableLiveData<Note>,batchId: Long,weekNumber: Int){
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/batch/$batchId/$weekNumber/note"
        val objectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                liveData.postValue(JSONParser.parseNote(response))
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )

        queue.add(objectRequest)
    }

    fun getTraineeNotes(liveData: MutableLiveData<List<Note>>, batchId:Long, weekNumber:Int) {
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/all/note/batch/$batchId/$weekNumber"
        val objectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                liveData.postValue(JSONParser.parseTraineeNotes(response))
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )

        queue.add(objectRequest)
    }
}