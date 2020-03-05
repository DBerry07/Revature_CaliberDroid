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
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.ui.qualityaudit.weekselection.ListLiveData
import timber.log.Timber

object AuditAPIHandler {

    fun getAuditWeekNotes(context: Context, liveData: ListLiveData<AuditWeekNotes>, batch: Batch) {
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
//                    liveData.postValue(liveData.value.apply {
//                        auditWeekNotes = JSONParser.parseAuditWeekNotes(response = it)
//                        liveData.value?.get(i - 1).apply {
//                            if (!auditWeekNotes.overallNotes.equals("null")) {
//                                this?.value?.overallNotes = auditWeekNotes.overallNotes
//                            }
//                            if (!auditWeekNotes.overallStatus.equals("null")) {
//                                this?.value?.overallStatus = auditWeekNotes.overallStatus
//                            }
//
//                        }
//                    })
                    auditWeekNotes = JSONParser.parseAuditWeekNotes(response = it)

                    liveData[i - 1].apply {
                        if (!auditWeekNotes.overallNotes.equals("null")) {
                            this?.overallNotes = auditWeekNotes.overallNotes
                        }
                        if (!auditWeekNotes.overallStatus.equals("null")) {
                            this?.overallStatus = auditWeekNotes.overallStatus
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

        skillCategoriesRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                liveData.postValue(JSONParser.parseSkillCategories(response = it))
            },
            Response.ErrorListener {

            }
        )

        queue.add(skillCategoriesRequest)
    }
}