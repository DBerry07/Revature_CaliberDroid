package com.revature.caliberdroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.Category

object CategoryRepository {

    fun getCategories(): MutableLiveData< ArrayList<Category> >{
        val liveData = MutableLiveData< ArrayList<Category> >()
        APIHandler.getCategories(liveData)
        return liveData
    }
}