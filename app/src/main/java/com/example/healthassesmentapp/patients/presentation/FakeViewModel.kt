package com.example.healthassesmentapp.patients.presentation

import com.example.healthassesmentapp.patients.data.AssessmentEntity
import com.example.healthassesmentapp.patients.data.PatientEntity
import com.example.healthassesmentapp.patients.data.VitalsEntity

interface PatientActions {
    fun saveRegistration(patient: PatientEntity, onComplete: (Long) -> Unit)
    fun setCurrentPatientId(id: Long)
    fun saveAssessment(assessment: AssessmentEntity, onComplete: (Long) -> Unit = {})
   fun saveVitals(vitals: VitalsEntity, onComplete: (Long) -> Unit = {})
}

