package com.revature.caliberdroid.data.api

import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.data.model.Note
import com.revature.caliberdroid.data.parser.JSONParser
import org.json.JSONObject
import timber.log.Timber

object NoteAPIHandler {

    fun getAssessBatchOverallNote(assessWeekNotes: AssessWeekNotes){
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/batch/${assessWeekNotes.batch!!.batchID}/${assessWeekNotes.weekNumber}/note"
        val objectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                assessWeekNotes.batchNote = JSONParser.parseNote(response)
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

    fun putTraineeNote(traineeNote: Note){
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/assessment/note"

        lateinit var jsonBody:JSONObject
        if(traineeNote.noteId==-1L){
            jsonBody = JSONObject(
                " {\"noteContent\": \"" + traineeNote.noteContent + "\", " +
                        "\"noteType\": \"" + traineeNote.noteType + "\", " +
                        "\"weekNumber\": " + traineeNote.weekNumber + ", " +
                        "\"batchId\": " + traineeNote.batchId + ", " +
                        "\"traineeId\": " + traineeNote.traineeId + "} "
            )

        } else {
             jsonBody = JSONObject(
                " {\"noteId\": " + traineeNote.noteId + ", " +
                        "\"noteContent\": \"" + traineeNote.noteContent + "\", " +
                        "\"noteType\": \"" + traineeNote.noteType + "\", " +
                        "\"weekNumber\": " + traineeNote.weekNumber + ", " +
                        "\"batchId\": " + traineeNote.batchId + ", " +
                        "\"traineeId\": " + traineeNote.traineeId + "} "
            )
        }

        val objectRequest = JsonObjectRequest(
            Request.Method.PUT,
            url,
            jsonBody,
            Response.Listener { response ->
                traineeNote.noteId = JSONParser.parseNote(response).noteId
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )

        queue.add(objectRequest)
    }
}