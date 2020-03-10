package com.revature.caliberdroid.data.api

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.data.parser.AuditParser
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.ui.qualityaudit.trainees.TraineeWithNotesLiveData
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekLiveData
import timber.log.Timber

object AuditAPIHandler {

    fun getAuditWeekNotes(
        context: Context,
        liveData: MutableLiveData<ArrayList<WeekLiveData>>,
        batch: Batch
    ) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/qa/audit/notes/overall/${batch.batchID}"

        lateinit var auditWeekNotesRequest: JsonObjectRequest
        lateinit var auditWeekNotes: AuditWeekNotes

        for (i in 1 .. batch.weeks) {
            auditWeekNotesRequest = VolleyJsonObjectRequest(
                Request.Method.GET,
                "$url/$i",
                null,
                Response.Listener {
                    Timber.d(it.toString())

                    if (it!!.length() > 0) {
                        auditWeekNotes = JSONParser.parseAuditWeekNotes(response = it)
                    } else {
                        auditWeekNotes = AuditWeekNotes(weekNumber = i)
                    }

                    liveData.value?.get(i - 1).apply {
                        if (!auditWeekNotes.overallNotes.equals("null")) {
                            this?.value?.overallNotes = auditWeekNotes.overallNotes
                        }
                        if (!auditWeekNotes.overallStatus.equals("null")) {
                            this?.value?.overallStatus = auditWeekNotes.overallStatus
                        }
                    }
                },
                Response.ErrorListener {
                    Timber.d(it.toString())
                }
            )

            queue.add(auditWeekNotesRequest)
        }
    }

    fun getSkillCategories(context: Context, liveData: MutableLiveData<List<SkillCategory>>, batch: Batch, weekNumber: Int) {

        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/qa/category/${batch.batchID}/$weekNumber/all"

        lateinit var skillCategoriesRequest: JsonArrayRequest

        skillCategoriesRequest = VolleyJsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                liveData.postValue(JSONParser.parseSkillCategories(response = it!!))
            },
            Response.ErrorListener {

            }
        )

        queue.add(skillCategoriesRequest)
    }

    fun getTraineesWithNotes(
        context: Context,
        liveData: MutableLiveData<List<TraineeWithNotesLiveData>>,
        batch: Batch,
        weekNumber: Int
    ) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        //response is JSONarray of assessments
        var url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/all/trainee/?batch=${batch.batchID}"
        lateinit var traineeWithNotesList: List<TraineeWithNotesLiveData>
        val traineesArrayRequest = VolleyJsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { traineesResponse ->
                Timber.d(traineesResponse.toString())
                traineeWithNotesList = AuditParser.parseTrainees(traineesResponse!!)

                url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/qa/audit/trainee/notes/${batch.batchID}/$weekNumber"

                val notesArrayRequest = VolleyJsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener { notesResponse ->
                        Timber.d(notesResponse.toString())
                        liveData.postValue(
                            AuditParser.parseTraineeNotes(
                                notesResponse!!,
                                traineeWithNotesList,
                                batch,
                                weekNumber
                            )
                        )
                    },
                    Response.ErrorListener { error ->
                        Timber.d(error.toString())
                    }
                )

                queue.add(notesArrayRequest)
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) })

        queue.add(traineesArrayRequest)
    }
}