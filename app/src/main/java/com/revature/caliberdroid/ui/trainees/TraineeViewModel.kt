package com.revature.caliberdroid.ui.trainees

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Trainee


class TraineeViewModel : ViewModel() {

    private var traineesLiveData: LiveData<List<Trainee>>? = null

    fun getTrainees(): LiveData<List<Trainee>>? {
        return traineesLiveData
    }

    private fun loadTrainees() {
        // Do an asynchronous operation to fetch trainees
    }

}