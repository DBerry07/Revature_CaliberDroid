package com.revature.caliberdroid.ui.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.repository.CategoryRepository

class CategoriesViewModel: ViewModel() {
    lateinit var categoryLiveData: MutableLiveData< ArrayList<Category> >

    fun getCategories(){
       categoryLiveData = CategoryRepository.getCategories()
    }
}