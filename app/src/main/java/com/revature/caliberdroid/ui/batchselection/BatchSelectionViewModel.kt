package com.revature.caliberdroid.ui.batches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository

class BatchSelectionViewModel : ViewModel() {

    val batches = MutableLiveData<List<Batch>>()
    lateinit var validYears: LiveData<List<Int>>
    val quarters = listOf("Q1", "Q2", "Q3", "Q4")
    var selectedYear: Int? = null
        set(value) {
            field = value
            getBatches()
        }
    var selectedQuarter = quarters[0][1].toString().toInt()
        set(value) {
            field = value
            getBatches()
        }

    fun getBatches() {
        BatchRepository.getBatchesByYearAndQuarter(batches, selectedYear!!, selectedQuarter)
    }

    private fun getValidYears() {
        validYears = BatchRepository.getValidYears()
    }

    fun getData() {
        getValidYears()
    }
}