package com.example.trubbi.providers

import com.example.trubbi.model.SurveyModel
import com.example.trubbi.providers.SurveyProvider.Companion.surveys

class SurveyProvider {
    companion object {
        fun getSurvey(position: Int): SurveyModel {
                return surveys[position];
        }

        private val surveys = listOf(
            SurveyModel(
                dialogOpened=false,
                question1 = false,
                question2 = false,
                question3 = false,
                openQuestion = ""
            )
        )
    }
}