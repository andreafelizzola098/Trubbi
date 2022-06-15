package com.example.trubbi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trubbi.model.SurveyModel

class SurveyViewModel: ViewModel() {
    val surveyModel = MutableLiveData<SurveyModel>()
    val dialogOpened = MutableLiveData<Boolean>(false)

    fun setDialogOpened(value:Boolean){
        dialogOpened.postValue(value)
    }
}