package com.revature.caliberdroid.data.api

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.ui.qualityaudit.weekselection.ListLiveData
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

    fun getAuditWeekNotes(liveData: ListLiveData<AuditWeekNotes>, batch: Batch) {
        AuditAPIHandler.getAuditWeekNotes(context, liveData, batch)
    }

    fun getSkillCategories(liveData: MutableLiveData<List<SkillCategory>>, batch: Batch, weekNumber: Int) {
        AuditAPIHandler.getSkillCategories(context, liveData, batch, weekNumber)
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

    fun getLocations(liveData: MutableLiveData< ArrayList<Location> >){
        LocationsAPI.getLocations(liveData)
    }

    fun addLocation(location:Location){
        LocationsAPI.addLocation(location)
    }

    fun editLocation(location:Location){
        LocationsAPI.editLocation(location)
    }

    fun getTrainers(liveData: MutableLiveData< ArrayList<Trainer> >){
        TrainersAPI.getTrainers(liveData)
    }

    fun addTrainer(trainer: Trainer){
        TrainersAPI.addTrainer(trainer)
    }

    fun editTrainer(trainer: Trainer){
        TrainersAPI.editTrainer(trainer)
    }

    fun getCategories(liveData: MutableLiveData<ArrayList<Category>>){
        CategoriesAPI.getCategories(liveData)
    }

    fun addCategory(skillCategory: String, liveData: MutableLiveData<ArrayList<Category>>){
        CategoriesAPI.addCategory(skillCategory,liveData)
    }

    fun editCategory(category: Category, liveData: MutableLiveData<ArrayList<Category>>){
        CategoriesAPI.editCategory(category,liveData)
    }
}