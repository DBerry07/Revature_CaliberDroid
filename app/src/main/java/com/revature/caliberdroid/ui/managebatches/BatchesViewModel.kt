package com.revature.caliberdroid.ui.managebatches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.data.repository.TrainerRepository

class BatchesViewModel : ViewModel() {

    var batchesLiveData = MutableLiveData<List<Batch>>()
    lateinit var validYears: LiveData<List<Int>>
    lateinit var locations: LiveData<ArrayList<Location>>
    lateinit var trainers: MutableLiveData<ArrayList<Trainer>>

    var selectedYear: Int? = null
        set(value) {
            field = value
            getBatches()
        }

    private fun getBatches() {
        BatchRepository.getBatchesByYear(batchesLiveData, selectedYear!!)
    }

    fun getValidYears() {
        validYears = BatchRepository.getValidYears()
    }

    fun getTrainers() {
        trainers = TrainerRepository.getTrainers()
    }
}