package com.example.healthassesmentapp.patients.data


import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepository @Inject constructor(
    private val db: AppDatabase
) {
    private val patientDao = db.patientDao()
    private val vitalsDao = db.vitalsDao()
    private val assessmentDao = db.assessmentDao()


    suspend fun insertPatient(patient: PatientEntity): Long {
        return patientDao.insertPatient(patient)
    }

    suspend fun getPatientByUnique(unique: String): PatientEntity? =
        patientDao.getPatientByUnique(unique)

    suspend fun insertVitals(vitals: VitalsEntity): Long {
        return vitalsDao.insertVitals(vitals)
    }

    suspend fun insertAssessment(assessment: AssessmentEntity): Long {
        return assessmentDao.insertAssessment(assessment)
    }

    suspend fun getLatestPatient(): PatientEntity? = patientDao.getLatestPatient()

    suspend fun getVitalsForPatient(patientId: Long) =
        vitalsDao.getVitalsForPatient(patientId)

    suspend fun getAssessmentsForPatient(patientId: Long) =
        assessmentDao.getAssessmentsForPatient(patientId)

    fun getPatientById(id: Long): Flow<PatientEntity> = patientDao.getPatientById(id)

}
