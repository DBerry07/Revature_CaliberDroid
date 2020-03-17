package com.revature.caliberdroid.data.api

import android.content.Context
import android.system.Os.remove
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.api.APIHandler.context
import com.revature.caliberdroid.data.model.AuditTraineeWithNotes
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.data.parser.AuditParser
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.ui.qualityaudit.trainees.TraineeWithNotesLiveData
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekLiveData
import org.json.JSONObject
import timber.log.Timber

object AuditAPIHandler {

    fun deleteAuditSkillCategory(skillCategory: SkillCategory, skillCategoryLiveData: MutableLiveData<ArrayList<SkillCategory>>) {
        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/qa/category/delete/${skillCategory.id}"

        val deleteSkillCategoryRequest = VolleyJsonObjectRequest(
            Request.Method.DELETE,
            url,
            null,
            Response.Listener {
                val copy = skillCategoryLiveData.value!!.clone() as ArrayList<SkillCategory>
                copy.remove(skillCategory)
                skillCategoryLiveData.postValue(copy)
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )

        queue.add(deleteSkillCategoryRequest)
    }

    fun deleteAuditSkillCategories(skillCategories: ArrayList<SkillCategory>, skillCategoriesLiveData: MutableLiveData<ArrayList<SkillCategory>>) {
        for (skillCategory in skillCategories) deleteAuditSkillCategory(skillCategory, skillCategoriesLiveData)
    }

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

        for (week in 1..batch.weeks) {
            auditWeekNotesRequest = VolleyJsonObjectRequest(
                Request.Method.GET,
                "$url/$week",
                null,
                Response.Listener {
                    Timber.d("Response from GET -> ${it.toString()}")

                    if (it!!.length() > 0) {
                        auditWeekNotes = JSONParser.parseAuditWeekNotes(response = it)
                    } else {
                        auditWeekNotes = AuditWeekNotes(weekNumber = week)
                    }

                    liveData.value?.get(week - 1).apply {
                        if (!auditWeekNotes.overallNotes.equals("null")) {
                            this?.value?.overallNotes = auditWeekNotes.overallNotes
                        }
                        if (!auditWeekNotes.overallStatus.equals("null")) {
                            this?.value?.overallStatus = auditWeekNotes.overallStatus
                        }
                        this?.value?.noteId = auditWeekNotes.noteId
                    }
                },
                Response.ErrorListener {
                    Timber.d(it.toString())
                }
            )

            queue.add(auditWeekNotesRequest)
        }
    }

    fun getSkillCategories(context: Context, liveData: MutableLiveData<ArrayList<SkillCategory>>, batch: Batch, weekNumber: Int) {

        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/qa/category/${batch.batchID}/$weekNumber/all"

        lateinit var skillCategoriesRequest: JsonArrayRequest

        skillCategoriesRequest = VolleyJsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { liveData.postValue(JSONParser.parseSkillCategories(response = it!!)) },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
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
                        Timber.d("Trainee notes response -> ${notesResponse.toString()}")
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

    fun postAuditSkillCategory(category: Category, batch: Batch, weekNumber: Int, skillCategoryLiveData: MutableLiveData<ArrayList<SkillCategory>>) {
        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/qa/category"

        val skillCategoryJsonObject = JSONObject()
        skillCategoryJsonObject.put("categoryId", category.categoryId)
        skillCategoryJsonObject.put("batchId", batch.batchID)
        skillCategoryJsonObject.put("week", weekNumber)
        skillCategoryJsonObject.put("skillCategory", category.skillCategory)

        val deleteSkillCategoryRequest = VolleyJsonObjectRequest(
            Request.Method.POST,
            url,
            skillCategoryJsonObject,
            Response.Listener {
                val skillCategoryAdded = JSONParser.parseSkillCategory(it!!)
                val skillCategories = skillCategoryLiveData.value
                skillCategories!!.add(skillCategoryAdded)
                skillCategoryLiveData.postValue(skillCategories)
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )

        queue.add(deleteSkillCategoryRequest)
    }

    fun postAuditSkillCategories(categories: ArrayList<Category>, batch: Batch, weekNumber: Int, skillCategoryLiveData: MutableLiveData<ArrayList<SkillCategory>>) {
        for (category in categories) postAuditSkillCategory(category, batch, weekNumber, skillCategoryLiveData)
    }

    fun putAuditWeekNotes(context: Context, auditWeekNotes: AuditWeekNotes) {

        val queue = Volley.newRequestQueue(context)

        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/qa/audit/batch/notes"

        val requestBody =
            if (auditWeekNotes.noteId == -1L) {
                JSONObject(
                    "{" +
                            "\"week\":${auditWeekNotes.weekNumber}," +
                            "\"batchId\":${auditWeekNotes.batchId}," +
                            "\"type\":\"QC_BATCH\"," +
                            "\"technicalStatus\":\"${auditWeekNotes.overallStatus}\"," +
                            "\"softSkillStatus\":\"Undefined\"," +
                            "\"content\":\"${auditWeekNotes.overallNotes}\"" +
                            "}"
                )
            } else {
                JSONObject(
                    "{" +
                            "\"noteId\": ${auditWeekNotes.noteId}," +
                            "\"content\": \"${auditWeekNotes.overallNotes}\"," +
                            "\"week\": ${auditWeekNotes.weekNumber}," +
                            "\"batchId\": ${auditWeekNotes.batchId}," +
                            "\"trainee\": null," +
                            "\"traineeId\": 0," +
                            "\"type\": \"QC_BATCH\"," +
                            "\"technicalStatus\": \"${auditWeekNotes.overallStatus}\"," +
                            "\"softSkillStatus\": \"Undefined\"," +
                            "\"updateTime\": ${System.currentTimeMillis() / 1000}," +
                            "\"lastSavedBy\": null" +
                            "}"
                )
            }

        val request = VolleyJsonObjectRequest(
            Request.Method.PUT,
            url,
            requestBody,
            Response.Listener {
                Timber.d("Response from PUT -> ${it.toString()}")
                if (auditWeekNotes.noteId == -1L) {
                    if (it != null) {
                        auditWeekNotes.noteId = it.getLong("noteId")
                    }
                }
            },
            Response.ErrorListener {
                Timber.d(it.toString())
            }
        )

        Timber.d("Making call to API")

        queue.add(request)

    }

    fun putTraineeWithNotes(context: Context, traineeWithNotes: AuditTraineeWithNotes) {

        val queue = Volley.newRequestQueue(context)

        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/qa/audit/trainee/notes"

        val auditTraineeNotes = traineeWithNotes.auditTraineeNotes!!

        val requestBody =
            if (auditTraineeNotes.noteId == -1L) {
                JSONObject(
                    "{" +
                            "\"technicalStatus\":\"${auditTraineeNotes.technicalStatus}\"," +
                            "\"softSkillStatus\":\"Undefined\"," +
                            "\"traineeId\":${auditTraineeNotes.traineeId}," +
                            "\"batchId\":${auditTraineeNotes.batch.batchID}," +
                            "\"week\":${auditTraineeNotes.weekNumber}," +
                            "\"content\":\"${auditTraineeNotes.content}\"," +
                            "\"type\":\"QC_TRAINEE\"" +
                            "}"
                )
            } else {
                JSONObject(
                    "{" +
                            "\"noteId\": ${auditTraineeNotes.noteId}," +
                            "\"content\": \"${auditTraineeNotes.content}\"," +
                            "\"week\": ${auditTraineeNotes.weekNumber}," +
                            "\"batchId\": ${auditTraineeNotes.batch.batchID}," +
                            "\"trainee\": null," +
                            "\"traineeId\": ${auditTraineeNotes.traineeId}," +
                            "\"type\": \"QC_TRAINEE\"," +
                            "\"technicalStatus\": \"${auditTraineeNotes.technicalStatus}\"," +
                            "\"softSkillStatus\": \"Undefined\"," +
                            "\"updateTime\": ${System.currentTimeMillis() / 1000}," +
                            "\"lastSavedBy\": null" +
                            "}"
                )
            }

        val request = VolleyJsonObjectRequest(
            Request.Method.PUT,
            url,
            requestBody,
            Response.Listener {
                Timber.d("Response from PUT -> ${it.toString()}")
                if (traineeWithNotes.auditTraineeNotes!!.noteId == -1L) {
                    if (it != null) {
                        traineeWithNotes.auditTraineeNotes!!.noteId = it.getLong("noteId")
                    }
                }
            },
            Response.ErrorListener {
                Timber.d(it.toString())
            }
        )

        Timber.d("Making call to API")

        queue.add(request)

    }
}