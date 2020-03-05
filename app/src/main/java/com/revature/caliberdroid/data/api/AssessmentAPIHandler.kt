package com.revature.caliberdroid.data.api

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.parser.JSONParser
import org.json.JSONArray
import timber.log.Timber

object AssessmentAPIHandler{

    fun getAssessments(liveData: MutableLiveData<List<Assessment>>,batchId:Long,weekNumber:Int) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(APIHandler.context)
        //response is JSONarray of assessments
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/all/assessment/batch/$batchId/?week=$weekNumber"
        val arrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> { response ->
                liveData.postValue(JSONParser.parseAssessments(response))
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) })

        queue.add(arrayRequest)
    }


}