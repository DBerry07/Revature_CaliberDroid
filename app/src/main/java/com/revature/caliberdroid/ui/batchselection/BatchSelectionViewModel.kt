package com.revature.caliberdroid.ui.batches

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository

class BatchSelectionViewModel : ViewModel() {

    lateinit var batches: LiveData<List<Batch>>

    fun getBatches() {
        batches = BatchRepository.getBatches()
    }
}