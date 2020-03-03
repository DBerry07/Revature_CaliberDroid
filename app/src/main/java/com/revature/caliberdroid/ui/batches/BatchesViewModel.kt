package com.revature.caliberdroid.ui.batches

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository

class BatchesViewModel : ViewModel() {

    lateinit var batchesLiveData: LiveData<Batch>

    fun getBatches() {
        batchesLiveData = BatchRepository.getBatches()
    }
}