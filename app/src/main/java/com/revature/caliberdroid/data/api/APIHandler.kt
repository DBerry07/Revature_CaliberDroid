package com.revature.caliberdroid.data.api

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.data.parser.TrainerParser
import com.revature.caliberdroid.ui.assessbatch.weekselection.AssessWeekLiveData
import com.revature.caliberdroid.ui.qualityaudit.trainees.TraineeWithNotesLiveData
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekLiveData
import org.json.JSONObject
import timber.log.Timber
import java.lang.Exception

object APIHandler {

    lateinit var context: Context

    fun getBatchesByYear(
        liveData: MutableLiveData<List<Batch>>,
        selectedYear: Int
    ) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/vp/batch/$selectedYear"
        // Request a string response from the provided URL.
        val batchesRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                Timber.d(response.toString())
                liveData.postValue(JSONParser.parseBatches(response))
            },
            Response.ErrorListener { error ->
                Timber.d(error.toString())
            })

        queue.add(batchesRequest)
    }

    fun getBatchesByYearAndQuarter(
        liveData: MutableLiveData<List<Batch>>,
        selectedYear: Int,
        selectedQuarter: Int
    ) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/vp/batch/all/?year=$selectedYear&quarter=$selectedQuarter"
        Timber.d(url)
        // Request a string response from the provided URL.
        val batchesRequest = VolleyJsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                Timber.d(response.toString())
                liveData.postValue(JSONParser.parseBatches(response))
            },
            Response.ErrorListener { error ->
                Timber.d(error.toString())
            })

        queue.add(batchesRequest)
    }

    fun addWeekFromAudit(batch: Batch, liveData: MutableLiveData<ArrayList<WeekLiveData>>) {
        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/all/batch/update"
        lateinit var data: WeekLiveData
        var auditWeekNotes: AuditWeekNotes

        val addWeekRequest = JsonObjectRequest(
            Request.Method.PUT,
            url,
            JSONParser.getBatchJSONObject(batch).apply { put("weeks", getInt("weeks") + 1) },
            Response.Listener { response ->
                Timber.d(response.toString())
                liveData.postValue(liveData.value!!.apply {
                    batch.weeks += 1
                    data = WeekLiveData()
                    auditWeekNotes = AuditWeekNotes(batch.weeks)
                    auditWeekNotes.batchId = batch.batchID
                    data.value = auditWeekNotes
                    this.add(data)
                })
            },
            Response.ErrorListener {
                Timber.d(it.toString())
            })

        queue.add(addWeekRequest)
    }

    fun addWeekFromAssess(batch: Batch, liveData: MutableLiveData<ArrayList<AssessWeekLiveData>>) {
        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/all/batch/update"
        lateinit var data: AssessWeekLiveData

        val addWeekRequest = JsonObjectRequest(
            Request.Method.PUT,
            url,
            JSONParser.getBatchJSONObject(batch).apply { put("weeks", getInt("weeks") + 1) },
            Response.Listener { response ->
                Timber.d(response.toString())
                liveData.postValue(liveData.value!!.apply {
                    batch.weeks += 1
                    data = AssessWeekLiveData()
                    data.value = AssessWeekNotes(batch.weeks, batch)
                    this.add(data)
                })
            },
            Response.ErrorListener {
                Timber.d(it.toString())
            })

        queue.add(addWeekRequest)
    }

    fun getValidYears(liveData: MutableLiveData<List<Int>>) {
        val queue = Volley.newRequestQueue(context)
        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/all/batch/valid_years"
        var length: Int

        val validYearsRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                Timber.d(response.toString())
                length = response.length()
                liveData.postValue(liveData.value!!.apply {
                    for (i in 0 until length) {
                        (this as ArrayList).add(response.getInt(i))
                    }
                })
            },
            Response.ErrorListener {
                Timber.d(it.toString())
            })

        queue.add(validYearsRequest)
    }

    fun getTraineesWithNotes(
        liveData: MutableLiveData<List<TraineeWithNotesLiveData>>,
        batch: Batch,
        weekNumber: Int
    ) {
        AuditAPIHandler.getTraineesWithNotes(context = context, liveData =  liveData, batch =  batch, weekNumber = weekNumber)
    }

    fun getAuditWeekNotes(liveData: MutableLiveData<ArrayList<WeekLiveData>>, batch: Batch) {
        AuditAPIHandler.getAuditWeekNotes(context, liveData, batch)
    }

    fun getSkillCategories(liveData: MutableLiveData<List<SkillCategory>>, batch: Batch, weekNumber: Int) {
        AuditAPIHandler.getSkillCategories(context, liveData, batch, weekNumber)
    }

    fun putAuditWeekNotes(auditWeekNotes: AuditWeekNotes) {
        AuditAPIHandler.putAuditWeekNotes(context, auditWeekNotes)
    }

    fun getAssessWeekNotes(assessWeekNotes: AssessWeekNotes) {
        GradeAPIHandler.getGrades(assessWeekNotes)
        AssessmentAPIHandler.getAssessments(assessWeekNotes)
        NoteAPIHandler.getAssessBatchOverallNote(assessWeekNotes)
    }

    fun getTraineeNotes(liveData: MutableLiveData<List<Note>>, batchId: Long, weekNumber: Int) {
        NoteAPIHandler.getTraineeNotes(liveData, batchId, weekNumber)
    }

    fun getTrainees(liveData: MutableLiveData<List<Trainee>>, batchId: Long) {
        TraineeAPIHandler.getTrainees(liveData, batchId)
    }

    fun putTraineeWithNotes(traineeWithNotes: AuditTraineeWithNotes) {
        AuditAPIHandler.putTraineeWithNotes(context, traineeWithNotes)
    }

    fun postTrainee(jsonObject: JSONObject) {
        TraineeAPIHandler.postTrainee(jsonObject)
    }

    fun putTrainee(jsonObject: JSONObject) {
        TraineeAPIHandler.putTrainee(jsonObject)
    }

    fun deleteTrainee(trainee : Trainee){
        TraineeAPIHandler.deleteTrainee(trainee)
    }

    fun putTraineeNote(note:Note) {
        Timber.d(note.toString())
        NoteAPIHandler.putTraineeNote(note)
    }

    fun getLocations(liveData: MutableLiveData<ArrayList<Location>>) {
        LocationsAPI.getLocations(liveData)
    }

    fun addLocation(location: Location) {
        LocationsAPI.addLocation(location)
    }

    fun editLocation(location: Location) {
        LocationsAPI.editLocation(location)
    }

    fun getTrainers(liveData: MutableLiveData<ArrayList<Trainer>>) {
        TrainersAPI.getTrainers(liveData)
    }

    fun addTrainer(trainer: Trainer) {
        TrainersAPI.addTrainer(trainer)
    }

    fun editTrainer(trainer: Trainer) {
        TrainersAPI.editTrainer(trainer)
    }

    fun addBatch(batch: Batch) {
        BatchAPIHandler.addBatch(batch)
    }

    fun editBatch(batch: Batch) {
        BatchAPIHandler.editBatch(batch)
    }

    fun deleteBatch(batch: Batch) {
        BatchAPIHandler.deleteBatch(batch)
    }

    fun getCategories(liveData: MutableLiveData<ArrayList<Category>>) {
        CategoriesAPI.getCategories(liveData)
    }

    fun addCategory(skillCategory: String, liveData: MutableLiveData<ArrayList<Category>>) {
        CategoriesAPI.addCategory(skillCategory, liveData)
    }

    fun editCategory(category: Category, liveData: MutableLiveData<ArrayList<Category>>) {
        CategoriesAPI.editCategory(category, liveData)
    }

    fun postAssessment(assessment: MutableLiveData<Assessment>) {
        AssessmentAPIHandler.postAssessment(assessment)
    }

    fun putAssessBatchOverallNote(note: Note) {
        NoteAPIHandler.putAssessBatchOverallNote(note)
    }

    fun getAllBatches(liveData: MutableLiveData<ArrayList<Batch>>) {
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url: String =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/vp/batch/all/"
        Timber.d("Url being sent: $url")
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                Timber.d("Response: $response")
                try {
                    liveData.postValue(JSONParser.parseBatches(response) as ArrayList<Batch>)
                } catch (e: Exception) {
                    Timber.d("Error resolving liveData: " + e.toString())
                }
            },
            Response.ErrorListener { error ->
                Timber.d("Error getting bactches: " + error.toString())
            }
        )
        queue.add(request)
    }

    fun switchTrainee(traineeLiveData: MutableLiveData<Trainee>, newBatch: Batch) {
        val trainee: Trainee? = traineeLiveData.value
        if(trainee != null){
            //Because the API will not allow null values for this field
            if(trainee.flagStatus == null || trainee?.flagStatus?.trim()?.toLowerCase().equals("null")){
                trainee.flagStatus = "NONE"
            }
            val url: String =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/trainee/switch"
            val queue = Volley.newRequestQueue(APIHandler.context)
            Timber.d("Url being sent: $url")
            val jsonBody =
                JSONObject(
                    " {\"traineeId\": " + trainee.traineeId + ", " +
                            "\"resourceId\": \"" + trainee.resourceId + "\", " +
                            "\"name\": \"" + trainee.name + "\", " +
                            "\"email\": \"" + trainee.email + "\", " +
                            "\"trainingStatus\": \"" + trainee.trainingStatus + "\", " +
                            "\"batchId\": " + newBatch.batchID + ", " +
                            "\"phoneNumber\": \"" + trainee.phoneNumber + "\", " +
                            "\"skypeId\": \"" + trainee.skypeId + "\", " +
                            "\"profileUrl\": \"" + trainee.profileUrl + "\", " +
                            "\"recruiterName\": \"" + trainee.recruiterName + "\", " +
                            "\"college\": \"" + trainee.college + "\", " +
                            "\"degree\": \"" + trainee.degree + "\", " +
                            "\"major\": \"" + trainee.major + "\", " +
                            "\"techScreenerName\": \"" + trainee.techScreenerName + "\", " +
                            "\"techScreenScore\": \"" + trainee.techScreenScore + "\", " +
                            "\"projectCompletion\": \"" + trainee.projectCompletion + "\", " +
                            "\"flagStatus\": \"" + trainee.flagStatus + "\", " +
                            "\"flagNotes\": \"" + trainee.flagNotes + "\", " +
                            "\"flagAuthor\": \"" + trainee.flagAuthor + "\", " +
                            "\"flagTimestamp\": \"" + trainee.flagTimestamp + "\"} "
                )
            Timber.d("Switch Trainee Request being sent: ${jsonBody.toString()}")
            val request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                Response.Listener { response ->
                    Timber.d("Response: $response")
                    traineeLiveData.postValue( JSONParser.parseSingleTrainee(response) )
                },
                Response.ErrorListener { error ->
                    Timber.e("Error switching trainee: " + error.toString())
                }
            )
            queue.add(request)
        }else{
            Timber.e("Error switching trainee: trainee is null." )
        }
    }
}